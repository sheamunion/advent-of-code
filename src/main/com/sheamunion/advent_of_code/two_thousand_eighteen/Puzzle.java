package com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle {

    public String answer(String inputFileName) {
        String answer = "You're not finished.";
        Map<Date, String> log = getInputFromFileName(inputFileName);

        TreeMap sortedLog = new TreeMap(log);

        sortedLog.forEach((date, string ) -> {
            System.out.printf("%s....%s%n", date.toString(), string);
        });

        return answer;
    }

    private Map<Date, String> getInputFromFileName(String inputFileName) {
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

    private Map<Date, String> parse(Path inputFilePath) {
        Map<Date, String> log = new HashMap<>();

        try {
            Files.lines(inputFilePath).forEach(line -> {
                List<String> result = parseLine(line);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                Date entryTimestamp = null;
                try {
                    entryTimestamp = simpleDateFormat.parse(result.get(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String entryData = result.get(1);

                log.put(entryTimestamp, entryData);
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
