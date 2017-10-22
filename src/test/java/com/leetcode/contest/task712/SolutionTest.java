package com.leetcode.contest.task712;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void test1() {
        assertEquals(
                231,
                new Solution().minimumDeleteSum("sea", "eat")
        );
    }

    @Test
    public void test2() {
        assertEquals(
                403,
                new Solution().minimumDeleteSum("delete", "leet")
        );
    }
}
