package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.bressio.rendezvous.helpers.PhysicsManager;
import com.bressio.rendezvous.scenes.Match;

// TODO extend Entity
public class Player extends Sprite {

    private enum State {
        IDLE, WALKING
    }
    private State currentState;
    private State previousState;
    private TextureRegion idlePlayer;
    private Animation<TextureRegion> walk;
    private float stateTimer;

    private World world;
    private Body body;
    private final float PPM;
    private Vector2 position;

    public Player(World world, Match match, float x, float y) {
        super(match.getAtlas().findRegion("player_animation"));
        PPM = PhysicsManager.PPM;
        this.world = world;

        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        }
        walk = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        position = new Vector2(x / PPM, y / PPM);
        start();
        idlePlayer = new TextureRegion(getTexture(), 0, 0, 64, 64);
        idlePlayer.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setOrigin(32 / PPM, 32 / PPM);
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
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case WALKING:
                region = walk.getKeyFrame(stateTimer, true);
                break;
            default:
                region = idlePlayer;
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (body.getLinearVelocity().x > 1 || body.getLinearVelocity().x < -1
                || body.getLinearVelocity().y > 1 || body.getLinearVelocity().y < -1) {
            return State.WALKING;
        } else {
            return State.IDLE;
        }
    }

    public Body getBody() {
        return body;
    }
}
