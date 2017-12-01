package com.leetcode.contest.task736;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void test1() {
        test("(add 1 2)", 3);
        test("(mult 3 (add 2 3))", 15);
        test("(let x 2 (mult x 5))", 10);
        test("(let x 2 (mult x (let x 3 y 4 (add x y))))", 14);
        test("(let x 3 x 2 x)", 2);
        test("(let x 1 y 2 x (add x y) (add x y))", 5);
        test("(let x 2 (add (let x 3 (let x 4 x)) x))", 6);
        test("(let a1 3 b2 (add a1 1) b2)", 4);
        test("(let x 7 -12)", -12);
        test("(let var 78 b 77 (let c 33 (add c (mult var 66))))", 5181);
    }

    private void test(String lispExpression, int expected) {
        assertEquals(expected, new Solution().evaluate(lispExpression));
    }
}
