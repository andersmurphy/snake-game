package com.andersmurphy.snake;

import java.awt.Point;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * Created by anders on 16/04/2016.
 */
public interface Movement {
	Function<Point, Point> getMovementFunctionFor(LinkedList<Integer> keyPressed);
}
