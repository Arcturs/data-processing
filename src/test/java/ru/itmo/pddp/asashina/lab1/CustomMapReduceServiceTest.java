package ru.itmo.pddp.asashina.lab1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

class CustomMapReduceServiceTest {

    private final CustomMapReduceService service =
            new CustomMapReduceService(new MapComponent(), new ReduceComponent());
    private final File file = new File("/Users/a.sashina/IdeaProjects/data-processing/data/big.txt");

    @ParameterizedTest
    @MethodSource("provideThreadAmounts")
    void processFileTest(int mapThreads, int reduceThreads) {
        service.setMapWorkerNumber(mapThreads);
        service.setReduceWorkerNumber(reduceThreads);
        System.out.println("Размер узлов на Map: " + mapThreads + " и Reduce: " + reduceThreads);
        System.out.println("Размер файла: " + file.length() / 1024 + " Кб");

        var now = System.currentTimeMillis();
        service.processFile(file.getAbsolutePath());
        var processTime = System.currentTimeMillis() - now;

        System.out.println("Время обработки файла: " + processTime + " мс");
    }

    private static Stream<Arguments> provideThreadAmounts() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(1, 2),
                Arguments.of(2, 1),
                Arguments.of(2, 2),
                Arguments.of(5, 5),
                Arguments.of(5, 10),
                Arguments.of(10, 5),
                Arguments.of(10, 10),
                Arguments.of(12, 12),
                Arguments.of(12, 15),
                Arguments.of(15, 12),
                Arguments.of(15, 15),
                Arguments.of(20, 20));
    }

}
