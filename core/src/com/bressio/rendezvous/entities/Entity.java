package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.helpers.BodyBuilder;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.helpers.PhysicsManager.pScale;

public abstract class Entity extends Sprite {

    private World world;
    private Body body;
    private Vector2 position;

    Entity(World world, Match match, float x, float y, String animationRegion) {
        super(match.getAtlas().findRegion(animationRegion));
        this.world = world;
        position = pScale(x, y);
        start();
    }

    private void start() {
        body = new BodyBuilder(world, position)
                .withBodyType(BodyDef.BodyType.DynamicBody)
                .withRadius(pScale(32))
                .withLinearDamping(5)
                .build();
    }

    public Body getBody() {
        return body;
    }
}
