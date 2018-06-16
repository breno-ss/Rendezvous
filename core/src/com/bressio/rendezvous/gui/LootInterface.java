package com.bressio.rendezvous.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.entities.objects.EntityObject;
import com.bressio.rendezvous.graphics.FontGenerator;
import com.bressio.rendezvous.graphics.ResourceHandler;
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

    private Image exchangeIcon;

    private ArrayList<EntityObject> lootItems;
    private ArrayList<EntityObject> inventoryItems;

    public LootInterface(Match match, ArrayList<EntityObject> lootItems, ArrayList<EntityObject> inventoryItems) {
        this.match = match;
        this.lootItems = lootItems;
        this.inventoryItems = inventoryItems;
        setupStages();
        forgeInventoryInterface();
        forgeLootInterface();
        forgeUpperStage();
    }

    private void setupStages() {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, match.getBatch());
        upperStage = new Stage(viewport, match.getBatch());
    }

    private void forgeInventoryInterface() {
        skin = match.getResources().getSkin(ResourceHandler.SkinPaths.WINDOW_SKIN);

        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) + 50, pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(match.getI18n().getBundle().get("inventory"), skin)).padTop(20).row();

        for (EntityObject inventoryItem : inventoryItems) {
            insertItemToWindow(window, inventoryItem);
        }

        stage.addActor(window);
    }

    private void forgeLootInterface() {
        window = new Window("", skin);
        window.setSize(width, height);
        window.setPosition(pCenter(GAME_WIDTH) - (width + 50), pCenter(GAME_HEIGHT) - pCenter(height));
        window.padTop(-20);

        window.add(new Label(match.getI18n().getBundle().get("loot"), skin)).padTop(20).row();

        for (EntityObject lootItem : lootItems) {
            insertItemToWindow(window, lootItem);
        }

        stage.addActor(window);
    }

    private void forgeUpperStage() {
        exchangeIcon = new Image(match.getResources().getTexture(ResourceHandler.TexturePath.EXCHANGE_ICON));
        exchangeIcon.setPosition(pCenter(GAME_WIDTH) - pCenter(exchangeIcon.getWidth()),
                pCenter(GAME_HEIGHT) - pCenter(exchangeIcon.getHeight()));
        upperStage.addActor(exchangeIcon);
    }

    private void insertItemToWindow(Window window, EntityObject item) {
        TextureRegion myTextureRegion = new TextureRegion(item.getIcon());
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton itemImage = new ImageButton(myTexRegionDrawable);

        window.add(itemImage).padTop(10).row();
        window.add(new Label(item.getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();
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
