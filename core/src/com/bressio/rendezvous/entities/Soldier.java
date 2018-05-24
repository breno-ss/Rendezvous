package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.helpers.PhysicsManager.pScale;

public abstract class Soldier extends Entity {

    private enum State {
        IDLE, WALKING
    }

    private State currentState;
    private State previousState;
    private TextureRegion idlePlayer;
    private Animation<TextureRegion> walk;
    private float stateTimer;

    Soldier(World world, Match match, float x, float y, String animationRegion) {
        super(world, match, x, y, animationRegion);
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 64, 0, 64, 64));
        }
        walk = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        idlePlayer = new TextureRegion(getTexture(), 0, 0, 64, 64);
        idlePlayer.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setOrigin(pScale(32), pScale(32));
        setBounds(0, 0, pScale(64), pScale(64));
        setRegion(idlePlayer);
    }

    public void update(float delta) {
        setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    private TextureRegion getFrame(float delta) {
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

    private State getState() {
        if (getBody().getLinearVelocity().x > 1 || getBody().getLinearVelocity().x < -1
                || getBody().getLinearVelocity().y > 1 || getBody().getLinearVelocity().y < -1) {
            return State.WALKING;
        } else {
            return State.IDLE;
        }
    }
}
