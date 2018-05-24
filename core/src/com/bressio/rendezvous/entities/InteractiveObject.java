package com.bressio.rendezvous.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.helpers.BodyBuilder;

import static com.bressio.rendezvous.helpers.PhysicsManager.pScale;

public class InteractiveObject {

    private World world;
    private TiledMap map;
    protected TiledMapTile tile;
    private Rectangle bounds;
    private Body body;

    InteractiveObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        body = new BodyBuilder(this.world, pScale(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2))
                .withWidth(pScale(bounds.getWidth() / 2))
                .withHeight(pScale(bounds.getHeight() / 2))
                .build();
    }
}
