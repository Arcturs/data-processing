package ru.itmo.pddp.asashina.lab2;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class HadoopWordCountServiceTest {

    private final HadoopWordCountService service = new HadoopWordCountService();

    @Test
    @SneakyThrows
    void countTest() {
        var now = System.currentTimeMillis();
        service.count("/tmp/data/big.txt", "/tmp/data/out");
        var processTime = System.currentTimeMillis() - now;

        System.out.println("Время обработки файла: " + processTime + " мс");
    }

}
