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
    private final short categoryBits;
    private final short maskBits;
    private final Object userData;

    private int health = 100;
    private int armor = 10;

    Soldier(World world, Match match, float x, float y, float radius, float linearDamping, int speed,
            AnimationRegion animationRegion, short categoryBits, short maskBits, Object userData) {
        super(world, match, x, y, animationRegion.getRegion());

        this.radius = radius;
        this.linearDamping = linearDamping;
        this.speed = speed;
        animator = new Animator(this, animationRegion);
        this.categoryBits = categoryBits;
        this.userData = userData;
        this.maskBits = maskBits;

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
                .withCategoryBits(categoryBits)
                .withMaskBits(maskBits)
                .withUserData(userData)
                .build());
    }

    public void update(float delta) {
        setPosition(getBody().getPosition().x - pCenter(getWidth()),getBody().getPosition().y - pCenter(getHeight()));
        setRegion(animator.getFrame(delta));
    }

    int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }
}
