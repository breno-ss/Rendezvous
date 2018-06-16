package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bressio.rendezvous.entities.objects.Inventory;
import com.bressio.rendezvous.forge.BodyBuilder;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.graphics.Animator;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private final int speed;
    private final short categoryBits;
    private final short maskBits;
    private final Object userData;
    private final AnimationRegion animationRegion;

    private Animator animator;
    private int health = 100;
    private int armor = 10;
    private Inventory inventory;

    Soldier(Match match, Vector2 position, float radius, float linearDamping, int speed,
            AnimationRegion animationRegion, short categoryBits, short maskBits, Object userData) {
        super(match, position, animationRegion);

        this.radius = radius;
        this.linearDamping = linearDamping;
        this.speed = speed;
        this.animationRegion = animationRegion;
        this.categoryBits = categoryBits;
        this.userData = userData;
        this.maskBits = maskBits;
        init();
        buildBody();
    }

    private void init() {
        animator = new Animator(this, animationRegion);
        inventory = new Inventory(getMatch());
        setOrigin(pScaleCenter(animationRegion.getFrameSize()), pScaleCenter(animationRegion.getFrameSize()));
        setBounds(0, 0, pScale(animationRegion.getFrameSize()), pScale(animationRegion.getFrameSize()));
        setRegion(animator.getIdleTexture());
    }

    @Override
    protected void buildBody() {
        setBody(new BodyBuilder(getMatch().getWorld(), getPosition())
                .withBodyType(BodyDef.BodyType.DynamicBody)
                .withRadius(pScale(radius))
                .withLinearDamping(linearDamping)
                .withCategoryBits(categoryBits)
                .withMaskBits(maskBits)
                .withUserData(userData)
                .build());
    }

    public void update(float delta) {
        setPosition(
                getBody().getPosition().x - pCenter(getWidth()),
                getBody().getPosition().y - pCenter(getHeight()));
        setRegion(animator.getFrame(delta, 1));
    }

    void changeHealth(int difference) {
        health = health + difference < 0 ? 0 : health + difference > 100 ? 100 : health + difference;
    }

    public Inventory getInventory() {
        return inventory;
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
