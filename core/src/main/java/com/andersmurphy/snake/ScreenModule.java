package com.andersmurphy.snake;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anders on 22/04/2016.
 */
@Module
public class ScreenModule {

	@Provides
	public Batch provideSpriteBatch() {
		return new SpriteBatch();
	}
}
