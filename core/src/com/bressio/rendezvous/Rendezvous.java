package com.bressio.rendezvous;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bressio.rendezvous.scenes.Match;

public class Rendezvous extends Game {

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Match(this));
	}

	@Override
	public void render () {
        super.render();
	}

	@Override
	public void dispose () {

	}

    public SpriteBatch getBatch() {
        return batch;
    }
}
