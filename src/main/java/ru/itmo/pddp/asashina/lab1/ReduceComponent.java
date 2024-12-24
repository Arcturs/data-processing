package ru.itmo.pddp.asashina.lab1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReduceComponent {

    public Map<String, Integer> reduce(List<String> input) {
        Map<String, Integer> result = new HashMap<>();
        for (String s : input) {
            result.put(s, result.getOrDefault(s, 1) + 1);
        }
        return result;
    }

}
