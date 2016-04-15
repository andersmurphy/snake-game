package com.andersmurphy.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by anders on 14/04/2016.
 */
public class GameScreen extends ScreenAdapter {
	private static final float MOVE_TIME = 0.5F;
	private static final int SNAKE_MOVEMENT = 32;

	private static final Map<String, Function<Point, Point>> movement = new HashMap<>();

	static {
		movement.put(Directions.UP,
				point -> point.y >= Gdx.graphics.getHeight() ? new Point(point.x, 0) : new Point(point.x, point.y + SNAKE_MOVEMENT));
		movement.put(Directions.DOWN,
				point -> point.y <= 0 ? new Point(point.x, Gdx.graphics.getHeight()) : new Point(point.x, point.y - SNAKE_MOVEMENT));
		movement.put(Directions.RIGHT,
				point -> point.x >= Gdx.graphics.getWidth() ? new Point(0, point.y) : new Point(point.x + SNAKE_MOVEMENT, point.y));
		movement.put(Directions.LEFT,
				point -> point.x <= 0 ? new Point(Gdx.graphics.getWidth(), point.y) : new Point(point.x - SNAKE_MOVEMENT, point.y));
	}

	private Point snakePosition = new Point(0, 0);
	private String snakeDirection = Directions.RIGHT;
	private float timer = MOVE_TIME;
	private Batch batch;
	private Texture snakeHead;
	private Texture snakeBody;
	private Texture apple;
	private boolean isAppleAvailable = false;
	private Point applePosition = new Point(0, 0);
	private Array<BodyPart> bodyParts = new Array<>();

	@Override
	public void show() {
		batch = new SpriteBatch();
		snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
		snakeBody = new Texture(Gdx.files.internal("snakebody.png"));
		apple = new Texture(Gdx.files.internal("apple.png"));
	}

	@Override
	public void render(float delta) {
		queryInput();
		timer -= delta;
		if (timer <= 0) {
			timer = MOVE_TIME;
			Point snakesOldPosition = snakePosition;
			snakePosition = movement.get(snakeDirection).apply(snakePosition);
			updateBodyPartsPosition(snakesOldPosition);
			if (isAppleAvailable && snakePosition.equals(applePosition)) {
				bodyParts.insert(0, new BodyPart(snakeBody, snakePosition));
				isAppleAvailable = false;
			}
		}
		checkAndPlaceApple();
		clearScreen();
		draw();
	}

	private void draw() {
		batch.begin();
		batch.draw(snakeHead, snakePosition.x, snakePosition.y);
		for (BodyPart bodyPart : bodyParts) {
			if (!snakePosition.equals(bodyPart.bodyPartPosition)) {
				batch.draw(bodyPart.texture, bodyPart.bodyPartPosition.x, bodyPart.bodyPartPosition.y);
			}
		}
		if (isAppleAvailable) {
			batch.draw(apple, applePosition.x, applePosition.y);
		}
		batch.end();
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void checkAndPlaceApple() {
		if (!isAppleAvailable) {
			do {
				applePosition.x = MathUtils.random(Gdx.graphics.getWidth() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
				applePosition.y = MathUtils.random(Gdx.graphics.getHeight() / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
				isAppleAvailable = true;
			} while (snakePosition.equals(applePosition));
		}
	}

	private void updateBodyPartsPosition(Point snakePosition) {
		if (bodyParts.size > 0) {
			BodyPart bodyPart = bodyParts.removeIndex(0);
			bodyPart.updateBodyPosition(snakePosition);
			bodyParts.add(bodyPart);
		}
	}

	private void queryInput() {
		boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

		if (lPressed) snakeDirection = Directions.LEFT;
		if (rPressed) snakeDirection = Directions.RIGHT;
		if (uPressed) snakeDirection = Directions.UP;
		if (dPressed) snakeDirection = Directions.DOWN;
	}

}

