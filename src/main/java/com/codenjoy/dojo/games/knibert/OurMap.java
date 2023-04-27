package com.codenjoy.dojo.games.knibert;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OurMap {
    private final int[] cells;
    private final int width;
    private final int height;

    public OurMap(int width, int height, List<Point> walls, List<Point> stones, List<Point> tail) {
        cells = new int[height * width];
        this.width = width;
        this.height = height;

        for (var wall : walls) {
            setCell(wall, 100);
        }

        for (var stone : stones) {
            setCell(stone, 100);
        }

        for (var p : tail) {
            setCell(p, 100);
        }
    }

    public OurMap(int[] cells, int width, int height) {
        this.cells = cells;
        this.width = width;
        this.height = height;
    }

    public void setCell(int x, int y, int value) {
        cells[width * y + x] = value;
    }

    public void setCell(Point p, int value) {
        cells[width * p.getY() + p.getX()] = value;
    }

    public int getCell(int x, int y) {
        return cells[width * y + x];
    }

    public int getCell(Point p) {
        return cells[width * p.getY() + p.getX()];
    }

    public OurMap clone() {
        return new OurMap(Arrays.copyOf(cells, cells.length), width, height);
    }

    @Override
    public String toString() {
        var buffer = "";

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buffer += getCell(x, y) + "\t";
            }

            buffer += "\n";
        }

        return buffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}