package com.leetcode.contest.task714;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void test1() {
        assertEquals(
                8,
                new Solution().maxProfit(
                    new int[]{ 1, 3, 2, 8, 4, 9 }, 2
                )
        );
    }

    @Test
    public void test2() {
        assertEquals(
                6,
                new Solution().maxProfit(
                        new int[]{ 1, 3, 7, 5, 10, 3 }, 3
                )
        );
    }
}
