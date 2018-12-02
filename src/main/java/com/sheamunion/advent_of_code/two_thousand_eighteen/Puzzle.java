package main.java.com.sheamunion.advent_of_code.two_thousand_eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle {

    private static final String PROJECT_ROOT = Paths.get("").toAbsolutePath().toString();
    private static final String SOURCE_DIR = "src/main/java/com/sheamunion/advent_of_code";
    private static final String YEAR_DIR = "two_thousand_eighteen";

    public Integer answer(String inputFileName) {
        List<Integer> frequencyChanges = getInputFromFileName(inputFileName);

        int currentFrequency = 0;
        Set<Integer> frequenciesSeen = new HashSet<>();
        boolean isNotDuplicateFrequency = true;

        while (isNotDuplicateFrequency) {
            for (int delta : frequencyChanges) {
                currentFrequency = currentFrequency + delta;
                if (!frequenciesSeen.add(currentFrequency)) {
                    isNotDuplicateFrequency = false;
                    break;
                }
                frequenciesSeen.add(currentFrequency);
            }
        }

        return currentFrequency;
    }

    private List<Integer> getInputFromFileName(String inputFileName) {
        Path inputFilePath = Paths.get(
                String.format(
                        "%s/%s/%s/resources/%s"
                        , PROJECT_ROOT
                        , SOURCE_DIR
                        , YEAR_DIR
                        , inputFileName
                )
        );

        List<Integer> frequencyChanges = null;
        try {
            frequencyChanges = Files.lines(inputFilePath)
                    .map(Integer::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequencyChanges;
    }
}
