package com.leetcode.contest.task671;

public class Test {

    public static void main(String[] args) {
        test(
                new TreeNode(1,
                        new TreeNode(1,
                                new TreeNode(1,
                                        new TreeNode(3,
                                                new TreeNode(3),
                                                new TreeNode(3)
                                        ),
                                        new TreeNode(1,
                                                new TreeNode(1),
                                                new TreeNode(6)
                                        )
                                ),
                                new TreeNode(1,
                                        new TreeNode(1,
                                                new TreeNode(2),
                                                new TreeNode(1)
                                        ),
                                        new TreeNode(1)
                                )
                        ),
                        new TreeNode(3,
                                new TreeNode(3,
                                        new TreeNode(3),
                                        new TreeNode(8)
                                ),
                                new TreeNode(4,
                                        new TreeNode(4),
                                        new TreeNode(8)
                                )
                        )
                ),
                2
        );

        test(
                new TreeNode(2,
                        new TreeNode(2),
                        new TreeNode(5, new TreeNode(5), new TreeNode(7))
                ),
                5

        );

        test(
                new TreeNode(2, new TreeNode(2), new TreeNode(2)),
                -1
        );
    }

    private static void test(TreeNode root, int expected) {
        int actual = new Solution().findSecondMinimumValue(root);
        if (actual != expected) {
            throw new RuntimeException(String.format("actual=%d, expected=%d", actual, expected));
        }
    }
}
