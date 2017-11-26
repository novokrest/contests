package com.leetcode.contest.task733;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        ColorManager colorManager = new ColorManager(image);
        Point startPoint = new Point(sr, sc);

        List<Point> pointsToVisit = new LinkedList<>();
        pointsToVisit.add(startPoint);

        Set<Point> pointsToVisitSet = new HashSet<>();
        pointsToVisitSet.add(startPoint);

        while (!pointsToVisit.isEmpty()) {
            Point p = pointsToVisit.get(0);
            pointsToVisit.remove(0);

            PointNeighbours neighbours = new PointNeighbours(p, image.length, image[0].length);
            neighbours.getNeighbours()
                    .stream()
                    .filter(neighbour ->
                            colorManager.getColor(p) == colorManager.getColor(neighbour) &&
                                    !pointsToVisitSet.contains(neighbour)
                    )
                    .forEach(neighbour -> {
                        pointsToVisit.add(neighbour);
                        pointsToVisitSet.add(neighbour);
                    });

            colorManager.setColor(p, newColor);
        }

        return image;

    }

    private static class ColorManager {
        private final int[][] image;

        private ColorManager(int[][] image) {
            this.image = image;
        }

        private int getColor(Point p) {
            return image[p.row][p.column];
        }

        private void setColor(Point p, int color) {
            image[p.row][p.column] = color;
        }
    }

    private static class PointNeighbours {
        private final Point p;
        private final int rows;
        private final int columns;

        private PointNeighbours(Point p, int rows, int columns) {
            this.p = p;
            this.rows = rows;
            this.columns = columns;
        }

        private List<Point> getNeighbours() {
            List<Point> points = new ArrayList<>(4);
            points.add(getLeft());
            points.add(getRight());
            points.add(getUpper());
            points.add(getBottom());
            return points.stream().filter(Objects::nonNull).collect(Collectors.toList());
        }

        private Point getLeft() {
            int column = p.column - 1;
            return column < 0 ? null : new Point(p.row, column);
        }

        private Point getRight() {
            int column = p.column + 1;
            return column == columns ? null : new Point(p.row, column);
        }

        private Point getUpper() {
            int row = p.row - 1;
            return row < 0 ? null : new Point(row, p.column);
        }

        private Point getBottom() {
            int row = p.row + 1;
            return row == rows ? null : new Point(row, p.column);
        }
    }

    private static class Point {
        private final int row;
        private final int column;

        private Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }

            Point other = (Point) obj;
            return row == other.row && column == other.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
}
