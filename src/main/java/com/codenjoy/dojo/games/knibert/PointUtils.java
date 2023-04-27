package com.codenjoy.dojo.games.knibert;

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;

public class PointUtils {
    public static Point addPoint(Point a, Point b) {
        return new PointImpl(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Point subPoint(Point a, Point b) {
        return new PointImpl(a.getX() - b.getX(), a.getY() - b.getY());
    }
}