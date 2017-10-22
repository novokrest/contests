package com.leetcode.contest.task714;

public class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices.length < 2) {
            return 0;
        }

        int[] buyPrices = prices;
        int[] sellPrices = computeSellPrices(prices, fee);
        return computeSolution1(buyPrices, sellPrices, prices.length);
    }

    private int computeSolution1(int[] buyPrices, int[] sellPrices, int n) {
        int profit = 0;
        int buyLocalMin = buyPrices[0];
        for (int i = 0; i < n - 1; i++) {
            if (sellPrices[i + 1] > buyLocalMin) {
                profit += sellPrices[i + 1] - buyLocalMin;
                buyLocalMin = buyPrices[i + 1];
            } else {
                buyLocalMin = Math.min(buyLocalMin, buyPrices[i + 1]);
            }
        }
        return profit;
    }

    /**
     * dp[i, j] represents the max profit up until prices[j] using at most i transactions.
     * dp[i, j] = max(dp[i, j-1], prices[j] - prices[jj] + dp[i-1, jj]) { jj in range of [0, j-1] }
     *          = max(dp[i, j-1], prices[j] + max(dp[i-1, jj] - prices[jj]))
     * dp[0, j] = 0; 0 transactions makes 0 profit
     * dp[i, 0] = 0; if there is only one price data point you can't make any transaction.
     *
     * prev = dp[i - 1][*]
     * res = dp[i][*]
     */
    private int computeSolution2(int[] buyPrices, int[] sellPrices, int n) {
        int k = (n + 1) / 2;
        int[] prev = new int[n];
        int[] res = new int[n];
        for (int i = 1; i <= k; i++) {
            int localMax = prev[0] - buyPrices[0];
            for (int j = 1; j < n; j++) {
                res[j] = Math.max(res[j-1],  sellPrices[j] + localMax);
                localMax = Math.max(localMax, prev[j] - buyPrices[j]);
                prev[j] = res[j];
            }
        }
        return res[n-1];
    }

    private int[] computeSellPrices(int[] prices, int fee) {
        int[] sellPrices = new int[prices.length];
        for (int i = 0, length = prices.length; i < length; i++) {
            sellPrices[i] = prices[i] - fee;
        }
        return sellPrices;
    }
}