package com.leetcode.contest.task714;

/**
 * https://discuss.leetcode.com/category/1573/best-time-to-buy-and-sell-stock-with-transaction-fee
 */
public class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices.length < 2) {
            return 0;
        }

        return computeSolution(prices, fee, prices.length);
    }

    /**
     * no_stock = profit having no stock
     * has_stock = profit having 1 stock
     */
    private int computeSolution(int[] prices, int fee, int n) {
        int no_stock = 0;
        int has_stock = -prices[0] - fee;

        for (int i = 1; i < n; i++) {
            int prev_no_stock = no_stock;
            no_stock = Math.max(no_stock, has_stock + prices[i]);
            has_stock = Math.max(has_stock, prev_no_stock - prices[i] - fee);
        }

        return no_stock;
    }
}