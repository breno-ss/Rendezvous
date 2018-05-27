package com.bressio.rendezvous.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.forge.BodyBuilder;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.graphics.Animator;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private final int speed;
    private final Animator animator;

    Soldier(World world, Match match, float x, float y, float radius, float linearDamping, int speed,
            AnimationRegion animationRegion) {
        super(world, match, x, y, animationRegion.getRegion());

        this.radius = radius;
        this.linearDamping = linearDamping;
        this.speed = speed;
        animator = new Animator(this, animationRegion);

        setOrigin(pScaleCenter(animationRegion.getFrameSize()), pScaleCenter(animationRegion.getFrameSize()));
        setBounds(0, 0, pScale(animationRegion.getFrameSize()), pScale(animationRegion.getFrameSize()));
        setRegion(animator.getIdleTexture());

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
        setRegion(animator.getFrame(delta));
    }

    int getSpeed() {
        return speed;
    }
}
