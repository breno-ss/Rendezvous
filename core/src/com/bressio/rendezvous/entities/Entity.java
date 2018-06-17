package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScale;

public abstract class Entity extends Sprite {

    private Match match;
    private Body body;
    private Vector2 position;

    Entity(Match match, Vector2 position, AnimationRegion animationRegion) {
        super(match.getResources().getTextureAtlas(
                ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS
        ).findRegion(animationRegion.getRegion()));
        this.match = match;
        this.position = new Vector2(pScale(position.x), pScale(position.y));
    }

    protected abstract void buildBody();

    void setBody(Body body) {
        this.body = body;
    }

    public Match getMatch() {
        return match;
    }

    public Body getBody() {
        return body;
    }

    Vector2 getPosition() {
        return position;
    }
}
