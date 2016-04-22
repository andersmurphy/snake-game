package com.andersmurphy.snake.game;

import com.andersmurphy.snake.game.Movement;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * Created by anders on 15/04/2016.
 */
public final class SnakeMovement implements Movement {
	private static final int SNAKE_MOVEMENT_UNIT = 32;

	private final Map<Integer, Function<Point, Point>> movement = new HashMap<>();
	private final List<List<Integer>> oppositeKeys = new ArrayList<>();
	private final Graphics graphics;

	public SnakeMovement(Graphics graphics) {
		this.graphics = graphics;

		movement.put(Input.Keys.UP,
				point -> point.y >= graphics.getHeight() ? new Point(point.x, 0) : new Point(point.x, point.y + SNAKE_MOVEMENT_UNIT));
		movement.put(Input.Keys.DOWN,
				point -> point.y <= 0 ? new Point(point.x, graphics.getHeight()) : new Point(point.x, point.y - SNAKE_MOVEMENT_UNIT));
		movement.put(Input.Keys.RIGHT,
				point -> point.x >= graphics.getWidth() ? new Point(0, point.y) : new Point(point.x + SNAKE_MOVEMENT_UNIT, point.y));
		movement.put(Input.Keys.LEFT,
				point -> point.x <= 0 ? new Point(graphics.getWidth(), point.y) : new Point(point.x - SNAKE_MOVEMENT_UNIT, point.y));

		oppositeKeys.add(asList(Input.Keys.UP, Input.Keys.DOWN));
		oppositeKeys.add(asList(Input.Keys.DOWN, Input.Keys.UP));
		oppositeKeys.add(asList(Input.Keys.RIGHT, Input.Keys.LEFT));
		oppositeKeys.add(asList(Input.Keys.LEFT, Input.Keys.RIGHT));

	}

	@Override
	public Function<Point, Point> getMovementFunctionFor(LinkedList<Integer> keysPressed) {
		if (oppositeKeys.contains(keysPressed)) {
			return movement.get(keysPressed.getFirst());
		} else {
			return movement.get(keysPressed.getLast());
		}
	}
}
