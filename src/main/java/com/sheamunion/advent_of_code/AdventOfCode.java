package com.sheamunion.advent_of_code;

import com.sheamunion.advent_of_code.two_thousand_eighteen.Puzzle;

public class AdventOfCode {

    private static Puzzle puzzle = new Puzzle();
    private static String inputFileName;

    public static void main(String[] args) {

        if (args.length == 1) {
            inputFileName = args[0];
        } else {
            inputFileName = "day_four.txt";
        }

        System.out.printf("Answer: %s%n", puzzle.answer(inputFileName));
    }
}