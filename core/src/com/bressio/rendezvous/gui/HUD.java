package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.graphics.FontGenerator;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

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

    private Texture vignette;

    public HUD(Match match) {
        this.match = match;
        setupStage();
        forgeEventDisplay();
        forgeMinimap();
        forgeHealthDisplay();
        forgeInventory();
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
        inventoryBackground = new Image(match.getResources().getTexture(ResourceHandler.TexturePath.INVENTORY));
        inventoryBackground.setPosition(1000, 10);
        stage.addActor(inventoryBackground);
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
}
