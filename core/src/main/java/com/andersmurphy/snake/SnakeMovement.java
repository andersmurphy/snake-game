package com.andersmurphy.snake;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by anders on 15/04/2016.
 */
public final class SnakeMovement implements Movement {
	private static final int SNAKE_MOVEMENT_UNIT = 32;

	private final Map<Integer, Function<Point, Point>> movement = new HashMap<>();
	private final Graphics graphics;

	public SnakeMovement(Graphics graphics) {
		this.graphics = graphics;
		movement.put(Input.Keys.UP, upMovementFunction());
		movement.put(Input.Keys.DOWN, downMovementFunction());
		movement.put(Input.Keys.RIGHT, rightMovementFunction());
		movement.put(Input.Keys.LEFT, leftMovementFunction());
	}

	private Function<Point, Point> upMovementFunction() {
		return point -> point.y >= graphics.getHeight() ? new Point(point.x, 0) : new Point(point.x, point.y + SNAKE_MOVEMENT_UNIT);
	}

	private Function<Point, Point> downMovementFunction() {
		return point -> point.y <= 0 ? new Point(point.x, graphics.getHeight()) : new Point(point.x, point.y - SNAKE_MOVEMENT_UNIT);
	}

	private Function<Point, Point> rightMovementFunction() {
		return point -> point.x >= graphics.getWidth() ? new Point(0, point.y) : new Point(point.x + SNAKE_MOVEMENT_UNIT, point.y);
	}

	private Function<Point, Point> leftMovementFunction() {
		return point -> point.x <= 0 ? new Point(graphics.getWidth(), point.y) : new Point(point.x - SNAKE_MOVEMENT_UNIT, point.y);
	}

	@Override
	public Function<Point, Point> getMovementFunctionFor(int keyPressed) {
		return movement.containsKey(keyPressed) ? movement.get(keyPressed) : point -> point;
	}
}
