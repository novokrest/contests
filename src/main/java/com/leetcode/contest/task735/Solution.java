package com.leetcode.contest.task735;

import java.util.Stack;

class Solution {

    public int[] asteroidCollision(int[] asteroids) {
        CollisionStack stack = new CollisionStack();
        for (int asteroid : asteroids) {
            stack.push(asteroid);
        }
        return stack.asArray();
    }

    class CollisionStack {
        private final Stack<Integer> asteroids = new Stack<>();

        public int[] asArray() {
            int[] array = new int[asteroids.size()];
            for (int i = array.length - 1; i > -1; i--) {
                int asteroid = asteroids.pop();
                array[i] = asteroid;
            }
            return array;
        }

        public void push(int asteroid) {
            if (asteroid > 0) {
                pushPositive(asteroid);
            } else {
                pushNegative(asteroid);
            }
        }

        private void pushPositive(int asteroid) {
            asteroids.push(asteroid);
        }

        private void pushNegative(int asteroid) {
            if (asteroids.empty() || asteroids.peek() < 0) {
                asteroids.push(asteroid);
            } else {
                int topPositiveAsteroid = asteroids.pop();
                int collisionResult = topPositiveAsteroid + asteroid;
                if (collisionResult != 0) {
                    push(collisionResult > 0 ? topPositiveAsteroid : asteroid);
                }
            }
        }
    }
}
