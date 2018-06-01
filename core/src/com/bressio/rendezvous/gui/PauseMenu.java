package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.languages.Internationalization;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class PauseMenu implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Skin skin;

    private Window window;

    private int width = 300;
    private int height = 200;

    public PauseMenu(SpriteBatch spriteBatch, Internationalization i18n, ResourceHandler resources) {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        skin = resources.getSkin(ResourceHandler.SkinPath.WINDOW_SKIN);

        window = new Window(i18n.getBundle().get("paused"), skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) - pCenter(width), pCenter(GAME_HEIGHT) - pCenter(height));

        stage.addActor(window);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
