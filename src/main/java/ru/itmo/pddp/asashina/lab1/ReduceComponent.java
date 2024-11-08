package ru.itmo.pddp.asashina.lab1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReduceComponent {

    public Map<String, Integer> reduce(List<String> input) {
        var result = new HashMap<String, Integer>();
        for (var s : input) {
            result.put(s, result.getOrDefault(s, 1) + 1);
        }
        return result;
    }

}
