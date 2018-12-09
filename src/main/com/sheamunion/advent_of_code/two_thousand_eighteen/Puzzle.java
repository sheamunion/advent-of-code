package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Puzzle {

    public String answer(String inputFileName) {
        Map<String, String> log = getInputFromFileName(inputFileName);
        TreeMap<String, String> sortedLog = new TreeMap(log);
        String guardId = "";

        Map<String, List<Integer>> intervalsOfSleepByGuard = collectIntervalsOfSleepByGuard(sortedLog, guardId);

        String idOfGuardWhoSleptMost = getIdOfGuardWhoSleptMost(intervalsOfSleepByGuard);

        List<Integer> rangeOfMinutesSleptBySleepiestGuard = intervalsOfSleepByGuard.get(idOfGuardWhoSleptMost);

        Map<Integer, Integer> asleepDuringMinuteFrequency = getAsleepDuringMinuteFrequency(rangeOfMinutesSleptBySleepiestGuard);

        Integer sleepiestMinute = Collections.max(asleepDuringMinuteFrequency.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

        System.out.printf("Guard selected: %s%n", idOfGuardWhoSleptMost);
        System.out.printf("Minute selected: %s%n", sleepiestMinute);

        return String.valueOf(Integer.valueOf(idOfGuardWhoSleptMost) * sleepiestMinute);
    }

    private String getIdOfGuardWhoSleptMost(Map<String, List<Integer>> intervalsOfSleepByGuard) {
        Map<Integer, String> guardIdByTotalMinutesSlept = calculateTotalMinutesSleptByGuard(intervalsOfSleepByGuard);

        return Collections.max(guardIdByTotalMinutesSlept.entrySet(), Comparator.comparingInt(Map.Entry::getKey)).getValue();
    }

    private Map<Integer, Integer> getAsleepDuringMinuteFrequency(List<Integer> rangeOfMinutesSleptBySleepiestGuard) {
        Map<Integer, Integer> asleepDuringMinuteFrequency = new HashMap<>();
        for (int i = 1; i < rangeOfMinutesSleptBySleepiestGuard.size(); i++) {
            IntStream.rangeClosed(rangeOfMinutesSleptBySleepiestGuard.get(i - 1), rangeOfMinutesSleptBySleepiestGuard.get(i))
                    .forEach((minute) -> {
                        Integer currentCount = asleepDuringMinuteFrequency.get(minute);
                        currentCount = currentCount == null ? 0 : currentCount;
                        asleepDuringMinuteFrequency.put(minute, currentCount + 1);
                    });
        }
        return asleepDuringMinuteFrequency;
    }

    private Map<Integer, String> calculateTotalMinutesSleptByGuard(Map<String, List<Integer>> shiftsByGuard) {
        Map<Integer, String> sumMinutesSleptByGuard = new HashMap<>();
        shiftsByGuard.forEach((id, minutes) -> {
            Optional<Integer> sumMinutes = minutes.stream().reduce((a, b) -> b - a);
            if (sumMinutes.isPresent()) {
                sumMinutes.ifPresent(sum -> sumMinutesSleptByGuard.put(sum, id));
            } else {
                sumMinutesSleptByGuard.put(0, id);
            }
        });
        return sumMinutesSleptByGuard;
    }

    private Map<String, List<Integer>> collectIntervalsOfSleepByGuard(TreeMap<String, String> sortedLog, String guardId) {
        Map<String, List<Integer>> shiftsByGuard = new HashMap<>();
        for (Map.Entry<String, String> logEntry : sortedLog.entrySet()) {

            String entryData = logEntry.getValue();

            if (entryData.contains("Guard")) {
                guardId = entryData.split("#")[1].split(" ")[0];
                shiftsByGuard.putIfAbsent(guardId, new ArrayList<>());
                continue;
            }

            Integer minutes = Integer.valueOf(logEntry.getKey().split(":")[1]);

            if (entryData.contains("sleep")) {
                shiftsByGuard.get(guardId).add(minutes);
            } else if (entryData.contains("wake")) {
                shiftsByGuard.get(guardId).add(minutes - 1);
            }
        }
        return shiftsByGuard;
    }

    private Map<String, String> getInputFromFileName(String inputFileName) {
        String projectRoot = Paths.get("").toAbsolutePath().toString();
        String subdir = projectRoot.contains("src") ? "/main/resources/" : "/src/main/resources/";

        Path inputFilePath = Paths.get(
                String.format(
                        "%s%s%s"
                        , projectRoot
                        , subdir
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
