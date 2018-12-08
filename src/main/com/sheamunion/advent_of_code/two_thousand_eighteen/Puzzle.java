package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle {

    public String answer(String inputFileName) {
        String answer = "";
        Map<Integer, Map<String, Integer>> claims = getInputFromFileName(inputFileName);
        Map<List<Integer>, Integer> claimsToOverlapCount = new HashMap<>();

        for (Map.Entry<Integer, Map<String, Integer>> claim : claims.entrySet()) {
            Map<String, Integer> dimensions = claim.getValue();

            for (int h = 1; h <= dimensions.get("height"); h++) {
                for (int w = 1; w <= dimensions.get("width"); w++) {
                    List<Integer> newCoords = getNewCoords(dimensions.get("x"), dimensions.get("y"), h, w);

                    if (claimsToOverlapCount.get(newCoords) == null) {
                        claimsToOverlapCount.put(newCoords, 1);
                    } else {
                        claimsToOverlapCount.put(newCoords, claimsToOverlapCount.get(newCoords) + 1);
                    }
                }
            }
        }

        for (Map.Entry<Integer, Map<String, Integer>> claim : claims.entrySet()) {
            if (!doesClaimOverlap(claimsToOverlapCount, claim)) {
                answer = String.valueOf(claim.getKey());
            }
        }

        return answer;
    }

    private List<Integer> getNewCoords(int x, int y, int h, int w) {
        List<Integer> newCoords = new ArrayList<>();
        newCoords.add((x + w));
        newCoords.add((y + h));
        return newCoords;
    }

    private boolean doesClaimOverlap(Map<List<Integer>, Integer> overlapCount, Map.Entry<Integer, Map<String, Integer>> claim) {
        Map<String, Integer> dimensions = claim.getValue();
        for (int h = 1; h <= dimensions.get("height"); h++) {
            for (int w = 1; w <= dimensions.get("width"); w++) {
                List<Integer> newCoords = getNewCoords(dimensions.get("x"), dimensions.get("y"), h, w);
                if (overlapCount.get(newCoords) > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<Integer, Map<String, Integer>> getInputFromFileName(String inputFileName) {
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        Path inputFilePath = Paths.get(
                String.format(
                        "%s/src/main/resources/%s"
                        , projectRoot
                        , inputFileName
                )
        );
        return parse(inputFilePath);
    }

    private Map<Integer, Map<String, Integer>> parse(Path inputFilePath) {
        Map<Integer, Map<String, Integer>> claims = new HashMap<>();

        try {
            Files.lines(inputFilePath).forEach(line -> {
                List<String> result = parseLine(line);

                Integer id = Integer.valueOf(result.get(0));
                Integer x = Integer.valueOf(result.get(1));
                Integer y = Integer.valueOf(result.get(2));
                Integer width = Integer.valueOf(result.get(3));
                Integer height = Integer.valueOf(result.get(4));

                Map<String, Integer> dimensions = new HashMap<>();

                dimensions.put("x", x);
                dimensions.put("y", y);
                dimensions.put("width", width);
                dimensions.put("height", height);
                dimensions.put("hasOverlap", 0);

                try {
                    claims.put(id, dimensions);
                } catch (NullPointerException e) {
                    e.getLocalizedMessage();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return claims;
    }

    private List<String> parseLine(String line) {
        Pattern pattern = Pattern.compile("(\\d*) @ (\\d*),(\\d*): (\\d*)x(\\d*)");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        List<String> results = new ArrayList<>();
        results.add(matcher.group(1));
        results.add(matcher.group(2));
        results.add(matcher.group(3));
        results.add(matcher.group(4));
        results.add(matcher.group(5));

        return results;
    }
}
