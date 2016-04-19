package com.andersmurphy.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
 * Created by anders on 14/04/2016.
 */
public final class GameScreen extends ScreenAdapter {
	private static final float MOVE_TIME = 0.2F;
	private static final int GRID_CELL = 32;
	private static final String GAME_OVER_TEXT = "Game Over... Press space to Restart!";
	private Movement snakeMovement;
	private Point snakePosition = new Point(0, 0);
	private LinkedList<Integer> snakeDirection = new LinkedList<>();
	private float timer = MOVE_TIME;
	private Batch batch;
	private Texture snakeHead;
	private Texture snakeBody;
	private Texture apple;
	private boolean isAppleAvailable = false;
	private Point applePosition = new Point(0, 0);
	private Array<BodyPart> bodyParts = new Array<BodyPart>();
	private ShapeRenderer shapeRenderer;
	private boolean hasHit = false;
	private BitmapFont bitmapFont;
	private GlyphLayout layout = new GlyphLayout();

	private enum STATE {
		PLAYING, GAME_OVER
	}

	private STATE state = STATE.PLAYING;

	private final Map<STATE, Consumer<Float>> gameStates = new HashMap<>();

	@Override
	public void show() {
		batch = new SpriteBatch();
		snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
		snakeBody = new Texture(Gdx.files.internal("snakebody.png"));
		apple = new Texture(Gdx.files.internal("apple.png"));
		shapeRenderer = new ShapeRenderer();
		snakeDirection.add(Input.Keys.RIGHT);
		snakeDirection.add(Input.Keys.RIGHT);
		this.snakeMovement = new SnakeMovement(Gdx.graphics);

		gameStates.put(STATE.PLAYING, delta -> playing(delta));
		gameStates.put(STATE.GAME_OVER, delta -> gameOver(delta));

		bitmapFont = new BitmapFont();

	}

	@Override
	public void render(float delta) {
		gameStates.get(state).accept(delta);
		clearScreen();
		draw();

	}

	private void playing(float delta) {
		queryInput();
		updateSnake(delta);
		checkAppleCollision();
		checkAndPlaceApple();
	}

	private void gameOver(Float delta) {
		checkForRestart();
	}

	private void updateSnake(float delta) {
		timer -= delta;
		if (timer <= 0) {
			timer = MOVE_TIME;
			Point snakesOldPosition = snakePosition;
			snakePosition = snakeMovement.getMovementFunctionFor(snakeDirection).apply(snakePosition);
			updateBodyPartsPosition(snakesOldPosition);
			checkSnakeBodyCollision();
		}
	}

	private void checkAppleCollision() {
		if (isAppleAvailable && snakePosition.equals(applePosition)) {
			bodyParts.insert(0, new BodyPart(snakeBody, snakePosition));
			isAppleAvailable = false;
		}
	}

	private void draw() {
		batch.begin();
		batch.draw(snakeHead, snakePosition.x, snakePosition.y);

		StreamSupport.stream(bodyParts.spliterator(), false)
				.filter(bodyPart -> !snakePosition.equals(bodyPart.bodyPartPosition))
				.forEach(bodyPart -> batch.draw(bodyPart.texture, bodyPart.bodyPartPosition.x, bodyPart.bodyPartPosition.y));

		if (isAppleAvailable) {
			batch.draw(apple, applePosition.x, applePosition.y);
		}

		if (state == STATE.GAME_OVER) {
			layout.setText(bitmapFont, GAME_OVER_TEXT);
			bitmapFont.draw(batch, GAME_OVER_TEXT, (Gdx.graphics.getWidth() - layout.width) / 2, (Gdx.graphics.getHeight() - layout.height) / 2);
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
				applePosition.x = MathUtils.random(Gdx.graphics.getWidth() / GRID_CELL - 1) * GRID_CELL;
				applePosition.y = MathUtils.random(Gdx.graphics.getHeight() / GRID_CELL - 1) * GRID_CELL;
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
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !snakeDirection.getLast().equals(Input.Keys.LEFT))
			snakeDirection.add(Input.Keys.LEFT);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !snakeDirection.getLast().equals(Input.Keys.RIGHT))
			snakeDirection.add(Input.Keys.RIGHT);
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && !snakeDirection.getLast().equals(Input.Keys.UP))
			snakeDirection.add(Input.Keys.UP);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !snakeDirection.getLast().equals(Input.Keys.DOWN))
			snakeDirection.add(Input.Keys.DOWN);

		while (snakeDirection.size() > 2 && bodyParts.size > 0) {
			snakeDirection.removeFirst();
		}
	}

	private void checkSnakeBodyCollision() {
		for (BodyPart bodyPart : bodyParts) {
			if (bodyPart.bodyPartPosition.equals(snakePosition)) {
				state = STATE.GAME_OVER;
			}
		}
	}

	private void checkForRestart() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) doRestart();
	}

	private void doRestart() {
		state = STATE.PLAYING;
		bodyParts.clear();
		timer = MOVE_TIME;
		isAppleAvailable = false;
	}
}

