package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Puzzle {

    public String answer(String inputFileName) {

        String answer = "You're not finished.";

        Map<String, String> log = getInputFromFileName(inputFileName);
        TreeMap<String, String> sortedLog = new TreeMap(log);
        Map<String, List<Integer>> shiftsByGuard = new HashMap<>();
        String guardId = "";

        collectMinutesSleptByGuard(sortedLog, shiftsByGuard, guardId);

        Map<Integer, String> guardIdByTotalMinutesSlept = new HashMap<>();
        calculateMinutesSleptByGuard(shiftsByGuard, guardIdByTotalMinutesSlept);

        Integer mostMinutesSlept = guardIdByTotalMinutesSlept.keySet()
                .stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList())
                .get(guardIdByTotalMinutesSlept.keySet().size() - 1);

        String idOfGuardWhoSleptMost = guardIdByTotalMinutesSlept.get(mostMinutesSlept);
        List<Integer> rangeOfMinutesSleptBySleepiestGuard = shiftsByGuard.get(idOfGuardWhoSleptMost);

        /*
        * It appears that I am finding the correct Guard Id.
        * I don't think I'm finding the correct sleepiest minute.
        * I need to debug this.
        * */

        Map<Integer, Integer> minutesSleptFrequency = new HashMap<>();
        for (int i = 1; i < rangeOfMinutesSleptBySleepiestGuard.size(); i++) {
            IntStream.rangeClosed(rangeOfMinutesSleptBySleepiestGuard.get(i - 1), rangeOfMinutesSleptBySleepiestGuard.get(i))
                    .forEach((minute) -> {
                        Integer currentCount = minutesSleptFrequency.get(minute);
                        currentCount = currentCount == null ? 1 : currentCount;
                        minutesSleptFrequency.put(minute, currentCount + 1);
                    });
        }

        Integer sleepiestMinute = minutesSleptFrequency.values()
                .stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList())
                .get(minutesSleptFrequency.values().size() - 1);

        System.out.println(idOfGuardWhoSleptMost);
        System.out.println(sleepiestMinute);

        return String.valueOf(Integer.valueOf(idOfGuardWhoSleptMost) * sleepiestMinute);
    }

    private void calculateMinutesSleptByGuard(Map<String, List<Integer>> shiftsByGuard, Map<Integer, String> sumMinutesSleptByGuard) {
        shiftsByGuard.forEach((id, minutes) -> {
            Optional<Integer> sumMinutes = minutes.stream().reduce((a, b) -> b - a);
            if (sumMinutes.isPresent()) {
                sumMinutes.ifPresent(sum -> sumMinutesSleptByGuard.put(sum, id));
            } else {
                sumMinutesSleptByGuard.put(0, id);
            }
        });
    }

    private void collectMinutesSleptByGuard(TreeMap<String, String> sortedLog, Map<String, List<Integer>> shiftsByGuard, String guardId) {
        for (Map.Entry<String, String> logEntry : sortedLog.entrySet()) {

            String entryData = logEntry.getValue();

            if (entryData.contains("Guard")) {
                guardId = entryData.split("#")[1].split(" ")[0];
                shiftsByGuard.putIfAbsent(guardId, new ArrayList<>());
                continue;
            }
            Integer minutes = Integer.valueOf(logEntry.getKey().split(":")[1]);
            shiftsByGuard.get(guardId).add(minutes);
        }
    }

    private Map<String, String> getInputFromFileName(String inputFileName) {
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

    private Map<String, String> parse(Path inputFilePath) {
        Map<String, String> log = new HashMap<>();

        try {
            Files.lines(inputFilePath).forEach(line -> {
                List<String> result = parseLine(line);
                log.put(result.get(0), result.get(1));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }

    private List<String> parseLine(String line) {
        Pattern pattern = Pattern.compile("\\[([\\d- :]*)\\] ([\\w# ]*)");
        Matcher matcher = pattern.matcher(line);
        matcher.find();

        List<String> results = new ArrayList<>();
        results.add(matcher.group(1));
        results.add(matcher.group(2));

        return results;
    }
}
