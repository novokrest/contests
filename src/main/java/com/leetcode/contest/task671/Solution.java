package com.leetcode.contest.task671;

public class Solution {

    private static final int NOT_FOUND_MINIMUM = -1;

    public int findSecondMinimumValue(TreeNode root) {
        return findMinimumValues(root).secondMinimum;
    }

    private MinimumValues findMinimumValues(TreeNode node) {
        if (node == null) {
            return null;
        }

        if (node.left == null && node.right == null) {
            return new MinimumValues(node.val, NOT_FOUND_MINIMUM);
        }

        MinimumValues leftValues = findMinimumValues(node.left);
        MinimumValues rightValues = findMinimumValues(node.right);
        MinimumValues childValues = mergeMinimumValues(leftValues, rightValues);

        if (node.val == childValues.minimum) {
            return new MinimumValues(node.val, childValues.secondMinimum);
        }

        return new MinimumValues(
                node.val,
                childValues.minimum
        );
    }

    private MinimumValues mergeMinimumValues(MinimumValues values1, MinimumValues values2) {
        if (values1 == null) {
            return values2;
        }
        if (values2 == null) {
            return values1;
        }

        int secondMinimum = findSecondMinimum(values1.secondMinimum, values2.secondMinimum);
        if (values1.minimum == values2.minimum) {
            int minimum = values1.minimum;
            return new MinimumValues(minimum, secondMinimum);
        }

        return new MinimumValues(
                Math.min(values1.minimum, values2.minimum),
                secondMinimum == NOT_FOUND_MINIMUM
                        ? Math.max(values1.minimum, values2.minimum)
                        : Math.min(Math.max(values1.minimum, values2.minimum), secondMinimum)
        );
    }

    private int findSecondMinimum(int secondMinimum1, int secondMinimum2) {
        if (secondMinimum1 == NOT_FOUND_MINIMUM) {
            return secondMinimum2;
        }
        if (secondMinimum2 == NOT_FOUND_MINIMUM) {
            return secondMinimum1;
        }
        return Math.min(secondMinimum1, secondMinimum2);
    }

    private static class MinimumValues {
        final int minimum;
        final int secondMinimum;

        MinimumValues(int minimum, int secondMinimum) {
            this.minimum = minimum;
            this.secondMinimum = secondMinimum;
        }
    }
}
