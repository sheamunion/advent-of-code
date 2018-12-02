package com.sheamunion.advent_of_code.two_thousand_eighteen;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PuzzleTest {

    private Puzzle subject = new Puzzle();
    private List<String> onlyExactlyTwice;
    private List<String> onlyExactlyThrice;
    private String onlyExactlyTwiceOrThrice;
    private String neitherTwiceNorThrice;

    @Before
    public void setUp() {
        onlyExactlyTwice = Arrays.asList(
                "abbcde"
                , "aabcdd"
                , "abcdee"
        );

        onlyExactlyThrice = Arrays.asList("abcccd", "ababab");

        onlyExactlyTwiceOrThrice = "bababc";

        neitherTwiceNorThrice = "abcdef";
    }

    @Test
    public void answer() {
        int checksum = subject.answer("day_two_sample.txt");
        assertEquals(checksum, 12);
    }

    @Test
    public void hasLetterExactlyTwice_whenALetterAppearsExactlyTwice_thenReturnsTrue() {
        onlyExactlyTwice.forEach(testCase -> {
            assertTrue("Should appear exactly twice", subject.hasLetterExactlyTwice(testCase));
        });

        assertTrue("Should appear exactly twice or thrice", subject.hasLetterExactlyTwice(onlyExactlyTwiceOrThrice));

        onlyExactlyThrice.forEach(testCase -> {
            assertFalse("Should NOT appear exactly thrice", subject.hasLetterExactlyTwice(testCase));
        });

        assertFalse("Should appear exactly twice", subject.hasLetterExactlyTwice(neitherTwiceNorThrice));
    }

    @Test
    public void hasLetterExactlyThrice_whenALetterAppearsExactlyThrice_thenReturnsTrue() {
        onlyExactlyThrice.forEach(testCase -> {
            assertTrue("Should appear exactly thrice", subject.hasLetterExactlyThrice(testCase));
        });

        assertTrue("Should appear exactly twice or thrice", subject.hasLetterExactlyThrice(onlyExactlyTwiceOrThrice));

        onlyExactlyTwice.forEach(testCase -> {
            assertFalse("Should NOT appear exactly twice", subject.hasLetterExactlyThrice(testCase));
        });

        assertFalse("Neither twice nor thrice", subject.hasLetterExactlyThrice(neitherTwiceNorThrice));
    }
}