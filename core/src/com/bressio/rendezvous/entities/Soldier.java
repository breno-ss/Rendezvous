package com.bressio.rendezvous.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.graphics.StateMachine;
import com.bressio.rendezvous.helpers.BodyBuilder;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.helpers.PhysicsManager.pScale;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private StateMachine animation;

    Soldier(World world, Match match, float x, float y, float radius, float linearDamping, ResourceHandler.AnimationRegion animationRegion) {
        super(world, match, x, y, animationRegion.getRegion());
        this.radius = radius;
        this.linearDamping = linearDamping;
        create();
        animation = new StateMachine(this, animationRegion);
        setOrigin(pScale(32), pScale(32));
        setBounds(0, 0, pScale(64), pScale(64));
        setRegion(animation.getIdleTexture());
    }

    @Override
    protected void create() {
        setBody(new BodyBuilder(getWorld(), getPosition())
                .withBodyType(BodyDef.BodyType.DynamicBody)
                .withRadius(pScale(radius))
                .withLinearDamping(linearDamping)
                .build());
    }

    public void update(float delta) {
        setPosition(getBody().getPosition().x - getWidth() / 2, getBody().getPosition().y - getHeight() / 2);
        setRegion(animation.getFrame(delta));
    }
}
