package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle {

    public Integer answer(String inputFileName) {
        List<String> boxIds = getInputFromFileName(inputFileName);

        Integer twiceCount = boxIds.stream()
                .filter(this::hasLetterExactlyTwice)
                .collect(Collectors.toList())
                .size();

        Integer thriceCount = boxIds.stream()
                .filter(this::hasLetterExactlyThrice)
                .collect(Collectors.toList())
                .size();

        return twiceCount * thriceCount;
    }

    public boolean hasLetterExactlyTwice(String id) {
        Map<String, Integer> frequency = toHistogram(id);

        boolean appearsExactlyTwice = false;
        for (String key : frequency.keySet()) {
            if (frequency.get(key) == 2) {
                appearsExactlyTwice = true;
                break;
            }
        }
        return appearsExactlyTwice;
    }

    public boolean hasLetterExactlyThrice(String id) {
        Map<String, Integer> frequency = toHistogram(id);

        boolean appearsExactlyThrice = false;
        for (String key : frequency.keySet()) {
            if (frequency.get(key) == 3) {
                appearsExactlyThrice = true;
                break;
            }
        }
        return appearsExactlyThrice;
    }

    private Map<String, Integer> toHistogram(String id) {
        Map<String, Integer> frequency = new HashMap<>();
        Arrays.stream(id.split(""))
                .forEach(el -> {
                    Integer count = frequency.get(el);
                    if (count == null) {
                        frequency.put(el, 1);
                    } else {
                        frequency.put(el, count + 1);
                    }
                });
        return frequency;
    }

    private List<String> getInputFromFileName(String inputFileName) {
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        Path inputFilePath = Paths.get(
                String.format(
                        "%s/src/main/resources/%s"
                        , projectRoot
                        , inputFileName
                )
        );

        List<String> items = null;
        try {
            items = Files.lines(inputFilePath).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
