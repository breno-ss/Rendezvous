package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.helpers.PhysicsManager;
import com.bressio.rendezvous.helpers.PlayerSettings;

public class MainMenu implements Screen {

    private final int WIDTH;
    private final int HEIGHT;
    private final int PPM;

    private Rendezvous game;

    public MainMenu(Rendezvous game) {
        WIDTH = PlayerSettings.GAME_WIDTH;
        HEIGHT = PlayerSettings.GAME_HEIGHT;
        PPM = PhysicsManager.PPM;

        this.game = game;

    }

    private void handleInput(float delta) {

    }

    private void update(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
