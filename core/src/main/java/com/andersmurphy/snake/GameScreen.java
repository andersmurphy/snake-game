package com.andersmurphy.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by anders on 14/04/2016.
 */
public class GameScreen extends ScreenAdapter {
	private static final float MOVE_TIME = 1F;
	private static final int SNAKE_MOVEMENT = 32;

	private int snakeX = 0;
	private int snakeY = 0;
	private float timer = MOVE_TIME;
	private Batch batch;
	private Texture snakeHead;

	@Override
	public void show() {
		batch = new SpriteBatch();
		snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
	}

	@Override
	public void render(float delta) {
		timer -= delta;

		if (timer <= 0) {
			timer = MOVE_TIME;
			snakeX += SNAKE_MOVEMENT;
		}

		if (isXOutOfBounds(snakeX)) {
			snakeX = 0;
		}

		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(snakeHead, snakeX, snakeY);
		batch.end();
	}

	private boolean isXOutOfBounds(final int x) {
		return x >= Gdx.graphics.getWidth();
	}


}

