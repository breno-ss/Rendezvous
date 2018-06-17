package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bressio.rendezvous.entities.Entity;

public class Animator {

    private enum State {
        IDLE, MOVING
    }

    private Entity entity;
    private float stateTimer;
    private State currentState;
    private State previousState;
    private TextureRegion idleTexture;
    private KeyFrameIndexer indexer;
    private AnimationRegion animationRegion;

    public Animator(Entity entity, AnimationRegion animationRegion) {
        this.entity = entity;
        this.animationRegion = animationRegion;
        init();
        setupIdleTexture();
    }

    private void init() {
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        indexer = new KeyFrameIndexer(entity.getTexture(), animationRegion);
    }

    private void setupIdleTexture() {
        idleTexture = new TextureRegion(entity.getTexture(),
                animationRegion.getIdleTextureX(),
                animationRegion.getIdleTextureY(),
                animationRegion.getIdleTextureWidth(),
                animationRegion.getIdleTextureHeight());
        idleTexture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public TextureRegion getIdleTexture() {
        return idleTexture;
    }

    public TextureRegion getFrame(float delta, float velocityThreshold) {
        currentState = getState(velocityThreshold);
        TextureRegion region;
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        switch (currentState) {
            case MOVING:
                region = indexer.getMovingAnimation().getKeyFrame(stateTimer, true);
                break;
            default:
                region = idleTexture;
                break;
        }
        previousState = currentState;
        return region;
    }

    private State getState(float velocityThreshold) {
        if (entity.getBody().getLinearVelocity().x > velocityThreshold ||
                entity.getBody().getLinearVelocity().x < -velocityThreshold ||
                entity.getBody().getLinearVelocity().y > velocityThreshold ||
                entity.getBody().getLinearVelocity().y < -velocityThreshold) {
            return State.MOVING;
        } else {
            return State.IDLE;
        }
    }
}
