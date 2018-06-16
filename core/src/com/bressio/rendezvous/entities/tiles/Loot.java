package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.entities.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class Loot extends InteractiveTile {

    private SpriteBatch batch;
    private Texture interactionButton;
    private boolean playerIsColliding;

    private ArrayList<EntityObject> items;

    Loot(Rectangle bounds, Match match) {
        super(bounds, match, false, LOOT_TAG);
        this.batch = match.getBatch();
        init();
    }

    private void init() {
        items = new ArrayList<>();
        interactionButton = getMatch().getResources().getTexture(ResourceHandler.TexturePath.INTERACTION_BUTTON);
    }

    public void drawInteractionButton() {
        if (playerIsColliding) {
            batch.begin();
            batch.draw(interactionButton,
                    getBody().getPosition().x - pScaleCenter(interactionButton.getWidth()),
                    getBody().getPosition().y - pScaleCenter(interactionButton.getHeight()),
                    pScale(interactionButton.getWidth()),
                    pScale(interactionButton.getHeight()));
            batch.end();
        }
    }

    public void update(float delta) {
        if (playerIsColliding) {
            getMatch().handleLootInterface(delta, items);
        }
    }

    ArrayList<EntityObject> getItems() {
        return items;
    }

    public Match getMatch() {
        return super.getMatch();
    }

    @Override
    public void onPlayerEnter() {
        playerIsColliding = true;
    }

    @Override
    public void onPlayerLeave() {
        playerIsColliding = false;
    }
}
