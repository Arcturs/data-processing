package ru.itmo.pddp.asashina.lab2;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.itmo.pddp.asashina.lab2.web.graph.HadoopWebGraphService;
import ru.itmo.pddp.asashina.lab2.word.count.HadoopWordCountService;

class HadoopServiceTest {

    private final HadoopWordCountService wordCountService = new HadoopWordCountService();
    private final HadoopWebGraphService webGraphService = new HadoopWebGraphService();

    @Test
    @SneakyThrows
    void wordCountTest() {
        var now = System.currentTimeMillis();
        wordCountService.count("/Users/a.sashina/IdeaProjects/data-processing/data/big.txt", "/tmp/data/out/word/count");
        var processTime = System.currentTimeMillis() - now;

        System.out.println("Время обработки файла: " + processTime + " мс");
    }

    @Test
    @SneakyThrows
    void webGraphTest() {
        var now = System.currentTimeMillis();
        webGraphService.count("/Users/a.sashina/IdeaProjects/data-processing/data/web-Google.txt", "/tmp/data/out/web/graph");
        var processTime = System.currentTimeMillis() - now;

        System.out.println("Время обработки файла: " + processTime + " мс");
    }

}
