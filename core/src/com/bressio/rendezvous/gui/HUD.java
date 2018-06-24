package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.entities.objects.Empty;
import com.bressio.rendezvous.entities.objects.Inventory;
import com.bressio.rendezvous.graphics.FontGenerator;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class HUD implements Disposable {

    private Stage stage;
    private Viewport viewport;

    private Label event;
    private Label timeToNextEvent;

    private Texture minimap;
    private Texture minimapFrame;
    private Texture minimapPlayerMark;
    private Rectangle minimapMask;
    private int minimapOffset = 10;

    private Image eventBackground;

    private Texture emptyBar;
    private Texture healthbar;
    private Texture armorBar;
    private Texture healthIcon;
    private Texture armorIcon;
    private Label healthPoints;
    private Label armorPoints;
    private Match match;

    private Image inventoryBackground;
    private Image selectionMarker;
    private ArrayList<Image> items;
    private int selectedSlot;

    private Texture vignette;

    private Label ammoIndicator;

    public HUD(Match match) {
        this.match = match;
        setupStage();
        forgeEventDisplay();
        forgeMinimap();
        forgeHealthDisplay();
        forgeInventory();
        forgeAmmoIndicator();
    }

    private void setupStage() {
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, match.getBatch());
        vignette = match.getResources().getTexture(ResourceHandler.TexturePath.VIGNETTE);
    }

    private void forgeEventDisplay() {
        eventBackground = new Image(match.getResources().getTexture(ResourceHandler.TexturePath.EVENT_BACKGROUND));
        eventBackground.setPosition(pCenter(GAME_WIDTH) - pCenter( eventBackground.getWidth()),
                GAME_HEIGHT - eventBackground.getHeight());
        stage.addActor(eventBackground);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        event = new Label("",
                new Label.LabelStyle(
                        FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 12, false),
                        Color.WHITE
                )
        );
        table.add(event).fillX().padTop(3).row();

        timeToNextEvent = new Label("",
                new Label.LabelStyle(
                        FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 22, false),
                        Color.WHITE
                )
        );
        table.add(timeToNextEvent).padTop(1).row();

        stage.addActor(table);
    }

    private void forgeMinimap() {
        minimap = match.getResources().getTexture(ResourceHandler.TexturePath.MATCH_MINIMAP);
        minimapFrame = match.getResources().getTexture(ResourceHandler.TexturePath.MATCH_MINIMAP_FRAME);
        minimapPlayerMark = match.getResources().getTexture(ResourceHandler.TexturePath.PLAYER_MARK);
        minimapMask = new Rectangle( minimapOffset, minimapOffset, 200, 200);
    }

    private void forgeHealthDisplay() {
        emptyBar = match.getResources().getTexture(ResourceHandler.TexturePath.EMPTY_BAR);
        healthbar = match.getResources().getTexture(ResourceHandler.TexturePath.HEALTH_BAR);
        armorBar = match.getResources().getTexture(ResourceHandler.TexturePath.ARMOR_BAR);
        healthIcon = match.getResources().getTexture(ResourceHandler.TexturePath.HEALTH_ICON);
        armorIcon = match.getResources().getTexture(ResourceHandler.TexturePath.ARMOR_ICON);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        armorPoints = new Label("",
                new Label.LabelStyle(
                        FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 14, false), Color.WHITE
                )
        );
        table.add(armorPoints).padBottom(3).padLeft(-350).row();
        healthPoints = new Label("",
                new Label.LabelStyle(
                        FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 14, false), Color.WHITE
                )
        );
        table.add(healthPoints).padBottom(15).padLeft(-350);

        stage.addActor(table);
    }

    private void forgeInventory() {
        items = new ArrayList<>();
        inventoryBackground = new Image(match.getResources().getTexture(ResourceHandler.TexturePath.INVENTORY));
        inventoryBackground.setPosition(1000, 10);
        stage.addActor(inventoryBackground);

        selectedSlot = 0;

        selectionMarker = new Image(match.getResources().getTexture(ResourceHandler.TexturePath.SELECTED_SLOT));
        Vector2 markerPos = getSelectionMarkerPosition(selectedSlot);
        selectionMarker.setPosition(markerPos.x, markerPos.y);
        stage.addActor(selectionMarker);

        float itemPos = 996;
        for (int i = 0; i < 6; i++) {
            items.add(new Image(match.getResources().getTexture(ResourceHandler.TexturePath.INVISIBLE_SLOT)));
            items.get(i).setPosition(itemPos, 15);
            itemPos += 55.5f;
            stage.addActor(items.get(i));
        }
    }

    private void forgeAmmoIndicator() {
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        ammoIndicator = new Label("",
                new Label.LabelStyle(
                        FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 22, false), Color.WHITE
                )
        );
        table.add(ammoIndicator).padBottom(60).row();

        stage.addActor(table);
    }

    private Vector2 getSelectionMarkerPosition(int index) {
        switch (index) {
            case 0: return new Vector2(996, 6);
            case 1: return new Vector2(1051.5f, 6);
            case 2: return new Vector2(1107, 6);
            case 3: return new Vector2(1162.5f, 6);
            case 4: return new Vector2(1218, 6);
            case 5: return new Vector2(1273.5f, 6);
            default: return new Vector2(1329, 6);
        }
    }

    public void drawVignette(float delta) {
        match.getBatch().begin();
        match.getBatch().draw(vignette, 0, 0);
        match.getBatch().end();
    }

    public void drawMinimap(float delta, Vector2 playerPosition) {
        match.getBatch().begin();
        match.getBatch().flush();
        ScissorStack.pushScissors(minimapMask);
        match.getBatch().draw(minimap, - playerPosition.x * 7.2f - 77 + minimapOffset,
                - playerPosition.y * 7.2f - 77 + minimapOffset);
        match.getBatch().draw(minimapFrame, minimapOffset, minimapOffset);
        match.getBatch().draw(minimapPlayerMark,
                pCenter(minimapMask.width) - pCenter(minimapPlayerMark.getWidth()) + 5 + minimapOffset,
                pCenter(minimapMask.height) - pCenter(minimapPlayerMark.getHeight()) + 5 + minimapOffset);
        match.getBatch().flush();
        ScissorStack.popScissors();
        match.getBatch().end();
    }

    public void drawHealthBars(float delta, int health, int armor) {
        match.getBatch().begin();
        match.getBatch().draw(emptyBar, pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()), 33);
        match.getBatch().draw(emptyBar, pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()), 15);

        match.getBatch().draw(armorBar,
                pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()),
                33,
                armor * 4,
                15);
        match.getBatch().draw(healthbar,
                pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()),
                15,
                health * 4,
                15);

        match.getBatch().draw(armorIcon,
                pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()) + 2, 35);
        match.getBatch().draw(healthIcon,
                pCenter(GAME_WIDTH) - pCenter(emptyBar.getWidth()) + 2, 17);

        match.getBatch().end();
    }

    public void updateHealthBars(float delta, int health, int armor) {
        armorPoints.setText(String.valueOf(armor));
        healthPoints.setText(String.valueOf(health));
    }

    public void updateAmmoIndicator(float delta, Inventory inventory) {
        String bulletsInMagazine = inventory.getBulletsInMagazine();
        String bulletsInAmmoBoxes = inventory.getBulletsInAmmoBoxes();
        if (bulletsInMagazine != null && bulletsInAmmoBoxes != null) {
            ammoIndicator.setText(bulletsInMagazine + " | " + bulletsInAmmoBoxes);
        } else {
            ammoIndicator.setText("");
        }
    }

    public void updateInventory(float delta) {
        if (match.getPlayer() != null) {
            for (int i = 0; i < 6; i++) {
                items.get(i).setDrawable(
                        new SpriteDrawable(new Sprite(
                                match.getPlayer().getInventory().getItems().get(i).getClass() == Empty.class ?
                                        match.getResources().getTexture(ResourceHandler.TexturePath.INVISIBLE_SLOT) :
                                        match.getPlayer().getInventory().getItems().get(i).getIcon()
                        ))
                );
            }
        }
    }

    public void switchSelectedSlot(int slotIndex) {
        Vector2 newPos = getSelectionMarkerPosition(slotIndex);
        selectedSlot = slotIndex;
        selectionMarker.setPosition(newPos.x, newPos.y);
    }

    public void switchSelectedSlot(boolean isGoingForward) {
        if (!isGoingForward) {
            Vector2 newPos = getSelectionMarkerPosition(selectedSlot - 1 < 0 ? 5 : selectedSlot - 1);
            selectedSlot = selectedSlot - 1 < 0 ? 5 : selectedSlot - 1;
            selectionMarker.setPosition(newPos.x, newPos.y);
        } else {
            Vector2 newPos = getSelectionMarkerPosition(selectedSlot + 1 > 5 ? 0 : selectedSlot + 1);
            selectedSlot = selectedSlot + 1 > 5 ? 0 : selectedSlot + 1;
            selectionMarker.setPosition(newPos.x, newPos.y);
        }
    }

    public void updateTimeLabel(String time) {
        timeToNextEvent.setText(time);
    }

    public void updateEventLabel(String eventLabel, boolean isInRendezvous) {
        event.setText(eventLabel);
        if (isInRendezvous) {
            timeToNextEvent.setVisible(false);
            event.setStyle(new Label.LabelStyle(
                    FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 26, false), Color.WHITE
            ));
        } else {
            timeToNextEvent.setVisible(true);
            event.setStyle(new Label.LabelStyle(
                    FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 12, false), Color.WHITE
            ));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }
}
