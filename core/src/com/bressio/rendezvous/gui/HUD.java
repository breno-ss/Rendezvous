package com.bressio.rendezvous.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class HUD implements Disposable {

    private Stage stage;
    private Viewport viewport;

    private Label event;
    private Label timeToNextEvent;

    private SpriteBatch batch;

    private Texture minimap;
    private Texture minimapFrame;
    private Texture minimapPlayerMark;
    private Rectangle minimapMask;
    private int minimapOffset = 10;

    private Image eventBackground;

    public HUD(SpriteBatch batch, ResourceHandler resources) {
        this.batch = batch;
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        minimap = resources.getTexture(ResourceHandler.TexturePath.MATCH_MINIMAP);

        eventBackground = new Image(resources.getTexture(ResourceHandler.TexturePath.EVENT_BACKGROUND));
        eventBackground.setPosition(pCenter(GAME_WIDTH) - pCenter( eventBackground.getWidth()),
                GAME_HEIGHT - eventBackground.getHeight());

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        event = new Label("",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 12, false), Color.WHITE));
        table.add(event).fillX().padTop(3).row();

        timeToNextEvent = new Label("",
                new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 22, false), Color.WHITE));
        table.add(timeToNextEvent).padTop(1).row();

        stage.addActor(eventBackground);
        stage.addActor(table);

        minimap = resources.getTexture(ResourceHandler.TexturePath.MATCH_MINIMAP);
        minimapFrame = resources.getTexture(ResourceHandler.TexturePath.MATCH_MINIMAP_FRAME);
        minimapPlayerMark = resources.getTexture(ResourceHandler.TexturePath.PLAYER_MARK);

        minimapMask = new Rectangle( minimapOffset, minimapOffset, 200, 200);
    }

    public void drawMinimap(float delta, Vector2 playerPosition) {
        batch.begin();
        batch.flush();
        ScissorStack.pushScissors(minimapMask);
        batch.draw(minimap, - playerPosition.x * 7.2f - 77 + minimapOffset,
                - playerPosition.y * 7.2f - 77 + minimapOffset);
        batch.draw(minimapFrame, minimapOffset, minimapOffset);
        batch.draw(minimapPlayerMark,
                pCenter(minimapMask.width) - pCenter(minimapPlayerMark.getWidth()) + 5 + minimapOffset,
                pCenter(minimapMask.height) - pCenter(minimapPlayerMark.getHeight()) + 5 + minimapOffset);
        batch.flush();
        ScissorStack.popScissors();
        batch.end();
    }

    public void updateTimeLabel(String time) {
        timeToNextEvent.setText(time);
    }

    public void updateEventLabel(String eventLabel, boolean isInRendezvous) {
        event.setText(eventLabel);
        if (isInRendezvous) {
            timeToNextEvent.setVisible(false);
            event.setStyle(new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 26, false), Color.WHITE));
        } else {
            timeToNextEvent.setVisible(true);
            event.setStyle(new Label.LabelStyle(FontGenerator.generate(ResourceHandler.FontPath.BOMBARD, 12, false), Color.WHITE));
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
