package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle {

    public String answer(String inputFileName) {
        List<String> boxIds = getInputFromFileName(inputFileName);
        String matchingLetters = null;

        for (int i = 0; i < boxIds.size(); i++) {
            for (int j = i + 1; j < boxIds.size(); j++) {
                matchingLetters = findMatch(boxIds.get(i), boxIds.get(j));
                if (matchingLetters!=null) {
                    return matchingLetters;
                }
            }
        }

        return null;
    }

    private String findMatch(String first, String second) {
        System.out.printf("Comparing %s to %s%n", first, second);
        int idSize = first.length();
        List<String> matchingChars = new ArrayList<>();

        for (int k = 0; k < idSize; k++) {
            if (first.charAt(k) == second.charAt(k)) {
                matchingChars.add(String.valueOf(first.charAt(k)));
            }
            if (matchingChars.size() == idSize - 1) {
                return matchingChars.stream().map(Object::toString).collect(Collectors.joining(""));
            }
        }
        return null;
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
