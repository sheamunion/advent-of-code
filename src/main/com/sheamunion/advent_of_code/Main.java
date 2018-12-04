package com.sheamunion.advent_of_code;

import com.sheamunion.advent_of_code.two_thousand_eighteen.Puzzle;

public class Main {

    private static Puzzle puzzle = new Puzzle();
    private static String inputFileName;

    public static void main(String[] args) {
        switch (args.length) {
            case 1:
                inputFileName = args[0];
            default:
                inputFileName = "day_two.txt";
        }

        System.out.printf("Answer: %s", puzzle.answer(inputFileName));
    }
}
