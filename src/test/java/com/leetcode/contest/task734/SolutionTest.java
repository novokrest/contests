package com.leetcode.contest.task734;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SolutionTest {

    @Test
    public void test1() {
        // given
        String[] words1 = {"great", "acting", "skills"};
        String[] words2 = {"fine", "drama", "talent"};
        String[][] pairs = {
                {"great", "fine"},
                {"drama", "acting"},
                {"skills", "talent"}
        };

        // when
        boolean result = new Solution().areSentencesSimilar(words1, words2, pairs);

        // then
        assertTrue(result);
    }
}
