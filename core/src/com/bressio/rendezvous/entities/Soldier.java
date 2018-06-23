package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bressio.rendezvous.entities.objects.Empty;
import com.bressio.rendezvous.entities.objects.Inventory;
import com.bressio.rendezvous.entities.objects.Medkit;
import com.bressio.rendezvous.entities.objects.equipment.armor.MilitaryVest;
import com.bressio.rendezvous.entities.objects.equipment.armor.SoftVest;
import com.bressio.rendezvous.entities.objects.equipment.helmets.CombatHelmet;
import com.bressio.rendezvous.entities.objects.equipment.helmets.HalfHelmet;
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
    private Object lastEquippedArmorClass;
    private Object lastEquippedHelmetClass;

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
        listenObjectChanges();
    }

    private void listenObjectChanges() {
        Object selectedObjectClass = inventory.getItem(getMatch().getHud().getSelectedSlot()).getClass();
        Object selectedAmorClass = inventory.getEquipmentItems().get(1).getClass();
        Object selectedHelmetClass = inventory.getEquipmentItems().get(0).getClass();

        if (selectedObjectClass != lastSelectedObjectClass || selectedAmorClass != lastEquippedArmorClass ||
                selectedHelmetClass != lastEquippedHelmetClass) {

            if (selectedAmorClass == Empty.class && selectedHelmetClass == Empty.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                } else if (selectedObjectClass == Medkit.class) {
                    changeAnimation(AnimationRegion.SOLDIER_MEDKIT, ResourceHandler.TextureAtlasPath.SOLDIER_MEDKIT_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == Empty.class && selectedAmorClass == MilitaryVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_MILITARY_VEST, ResourceHandler.TextureAtlasPath.SOLDIER_MILITARY_VEST_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == Empty.class && selectedAmorClass == SoftVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_SOFT_VEST, ResourceHandler.TextureAtlasPath.SOLDIER_SOFT_VEST_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == Empty.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_COMBAT_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_COMBAT_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == Empty.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_HALF_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_HALF_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == MilitaryVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_MV_COMBAT_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_MV_COMBAT_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == MilitaryVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_MV_HALF_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_MV_HALF_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == SoftVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_SV_COMBAT_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_SV_COMBAT_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == SoftVest.class) {
                if (selectedObjectClass == Empty.class) {
                    changeAnimation(AnimationRegion.SOLDIER_SV_HALF_HELMET, ResourceHandler.TextureAtlasPath.SOLDIER_SV_HALF_HELMET_ATLAS);
                } else {
                    changeAnimation(AnimationRegion.SOLDIER, ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS);
                }
            }
        }
        lastSelectedObjectClass = selectedObjectClass;
        lastEquippedArmorClass = selectedAmorClass;
        lastEquippedHelmetClass = selectedHelmetClass;
    }

    private void changeAnimation(AnimationRegion animationRegion, ResourceHandler.TextureAtlasPath textureAtlasPath) {
        this.animationRegion = animationRegion;
        setRegion(getMatch().getResources().getTextureAtlas(textureAtlasPath).findRegion(animationRegion.getRegion()));
        animator = new Animator(this, animationRegion);
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
