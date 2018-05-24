package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bressio.rendezvous.entities.Entity;

public class StateMachine {

    private enum State {
        IDLE, MOVING
    }

    private Entity entity;
    private float stateTimer;
    private State currentState;
    private State previousState;
    private TextureRegion idleTexture;
    private KeyFrameIndexer indexer;

    public StateMachine(Entity entity, ResourceHandler.AnimationRegion animationRegion) {
        this.entity = entity;

        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;

        indexer = new KeyFrameIndexer(this.entity.getTexture(), animationRegion);

        idleTexture = new TextureRegion(this.entity.getTexture(),
                animationRegion.getIdleTextureX(),
                animationRegion.getIdleTextureY(),
                animationRegion.getIdleTextureWidth(),
                animationRegion.getIdleTextureHeight());

        idleTexture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public TextureRegion getIdleTexture() {
        return idleTexture;
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case MOVING:
                region = indexer.getMovingAnimation().getKeyFrame(stateTimer, true);
                break;
            default:
                region = idleTexture;
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (entity.getBody().getLinearVelocity().x > 1 || entity.getBody().getLinearVelocity().x < -1
                || entity.getBody().getLinearVelocity().y > 1 || entity.getBody().getLinearVelocity().y < -1) {
            return State.MOVING;
        } else {
            return State.IDLE;
        }
    }
}
