package com.andersmurphy.snake;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import org.junit.Test;

import java.awt.Point;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

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
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.LEFT);
		linkedList.add(Input.Keys.UP);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, SNAKE_MOVEMENT_SPEED))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_up_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.LEFT);
		linkedList.add(Input.Keys.UP);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, screenHeight()));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_down() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.LEFT);
		linkedList.add(Input.Keys.DOWN);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 32));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_down_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.LEFT);
		linkedList.add(Input.Keys.DOWN);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, screenHeight()))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_left() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.UP);
		linkedList.add(Input.Keys.LEFT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(32, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_left_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.UP);
		linkedList.add(Input.Keys.LEFT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(screenWidth(), 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_right() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.UP);
		linkedList.add(Input.Keys.RIGHT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(SNAKE_MOVEMENT_SPEED, 0))));
	}

	@Test
	public void get_movement_function_returns_correct_movement_function_for_right_on_boundary() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.UP);
		linkedList.add(Input.Keys.RIGHT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(screenWidth(), 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void snake_cant_double_back_on_itself_when_going_up() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.UP);
		linkedList.add(Input.Keys.DOWN);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 32))));
	}

	@Test
	public void snake_cant_double_back_on_itself_when_going_down() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getHeight()).toReturn(screenHeight());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.DOWN);
		linkedList.add(Input.Keys.UP);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 32));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void snake_cant_double_back_on_itself_when_going_left() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.LEFT);
		linkedList.add(Input.Keys.RIGHT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(32, 0));

		assertThat(actualPoint, is(equalTo(new Point(0, 0))));
	}

	@Test
	public void snake_cant_double_back_on_itself_when_going_right() {
		Graphics graphics = mock(Graphics.class);
		stub(graphics.getWidth()).toReturn(screenWidth());
		SnakeMovement snakeMovement = new SnakeMovement(graphics);
		LinkedList<Integer> linkedList = new LinkedList<>();
		linkedList.add(Input.Keys.RIGHT);
		linkedList.add(Input.Keys.LEFT);

		Point actualPoint = snakeMovement.getMovementFunctionFor(linkedList).apply(new Point(0, 0));

		assertThat(actualPoint, is(equalTo(new Point(32, 0))));
	}

	private Integer screenHeight() {
		return 320;
	}

	private Integer screenWidth() {
		return 320;
	}
}