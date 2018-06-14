package com.bressio.rendezvous.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.graphics.FontGenerator;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.languages.Internationalization;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class LootInterface implements Disposable {

    private Stage stage;
    private Stage upperStage;
    private Viewport viewport;
    private Skin skin;

    private Window window;
    private Match match;

    private int width = 300;
    private int height = 400;

    private ResourceHandler resources;
    private Internationalization i18n;

    private Image exchangeIcon;

    public LootInterface(SpriteBatch spriteBatch, Internationalization i18n, ResourceHandler resources, Match match) {
        this.match = match;
        this.resources = resources;
        this.i18n = i18n;
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        upperStage = new Stage(viewport, spriteBatch);
        skin = resources.getSkin(ResourceHandler.SkinPaths.WINDOW_SKIN);

        exchangeIcon = new Image(resources.getTexture(ResourceHandler.TexturePath.EXCHANGE_ICON));
        exchangeIcon.setPosition(pCenter(GAME_WIDTH) - pCenter(exchangeIcon.getWidth()),
                pCenter(GAME_HEIGHT) - pCenter(exchangeIcon.getHeight()));

        forgeLootInterface();
        forgeInventoryInterface();

        upperStage.addActor(exchangeIcon);
    }

    private void forgeLootInterface() {
        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) - (width + 50), pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(i18n.getBundle().get("loot"), skin)).padTop(20).row();

        TextureRegion myTextureRegion = new TextureRegion(resources.getTexture(ResourceHandler.TexturePath.INTERACTION_BUTTON));
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        ImageButton item1 = new ImageButton(myTexRegionDrawable);
        ImageButton item2 = new ImageButton(myTexRegionDrawable);
        ImageButton item3 = new ImageButton(myTexRegionDrawable);
        ImageButton item4 = new ImageButton(myTexRegionDrawable);
        ImageButton item5 = new ImageButton(myTexRegionDrawable);
        ImageButton item6 = new ImageButton(myTexRegionDrawable);

        window.add(item1).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item2).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item3).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item4).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item5).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item6).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        stage.addActor(window);
    }

    private void forgeInventoryInterface() {
        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) + 50, pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(i18n.getBundle().get("inventory"), skin)).padTop(20).row();

        TextureRegion myTextureRegion = new TextureRegion(resources.getTexture(ResourceHandler.TexturePath.INTERACTION_BUTTON));
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        ImageButton item1 = new ImageButton(myTexRegionDrawable);
        ImageButton item2 = new ImageButton(myTexRegionDrawable);
        ImageButton item3 = new ImageButton(myTexRegionDrawable);
        ImageButton item4 = new ImageButton(myTexRegionDrawable);
        ImageButton item5 = new ImageButton(myTexRegionDrawable);
        ImageButton item6 = new ImageButton(myTexRegionDrawable);

        window.add(item1).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item2).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item3).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item4).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item5).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item6).padTop(10).row();
        window.add(new Label("EMPTY",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        stage.addActor(window);
    }

    public void update(float delta) {
        handleInput(delta);
    }

    private void handleInput(float delta) {
        if (Gdx.app.getInput().isKeyJustPressed(Input.Keys.E) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.A) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.S) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.W) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.D) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.ESCAPE)){
            match.delegateInputProcessor();
            match.setCursor(ResourceHandler.PixmapPath.MATCH_CURSOR, true);
            match.setState(Match.GameState.RUNNING);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getUpperStage() {
        return upperStage;
    }
}
