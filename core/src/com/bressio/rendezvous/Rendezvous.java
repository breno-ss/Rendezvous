package com.bressio.rendezvous;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bressio.rendezvous.scenes.MainMenu;

public class Rendezvous extends Game {

    private SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        setScreen(new MainMenu(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
