package com.andersmurphy.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anders on 22/04/2016.
 */
@Module
public class ScreenModule {
	private static final float WORLD_WIDTH = 640;
	private static final float WORLD_HEIGHT = 480;

	@Provides
	public Batch provideSpriteBatch() {
		return new SpriteBatch();
	}

	@Provides
	public BitmapFont provideBitmapFont() {
		return new BitmapFont();
	}

	@Provides
	public Viewport provideViewport(Camera camera) {
		return new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
	}

	@Provides
	public Camera provideCamera() {
		Camera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
		camera.update();
		return camera;
	}
}
