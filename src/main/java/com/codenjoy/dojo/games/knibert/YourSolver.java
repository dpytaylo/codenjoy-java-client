package com.codenjoy.dojo.games.knibert;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Author: your name
 *
 * This is your AI algorithm for the game.
 * Implement it at your own discretion.
 * Pay attention to  - there is
 * a test framework for you.
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    private boolean isWallHere(Point currentPos, List<Point> walls) {
        for (var wall : walls) {
            if (wall.equals(currentPos)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        System.out.println(board.toString());

        var walls = board.getWalls();
        var stones = board.getStones();
        var apples = board.getApples();

        var head = board.getHead();
        var head_and_tail = board.getHero();

        var direction = board.getHeroDirection();
        var outputDirection = direction;

        var tail = new ArrayList<>(head_and_tail);
        tail.remove(head);

        var map = new OurMap(board.size(), board.size(), walls, stones, tail);

        if (!board.getApples().isEmpty()) {
            return getNextDirection(map, head, board.getApples().get(0)).toString();
        }

        if (direction == Direction.UP) {
            if (isWallHere(PointUtils.addPoint(head, new PointImpl(0, 1)), walls)) {
                outputDirection = Direction.RIGHT;
            }
        } else if (direction == Direction.RIGHT) {
            if (isWallHere(PointUtils.addPoint(head, new PointImpl(1, 0)), walls)) {
                outputDirection = Direction.DOWN;
            }
        } else if (direction == Direction.DOWN) {
            if (isWallHere(PointUtils.addPoint(head, new PointImpl(0, -1)), walls)) {
                outputDirection = Direction.LEFT;
            }
        } else if (direction == Direction.LEFT) {
            if (isWallHere(PointUtils.addPoint(head, new PointImpl(-1, 0)), walls)) {
                outputDirection = Direction.UP;
            }
        }

        return outputDirection.toString();
    }

    public Direction getNextDirection(OurMap map, Point currentPos, Point endPos) {
        var cloned = map.clone();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                findShortestPathLength(cloned, currentPos, new PointImpl(x, y));
            }
        }

        System.out.println(cloned);

        var p = findPath(cloned, currentPos, endPos);

        return currentPos.direction(p);
    }

    private void addValue(OurMap map, int x, int y, int value) {
        if (y < 0 || y >= map.getHeight()) {
            return;
        }

        if (x < 0 || x >= map.getWidth()) {
            return;
        }

        map.setCell(x, y, map.getCell(x, y) + value);
    }

    private final static int[] row = {-1, 0, 0, 1};
    private final static int[] col = {0, -1, 1, 0};

    private void findShortestPathLength(OurMap map, Point src, Point dst) {
        var visited = new boolean[map.getWidth()][map.getHeight()];

        class Node {
            int x;
            int y;
            int dist;

            Node(int x, int y, int dist) {
                this.x = x;
                this.y = y;
                this.dist = dist;
            }
        }

        var queue = new ArrayList<Node>();
        visited[src.getX()][src.getY()] = true;

        queue.add(new Node(src.getX(), src.getY(), 0));

        while (!queue.isEmpty()) {
            var node = queue.remove(0);

            if (node.x == dst.getX() && node.y == dst.getY()) {
                System.out.println("dst: " + dst + " value = " + node.dist);
                map.setCell(dst.getX(), dst.getY(), map.getCell(dst.getX(), dst.getY()) + node.dist);
                break;
            }

            for (int i = 0; i < 4; i++) {
                if (isValid(map, visited, node.x + row[i], node.y + col[i])) {
                    visited[node.x + row[i]][node.y + col[i]] = true;

                    queue.add(new Node(node.x + row[i], node.y + col[i], node.dist + 1));
                }
            }
        }
    }

    private boolean isValid(OurMap map, boolean[][] visited, int x, int y) {
        return (y >= 0) && (y < map.getHeight()) && (x >= 0) && (x < map.getWidth()) && !visited[x][y] && map.getCell(x, y) < 100;
    }

    private Point findPath(OurMap map, Point start, Point end) {
        var previous = end;
        var current = end;

        while (!current.equals(start)) {
            var left = new PointImpl(current.getX() - 1, current.getY());
            var top = new PointImpl(current.getX(), current.getY() + 1);
            var right = new PointImpl(current.getX() + 1, current.getY());
            var bottom = new PointImpl(current.getX(), current.getY() - 1);

            if (
                left.getX() >= 0 && left.getX() <= map.getWidth()
                    && left.getY() >= 0 && left.getY() <= map.getHeight()
            ) {
                if (map.getCell(current) - map.getCell(left) == 1) {
                    previous = current;
                    current = left;
                }
            }

            if (
                top.getX() >= 0 && top.getX() <= map.getWidth()
                    && top.getY() >= 0 && top.getY() <= map.getHeight()
            ) {
                if (map.getCell(current) - map.getCell(top) == 1) {
                    previous = current;
                    current = top;
                }
            }

            if (
                right.getX() >= 0 && right.getX() <= map.getWidth()
                    && right.getY() >= 0 && right.getY() <= map.getHeight()
            ) {
                if (map.getCell(current) - map.getCell(right) == 1) {
                    previous = current;
                    current = right;
                }
            }

            if (
                bottom.getX() >= 0 && bottom.getX() <= map.getWidth()
                    && bottom.getY() >= 0 && bottom.getY() <= map.getHeight()
            ) {
                if (map.getCell(current) - map.getCell(bottom) == 1) {
                    previous = current;
                    current = bottom;
                }
            }
        }

        return previous;
    }
}