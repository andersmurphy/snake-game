package com.andersmurphy.snake;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import org.junit.Test;

import java.awt.*;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

/**
 * Created by anders on 17/04/2016.
 */
public class SnakeMovementTest {

	public static final int SNAKE_MOVEMENT_SPEED = 32;

	@Test
	public void get_movement_function_returns_correct_movement_function_for_up() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.UP).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, SNAKE_MOVEMENT_SPEED))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_up_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.UP).apply(new Point(0, screenHeight()));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_down() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.DOWN).apply(new Point(0, SNAKE_MOVEMENT_SPEED));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_down_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.DOWN).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, screenHeight()))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_left() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.LEFT).apply(new Point(SNAKE_MOVEMENT_SPEED, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_left_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.LEFT).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(screenWidth(), 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_right() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.RIGHT).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(SNAKE_MOVEMENT_SPEED, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_right_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);

		Point actualPoint = snakeMovement.getMovementFunctionFor(Input.Keys.RIGHT).apply(new Point(screenWidth(), 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	private Integer screenHeight() {
		return 320;
	}

	private Integer screenWidth() {
		return 320;
	}
}