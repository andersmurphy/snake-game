package com.andersmurphy.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.awt.*;

/**
 * Created by anders on 15/04/2016.
 */
public final class BodyPart {
	public Point bodyPartPosition;
	public final Texture texture;

	public BodyPart(Texture texture, Point snakePosition) {
		this.texture = texture;
		this.bodyPartPosition = snakePosition;
	}

	public void updateBodyPosition(Point bodyPartPosition) {
		this.bodyPartPosition = bodyPartPosition;
	}
}
