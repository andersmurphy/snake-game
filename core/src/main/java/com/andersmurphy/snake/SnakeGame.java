package com.andersmurphy.snake;

import com.andersmurphy.snake.ScreenComponent;
import com.andersmurphy.snake.ScreenModule;
import com.andersmurphy.snake.game.GameScreen;
import com.badlogic.gdx.Game;

public final class SnakeGame extends Game {

	private ScreenComponent screenComponent;

	@Override
	public void create () {
		buildGraph();
		GameScreen gameScreen = new GameScreen();
		screenComponent.inject(gameScreen);
		setScreen(gameScreen);
	}

	private void buildGraph() {
		screenComponent = DaggerScreenComponent.builder()
				.screenModule(new ScreenModule())
				.build();
	}

	public ScreenComponent component() {
		return screenComponent;
	}

}
