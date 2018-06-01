package com.bressio.rendezvous.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.languages.Internationalization;
import com.bressio.rendezvous.scenes.MainMenu;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class PauseMenu implements Disposable {

    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    private Window window;
    private Match match;

    private int width = 400;
    private int height = 250;

    public PauseMenu(SpriteBatch spriteBatch, Internationalization i18n, ResourceHandler resources, Match match) {
        this.match = match;
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        skin = resources.getSkin(ResourceHandler.SkinPath.WINDOW_SKIN);

        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) - pCenter(width), pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        Label title = new Label(i18n.getBundle().get("paused"), skin);
        window.add(title).row();

        TextButton resumeButton = new TextButton(i18n.getBundle().get("pauseMenuResume"), skin);
        TextButton backButton = new TextButton(i18n.getBundle().get("pauseMenuBack"), skin);
        TextButton exitButton = new TextButton(i18n.getBundle().get("pauseMenuQuit"), skin);

        resumeButton.getLabel().setFontScale(.7f);
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                match.delegateInputProcessor();
                match.setState(Match.GameState.RUNNING);
            }
        });

        backButton.getLabel().setFontScale(.7f);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Rendezvous game = (Rendezvous)Gdx.app.getApplicationListener();
                game.setScreen(new MainMenu(game));
            }
        });

        exitButton.getLabel().setFontScale(.7f);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        window.add(resumeButton).padTop(20).row();
        window.add(backButton).padTop(10).row();
        window.add(exitButton).padTop(10).row();

        stage.addActor(window);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
