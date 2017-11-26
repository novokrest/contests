package com.leetcode.contest.task733;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void test() {
        // given
        int[][] image = new int[][] {
                new int[] { 1, 1, 1 },
                new int[] { 1, 1, 0 },
                new int[] { 1, 0, 1 },
        };

        int sc = 1, sr = 1, newColor = 2;

        // when
        int[][] result = new Solution().floodFill(image, sc, sr, newColor);

        // then
        int[][] expected = new int[][] {
                new int[] { 2, 2, 2 },
                new int[] { 2, 2, 0 },
                new int[] { 2, 0, 1 },
        };

        for (int row = 0, rows = expected.length; row < rows; row++) {
            for (int column = 0, columns = expected[0].length; column < columns; column++) {
                assertEquals(result[row][column], expected[row][column]);
            }
        }
    }
}
