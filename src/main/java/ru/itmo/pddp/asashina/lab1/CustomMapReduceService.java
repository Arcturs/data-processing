package ru.itmo.pddp.asashina.lab1;

import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardOpenOption.READ;

public class CustomMapReduceService {

    private static final int BYTE_BUFFER_SIZE_KB = 10 * 1024;

    @Setter
    private int mapWorkerNumber;

    @Setter
    private int reduceWorkerNumber;

    private final MapComponent mapComponent;
    private final ReduceComponent reduceComponent;

    public CustomMapReduceService(MapComponent mapComponent, ReduceComponent reduceComponent) {
        this.mapComponent = mapComponent;
        this.reduceComponent = reduceComponent;
    }

    public void processFile(String filePath) {
        ExecutorService mapService = Executors.newFixedThreadPool(mapWorkerNumber);
        ExecutorService reduceService = Executors.newFixedThreadPool(reduceWorkerNumber);

        List<CompletableFuture<Map<String, Integer>>> results = new ArrayList<>();
        try (SeekableByteChannel reader = Files.newByteChannel(new File(filePath).toPath(), READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE_KB);
            while (reader.read(byteBuffer) > 0) {
                byteBuffer.flip();

                // Map phase
                CompletableFuture<Map<String, Integer>> result = CompletableFuture.supplyAsync(() ->
                                mapComponent.map(new String(byteBuffer.array())), mapService)
                        .thenCompose(mappedValue ->
                                // Reduce phase
                                CompletableFuture.supplyAsync(() ->
                                        reduceComponent.reduce(mappedValue), reduceService));
                results.add(result);

                byteBuffer.clear();
            }
        } catch (IOException e) {
            mapService.shutdownNow();
            reduceService.shutdownNow();
            throw new UncheckedIOException(e);
        }

        Map<String, Integer> result = CompletableFuture.allOf(results.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> {
                    Map<String, Integer> resultMap = new HashMap<>();
                    for (CompletableFuture<Map<String, Integer>> future : results) {
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
