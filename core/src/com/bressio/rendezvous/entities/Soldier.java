package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bressio.rendezvous.entities.objects.Empty;
import com.bressio.rendezvous.entities.objects.Inventory;
import com.bressio.rendezvous.entities.objects.Medkit;
import com.bressio.rendezvous.forge.BodyBuilder;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.graphics.Animator;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private final int speed;
    private final short categoryBits;
    private final short maskBits;
    private final Object userData;
    private AnimationRegion animationRegion;
    private Object lastSelectedObjectClass;

    private Animator animator;
    private int health = 100;
    private int armor = 0;
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
        setOrigin(pScaleCenter(animationRegion.getFrameWidth()), pScaleCenter(animationRegion.getFrameHeight()));
        setBounds(0, 0, pScale(animationRegion.getFrameWidth()), pScale(animationRegion.getFrameHeight()));
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
        changeAnimation();
    }

    private void changeAnimation() {
        Object selectedObjectClass = inventory.getItem(getMatch().getHud().getSelectedSlot()).getClass();
        if (selectedObjectClass != lastSelectedObjectClass) {
            if (selectedObjectClass == Medkit.class) {
                animationRegion = AnimationRegion.SOLDIER_MEDKIT;
                setRegion(getMatch().getResources().getTextureAtlas(ResourceHandler.TextureAtlasPath.SOLDIER_MEDKIT_ATLAS).findRegion(animationRegion.getRegion()));
                animator = new Animator(this, AnimationRegion.SOLDIER_MEDKIT);
            } else if (selectedObjectClass == Empty.class) {
                animationRegion = AnimationRegion.SOLDIER;
                setRegion(getMatch().getResources().getTextureAtlas(ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS).findRegion(animationRegion.getRegion()));
                animator = new Animator(this, AnimationRegion.SOLDIER);
            }
            lastSelectedObjectClass = selectedObjectClass;
        }
    }

    public void changeHealth(int difference) {
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

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
