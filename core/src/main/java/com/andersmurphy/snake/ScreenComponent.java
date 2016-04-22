package com.andersmurphy.snake;

import com.andersmurphy.snake.game.GameScreen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by anders on 22/04/2016.
 */
@Singleton
@Component(modules={ScreenModule.class})
public interface ScreenComponent {
	void inject(GameScreen gameScreen);

	Batch batch();
	BitmapFont bitmapFont();
	Viewport  viewport();
	Camera camera();
}
