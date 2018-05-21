package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.helpers.PlayerSettings;

public class HUD {

    public Stage stage;
    private Viewport viewport;
    private final int WIDTH;
    private final int HEIGHT;

    private Label label;

    public HUD(SpriteBatch spriteBatch) {
        WIDTH = PlayerSettings.GAME_WIDTH;
        HEIGHT = PlayerSettings.GAME_HEIGHT;

        viewport = new FitViewport(WIDTH, HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        label = new Label("100 alive", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(label).expandX().padTop(5);

        stage.addActor(table);

    }
}
