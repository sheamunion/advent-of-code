package com.sheamunion.advent_of_code.two_thousand_eighteen;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PuzzleTest {

    @Test
    public void answer() {
        Puzzle subject = new Puzzle();

        assertThat(subject.answer("day_four_sample.txt")).isEqualTo("240");
    }
}