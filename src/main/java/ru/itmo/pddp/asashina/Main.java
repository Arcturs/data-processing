package ru.itmo.pddp.asashina;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itmo.pddp.asashina.lab2.web.graph.HadoopWebGraphService;
import ru.itmo.pddp.asashina.lab2.word.count.HadoopWordCountService;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        log.info("Запуск подсчета количества слов");
        long startTime = System.currentTimeMillis();
        HadoopWordCountService.count(args[0], args[2]);
        log.info("Потраченное время: {} мс", System.currentTimeMillis() - startTime);

        log.info("Запуск подсчета ссылок на потомков");
        startTime = System.currentTimeMillis();
        HadoopWebGraphService.count(args[1], args[3]);
        log.info("Потраченное время: {} мс", System.currentTimeMillis() - startTime);
    }

}