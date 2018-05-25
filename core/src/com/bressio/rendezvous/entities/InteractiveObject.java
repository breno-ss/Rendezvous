package com.bressio.rendezvous.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.forge.BodyBuilder;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScaleCenter;

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

        body = new BodyBuilder(this.world, pScaleCenter(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight()))
                .withWidth(pScaleCenter(bounds.getWidth()))
                .withHeight(pScaleCenter(bounds.getHeight()))
                .build();
    }
}
