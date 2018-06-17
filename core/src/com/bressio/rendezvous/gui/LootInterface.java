package com.bressio.rendezvous.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.entities.objects.Empty;
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

    private Window inventoryWindow;
    private Window lootWindow;
    private Match match;

    private int width = 300;
    private int height = 400;

    private ArrayList<EntityObject> lootItems;
    private ArrayList<EntityObject> inventoryItems;

    private EntityObject selectedLootItem;
    private EntityObject selectedInventoryItem;

    public LootInterface(Match match, ArrayList<EntityObject> lootItems, ArrayList<EntityObject> inventoryItems) {
        this.match = match;
        this.lootItems = lootItems;
        this.inventoryItems = inventoryItems;
        init();
        setupStages();
        forgeInventoryInterface();
        forgeLootInterface();
        forgeUpperStage();
    }

    private void init() {
        selectedInventoryItem = null;
        selectedLootItem = null;
    }

    private void setupStages() {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, match.getBatch());
        upperStage = new Stage(viewport, match.getBatch());
    }

    private void forgeInventoryInterface() {
        skin = match.getResources().getSkin(ResourceHandler.SkinPaths.WINDOW_SKIN);

        inventoryWindow = new Window("", skin, "no-background");
        inventoryWindow.setSize(width, height);
        inventoryWindow.setPosition(pCenter(GAME_WIDTH) + 50, pCenter(GAME_HEIGHT) - pCenter(height));
        inventoryWindow.padTop(-20);

        inventoryWindow.add(new Label(match.getI18n().getBundle().get("inventory"), skin)).padTop(20).row();

        for (int i = 0; i < inventoryItems.size(); i++) {
            insertItemToWindow(inventoryWindow, i, true);
        }

        stage.addActor(inventoryWindow);
    }

    private void forgeLootInterface() {
        lootWindow = new Window("", skin, "no-background");
        lootWindow.setSize(width, height);
        lootWindow.setPosition(pCenter(GAME_WIDTH) - (width + 50), pCenter(GAME_HEIGHT) - pCenter(height));
        lootWindow.padTop(-20);

        lootWindow.add(new Label(match.getI18n().getBundle().get("loot"), skin)).padTop(20).row();

        for (int i = 0; i < lootItems.size(); i++) {
            insertItemToWindow(lootWindow, i, false);
        }

        stage.addActor(lootWindow);
    }

    private void forgeUpperStage() {
        // forge equipment interface
    }

    private void insertItemToWindow(Window window, int index, boolean isInventoryItem) {
        EntityObject item = isInventoryItem ? inventoryItems.get(index) : lootItems.get(index);
        TextureRegion myTextureRegion = new TextureRegion(item.getIcon());
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton itemImage = new ImageButton(myTexRegionDrawable);

        itemImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isInventoryItem) {
                    if (!selectInventorySlot(index)) {
                        dropOnInventorySlot(index);
                    }
                } else {
                    if (!selectLootSlot(index)) {
                        dropOnLootSlot(index);
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        window.add(itemImage).padTop(10).row();
        window.add(new Label(item.getName(),
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD,
                        14, false), Color.WHITE))).row();
    }

    private boolean selectInventorySlot(int index) {
        if (inventoryItems.get(index).getClass() != Empty.class && selectedLootItem == null && selectedInventoryItem == null) {
            Texture icon = inventoryItems.get(index).getIcon();
            if (!icon.getTextureData().isPrepared()) {
                icon.getTextureData().prepare();
            }
            Pixmap cursor = icon.getTextureData().consumePixmap();
            match.setCursor(cursor, true);
            selectedInventoryItem = inventoryItems.get(index);
            inventoryItems.set(index, new Empty(match));
            updateWindows(false);
            return true;
        }
        return false;
    }

    private boolean selectLootSlot(int index) {
        if (lootItems.get(index).getClass() != Empty.class && selectedLootItem == null && selectedInventoryItem == null) {
            Texture icon = lootItems.get(index).getIcon();
            if (!icon.getTextureData().isPrepared()) {
                icon.getTextureData().prepare();
            }
            Pixmap cursor = icon.getTextureData().consumePixmap();
            match.setCursor(cursor, true);
            selectedLootItem = lootItems.get(index);
            lootItems.set(index, new Empty(match));
            updateWindows(true);
            return true;
        }
        return false;
    }

    private void dropOnInventorySlot(int index) {
        if (inventoryItems.get(index).getClass() == Empty.class) {
            if (selectedLootItem != null) {
                match.setCursor(match.getResources().getPixmap(ResourceHandler.PixmapPath.MENU_CURSOR), false);
                inventoryItems.set(index, selectedLootItem);
                selectedLootItem = null;
                updateWindows(true);
            } else if (selectedInventoryItem != null) {
                match.setCursor(match.getResources().getPixmap(ResourceHandler.PixmapPath.MENU_CURSOR), false);
                inventoryItems.set(index, selectedInventoryItem);
                selectedInventoryItem = null;
                updateWindows(true);
            }
        }
    }

    private void dropOnLootSlot(int index) {
        if (lootItems.get(index).getClass() == Empty.class) {
            if (selectedInventoryItem != null) {
                match.setCursor(match.getResources().getPixmap(ResourceHandler.PixmapPath.MENU_CURSOR), false);
                lootItems.set(index, selectedInventoryItem);
                selectedInventoryItem = null;
                updateWindows(false);
            } else if (selectedLootItem != null) {
                match.setCursor(match.getResources().getPixmap(ResourceHandler.PixmapPath.MENU_CURSOR), false);
                lootItems.set(index, selectedLootItem);
                selectedLootItem = null;
                updateWindows(false);
            }
        }
    }

    private void updateWindows(boolean inventoryIsFocused) {
        lootWindow.remove();
        inventoryWindow.remove();
        if (inventoryIsFocused) {
            forgeLootInterface();
            forgeInventoryInterface();
        } else {
            forgeInventoryInterface();
            forgeLootInterface();
        }
    }

    public void update(float delta) {
        handleInput(delta);
    }

    private void handleInput(float delta) {
        if ((Gdx.app.getInput().isKeyJustPressed(Input.Keys.E) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.A) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.S) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.W) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.D) ||
                Gdx.app.getInput().isKeyJustPressed(Input.Keys.ESCAPE)) &&
                selectedInventoryItem == null && selectedLootItem == null){
            match.delegateInputProcessor();
            match.setCursor(match.getResources().getPixmap(ResourceHandler.PixmapPath.MATCH_CURSOR), true);
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
