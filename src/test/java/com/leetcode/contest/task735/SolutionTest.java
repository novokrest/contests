package com.leetcode.contest.task735;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void test1() {
        test(new int[]{5, 10, -5}, new int[]{5, 10});
    }

    @Test
    public void test2() {
        test(new int[]{8, -8}, new int[0]);
    }

    @Test
    public void test3() {
        test(new int[]{10, 2, -5}, new int[]{10});
    }

    private void test(int[] given, int[] expected) {
        int[] actual = new Solution().asteroidCollision(given);

        assertEquals(expected.length, actual.length);
        for (int i = 0, count = actual.length; i < count; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
