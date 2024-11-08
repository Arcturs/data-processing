package ru.itmo.pddp.asashina.lab1;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.TestOnly;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static java.nio.file.StandardOpenOption.READ;

@RequiredArgsConstructor
public class CustomMapReduceService {

    private static final int BYTE_BUFFER_SIZE_KB = 10 * 1024;

    @Setter(onMethod_ = { @TestOnly})
    private int mapWorkerNumber;

    @Setter(onMethod_ = { @TestOnly })
    private int reduceWorkerNumber;

    private final MapComponent mapComponent;
    private final ReduceComponent reduceComponent;

    public void processFile(String filePath) {
        var mapService = Executors.newFixedThreadPool(mapWorkerNumber);
        var reduceService = Executors.newFixedThreadPool(reduceWorkerNumber);

        var results = new ArrayList<CompletableFuture<Map<String, Integer>>>();
        try (var reader = Files.newByteChannel(Path.of(filePath), READ)) {
            var byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE_KB);
            while (reader.read(byteBuffer) > 0) {
                byteBuffer.flip();

                // Map phase
                var result = CompletableFuture.supplyAsync(() ->
                                mapComponent.map(new String(byteBuffer.array())), mapService)
                        .thenCompose(mappedValue ->
                                // Reduce phase
                                CompletableFuture.supplyAsync(() ->
                                        reduceComponent.reduce(mappedValue), reduceService));
                results.add(result);

                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        var result = CompletableFuture.allOf(results.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> {
                    var resultMap = new HashMap<String, Integer>();
                    for (var future : results) {
                        future.join()
                                .forEach((key, value) ->
                                        resultMap.put(key, resultMap.getOrDefault(key, 0) + value));
                    }
                    return resultMap;
                })
                .join();

        System.out.println("Результат обработки:");
        result.forEach((key, value) -> System.out.println("Word: " + key + ", count: " + value));

        mapService.shutdown();
        reduceService.shutdown();
    }

}
