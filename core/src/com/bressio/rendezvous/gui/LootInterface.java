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
import com.bressio.rendezvous.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

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

    private ArrayList<EntityObject> lootItems;
    private ArrayList<EntityObject> inventoryItems;

    public LootInterface(SpriteBatch spriteBatch, Internationalization i18n, ResourceHandler resources, Match match,
                         ArrayList<EntityObject> lootItems, ArrayList<EntityObject> inventoryItems) {
        this.match = match;
        this.resources = resources;
        this.i18n = i18n;
        this.lootItems = lootItems;
        this.inventoryItems = inventoryItems;
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        upperStage = new Stage(viewport, spriteBatch);
        skin = resources.getSkin(ResourceHandler.SkinPaths.WINDOW_SKIN);

        exchangeIcon = new Image(resources.getTexture(ResourceHandler.TexturePath.EXCHANGE_ICON));
        exchangeIcon.setPosition(pCenter(GAME_WIDTH) - pCenter(exchangeIcon.getWidth()),
                pCenter(GAME_HEIGHT) - pCenter(exchangeIcon.getHeight()));

        forgeInventoryInterface();
        forgeLootInterface();

        upperStage.addActor(exchangeIcon);
    }

    private void forgeLootInterface() {
        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) - (width + 50), pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(i18n.getBundle().get("loot"), skin)).padTop(20).row();

        ImageButton item1, item2, item3, item4;
        item3 = null;
        item4 = null;

        TextureRegion myTextureRegion = new TextureRegion(lootItems.get(0).getIcon());
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item1 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(lootItems.get(1).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item2 = new ImageButton(myTexRegionDrawable);

        if (lootItems.size() > 2) {
            myTextureRegion = new TextureRegion(lootItems.get(2).getIcon());
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            item3 = new ImageButton(myTexRegionDrawable);

            myTextureRegion = new TextureRegion(lootItems.get(3).getIcon());
            myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
            item4 = new ImageButton(myTexRegionDrawable);
        }

        window.add(item1).padTop(10).row();
        window.add(new Label(lootItems.get(0).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item2).padTop(10).row();
        window.add(new Label(lootItems.get(1).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        if (lootItems.size() > 2) {
            window.add(item3).padTop(10).row();
            window.add(new Label(lootItems.get(2).getName(),
                    new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                            14, false), Color.WHITE))).row();

            window.add(item4).padTop(10).row();
            window.add(new Label(lootItems.get(3).getName(),
                    new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                            14, false), Color.WHITE))).row();
        }

        stage.addActor(window);
    }

    private void forgeInventoryInterface() {
        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) + 50, pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(i18n.getBundle().get("inventory"), skin)).padTop(20).row();

        ImageButton item1, item2, item3, item4, item5, item6;

        TextureRegion myTextureRegion = new TextureRegion(inventoryItems.get(0).getIcon());
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item1 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(inventoryItems.get(1).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item2 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(inventoryItems.get(2).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item3 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(inventoryItems.get(3).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item4 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(inventoryItems.get(4).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item5 = new ImageButton(myTexRegionDrawable);

        myTextureRegion = new TextureRegion(inventoryItems.get(5).getIcon());
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        item6 = new ImageButton(myTexRegionDrawable);

        window.add(item1).padTop(10).row();
        window.add(new Label(inventoryItems.get(0).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item2).padTop(10).row();
        window.add(new Label(inventoryItems.get(1).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item3).padTop(10).row();
        window.add(new Label(inventoryItems.get(2).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item4).padTop(10).row();
        window.add(new Label(inventoryItems.get(3).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item5).padTop(10).row();
        window.add(new Label(inventoryItems.get(4).getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();

        window.add(item6).padTop(10).row();
        window.add(new Label(inventoryItems.get(5).getName(),
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
