package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class Loot extends InteractiveObject {

    private SpriteBatch batch;
    private Texture interactionButton;
    private boolean playerIsColliding;
    private Match match;

    private ArrayList<EntityObject> items;

    Loot(World world, TiledMap map, Rectangle bounds, SpriteBatch batch, ResourceHandler resources, Match match) {
        super(world, map, bounds, false, LOOT_TAG);
        this.batch = batch;
        this.match = match;
        items = new ArrayList<>();
        interactionButton = resources.getTexture(ResourceHandler.TexturePath.INTERACTION_BUTTON);
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
            match.handleLootInterface(delta, items);
        }
    }

    ArrayList<EntityObject> getItems() {
        return items;
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
