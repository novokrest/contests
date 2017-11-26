package com.leetcode.contest.task734;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solution {

    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        SimilarResolver similarResolver = new SimilarResolver(pairs);

        for (int i = 0, count = words1.length; i < count; i++) {
            String word1 = words1[i];
            String word2 = words2[i];
            if (!similarResolver.isSimilar(word1, word2)) {
                return false;
            }
        }

        return true;
    }

    private static class SimilarResolver {
        private final Set<WordPair> pairs;

        private SimilarResolver(String[][] pairs) {
            this.pairs = new HashSet<>(pairs.length * 2);
            Arrays.stream(pairs).forEach(pair -> {
                this.pairs.add(new WordPair(pair[0], pair[1]));
                this.pairs.add(new WordPair(pair[1], pair[0]));
            });
        }

        private boolean isSimilar(String word1, String word2) {
            return Objects.equals(word1, word2) || pairs.contains(new WordPair(word1, word2));
        }
    }

    private static class WordPair {
        private final String first;
        private final String second;

        private WordPair(String first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }

            WordPair other = (WordPair) obj;
            return Objects.equals(first, other.first) && Objects.equals(second, other.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
}
