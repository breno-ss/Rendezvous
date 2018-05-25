package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScale;

public abstract class Entity extends Sprite {

    private World world;
    private Body body;
    private Vector2 position;

    Entity(World world, Match match, float x, float y, String animationRegion) {
        super(match.getResources().getAtlas().findRegion(animationRegion));
        this.world = world;
        position = pScale(x, y);
    }

    protected abstract void create();

    public Body getBody() {
        return body;
    }

    void setBody(Body body) {
        this.body = body;
    }

    World getWorld() {
        return world;
    }

    Vector2 getPosition() {
        return position;
    }
}
