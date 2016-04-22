package com.andersmurphy.snake;

import com.andersmurphy.snake.game.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
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
}
