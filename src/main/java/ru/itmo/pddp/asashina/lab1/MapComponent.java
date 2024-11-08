package ru.itmo.pddp.asashina.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapComponent {

    public List<String> map(String input) {
        return Arrays.stream(
                input.trim()
                    .split("\\W+"))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
    }

}
