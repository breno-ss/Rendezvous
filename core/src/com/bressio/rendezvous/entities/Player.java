package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bressio.rendezvous.helpers.PhysicsManager;
import com.bressio.rendezvous.scenes.Match;

// TODO extend Entity
public class Player extends Sprite {

    private World world;
    private Body body;
    private final float PPM;
    private Vector2 position;
    private TextureRegion idlePlayer;

    public Player(World world, Match match, float x, float y) {
        super(match.getAtlas().findRegion("player_animation"));
        PPM = PhysicsManager.PPM;
        this.world = world;
        position = new Vector2(x / PPM, y / PPM);
        start();
        idlePlayer = new TextureRegion(getTexture(), 0, 0, 64, 64);
        setBounds(0, 0, 64 / PPM, 64 / PPM);
        setRegion(idlePlayer);
    }

    private void start() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.linearDamping = 5;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(32 / PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }
}
