package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.languages.Internationalization;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class MatchMap implements Disposable {

    private Stage stage;
    private Viewport viewport;
    private Image map;
    private Image playerMark;

    public MatchMap(SpriteBatch spriteBatch, Internationalization i18n, ResourceHandler resources) {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        map = new Image(new Texture("textures/gui/maps/map-expanded.png"));
        map.setPosition(pCenter(GAME_WIDTH) - pCenter( map.getWidth()),
                pCenter(GAME_HEIGHT) - pCenter(map.getHeight()));
        playerMark = new Image(new Texture("textures/gui/maps/player-mark.png"));

        stage.addActor(map);
        stage.addActor(playerMark);
    }

    public void update(float delta, Vector2 playerPosition) {
        playerMark.setPosition(
                pCenter(GAME_WIDTH) -
                        pCenter(map.getWidth()) -
                        pCenter(playerMark.getWidth()) +
                        playerPosition.x * 7.3f,
                pCenter(GAME_HEIGHT) -
                        pCenter(map.getHeight()) -
                        pCenter(playerMark.getHeight()) +
                        playerPosition.y * 7.3f);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
