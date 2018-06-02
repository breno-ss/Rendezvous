package com.bressio.rendezvous.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.forge.BodyBuilder;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class InteractiveObject {

    private World world;
    private TiledMap map;
    protected TiledMapTile tile;
    private Rectangle bounds;
    private Body body;
    private Fixture fixture;

    InteractiveObject(World world, TiledMap map, Rectangle bounds, boolean isSensor, short categoryBits) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        body = new BodyBuilder(this.world, pScale(bounds.getX() + pCenter(bounds.getWidth()), bounds.getY() + pCenter(bounds.getHeight())))
                .withBodyType(BodyDef.BodyType.StaticBody)
                .withWidth(pScaleCenter(bounds.getWidth()))
                .withHeight(pScaleCenter(bounds.getHeight()))
                .withSensor(isSensor)
                .withCategoryBits(categoryBits)
                .build();

        fixture = body.getFixtureList().first();
    }

    public abstract void onPlayerEnter();

    public abstract void onPlayerLeave();

    TiledMap getMap() {
        return map;
    }

    Body getBody() {
        return body;
    }

    Fixture getFixture() {
        return fixture;
    }
}
