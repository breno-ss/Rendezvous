package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.graphics.ResourceHandler;

import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class HUD implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Label label;
    private I18NBundle bundle;

    public HUD(SpriteBatch spriteBatch, ResourceHandler resources) {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        bundle = resources.getI18n().getBundle();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        label = new Label(bundle.format("alive", 100), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(label).expandX().padTop(5);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
