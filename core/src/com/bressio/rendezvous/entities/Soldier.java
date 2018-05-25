package com.bressio.rendezvous.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.graphics.StateMachine;
import com.bressio.rendezvous.helpers.BodyBuilder;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.helpers.PhysicalConstants.pCenter;
import static com.bressio.rendezvous.helpers.PhysicalConstants.pScale;
import static com.bressio.rendezvous.helpers.PhysicalConstants.pScaleCenter;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private final int speed;
    private final StateMachine animation;

    Soldier(World world, Match match, float x, float y, float radius, float linearDamping, int speed,
            ResourceHandler.AnimationRegion animationRegion) {
        super(world, match, x, y, animationRegion.getRegion());

        this.radius = radius;
        this.linearDamping = linearDamping;
        this.speed = speed;
        animation = new StateMachine(this, animationRegion);

        setOrigin(pScaleCenter(animationRegion.getFrameSize()), pScaleCenter(animationRegion.getFrameSize()));
        setBounds(0, 0, pScale(animationRegion.getFrameSize()), pScale(animationRegion.getFrameSize()));
        setRegion(animation.getIdleTexture());

        create();
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
        setPosition(getBody().getPosition().x - pCenter(getWidth()),getBody().getPosition().y - pCenter(getHeight()));
        setRegion(animation.getFrame(delta));
    }

    int getSpeed() {
        return speed;
    }
}
