package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bressio.rendezvous.entities.objects.Empty;
import com.bressio.rendezvous.entities.objects.Inventory;
import com.bressio.rendezvous.entities.objects.Medkit;
import com.bressio.rendezvous.entities.objects.equipment.armor.MilitaryVest;
import com.bressio.rendezvous.entities.objects.equipment.armor.SoftVest;
import com.bressio.rendezvous.entities.objects.equipment.helmets.CombatHelmet;
import com.bressio.rendezvous.entities.objects.equipment.helmets.HalfHelmet;
import com.bressio.rendezvous.entities.objects.weapons.ars.STAR;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
import com.bressio.rendezvous.forge.BodyBuilder;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.graphics.Animator;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public abstract class Soldier extends Entity {

    private final float radius;
    private final float linearDamping;
    private int speed;
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

    private Texture pointlight;

    private boolean isFiring;

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
        pointlight = getMatch().getResources().getTexture(ResourceHandler.TexturePath.POINTLIGHT);
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
        verifyItems();
    }

    private void verifyItems() {
        Object selectedObjectClass = inventory.getItem(getMatch().getHud().getSelectedSlot()).getClass();
        Object selectedAmorClass = inventory.getEquipmentItems().get(1).getClass();
        Object selectedHelmetClass = inventory.getEquipmentItems().get(0).getClass();

        if (selectedObjectClass != lastSelectedObjectClass || selectedAmorClass != lastEquippedArmorClass ||
                selectedHelmetClass != lastEquippedHelmetClass) {

            if (selectedAmorClass == Empty.class && selectedHelmetClass == Empty.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER,
                        ResourceHandler.TextureAtlasPath.SOLDIER_ATLAS,
                        AnimationRegion.SOLDIER_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_STAR_ATLAS,
                        AnimationRegion.SOLDIER_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_W16A_ATLAS);
            } else if (selectedHelmetClass == Empty.class && selectedAmorClass == MilitaryVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_MILITARY_VEST,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MILITARY_VEST_ATLAS,
                        AnimationRegion.SOLDIER_MV_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_MV_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_STAR_ATLAS,
                        AnimationRegion.SOLDIER_MV_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_W16A_ATLAS);
            } else if (selectedHelmetClass == Empty.class && selectedAmorClass == SoftVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_SOFT_VEST,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SOFT_VEST_ATLAS,
                        AnimationRegion.SOLDIER_SV_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_SV_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_STAR_ATLAS,
                        AnimationRegion.SOLDIER_SV_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_W16A_ATLAS);
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == Empty.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_COMBAT_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_COMBAT_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_CT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_CT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_CT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_CT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_CT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_CT_W16A_ATLAS);
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == Empty.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_HALF_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_HALF_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_HT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_HT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_HT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_HT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_HT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_HT_W16A_ATLAS);
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == MilitaryVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_MV_COMBAT_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_COMBAT_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_MV_CT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_CT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_MV_CT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_CT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_MV_CT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_CT_W16A_ATLAS);
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == MilitaryVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_MV_HALF_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_HALF_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_MV_HT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_HT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_MV_HT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_HT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_MV_HT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_MV_HT_W16A_ATLAS);
            } else if (selectedHelmetClass == CombatHelmet.class && selectedAmorClass == SoftVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_SV_COMBAT_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_COMBAT_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_SV_CT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_CT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_SV_CT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_CT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_SV_CT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_CT_W16A_ATLAS);
            } else if (selectedHelmetClass == HalfHelmet.class && selectedAmorClass == SoftVest.class) {
                defineNewAnimation(selectedObjectClass,
                        AnimationRegion.SOLDIER_SV_HALF_HELMET,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_HALF_HELMET_ATLAS,
                        AnimationRegion.SOLDIER_SV_HT_MEDKIT,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_HT_MEDKIT_ATLAS,
                        AnimationRegion.SOLDIER_SV_HT_STAR,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_HT_STAR_ATLAS,
                        AnimationRegion.SOLDIER_SV_HT_W16A,
                        ResourceHandler.TextureAtlasPath.SOLDIER_SV_HT_W16A_ATLAS);
            }
        }
        lastSelectedObjectClass = selectedObjectClass;
        lastEquippedArmorClass = selectedAmorClass;
        lastEquippedHelmetClass = selectedHelmetClass;
    }

    private void defineNewAnimation(Object selectedObjectClass,
                                    AnimationRegion baseAnim, ResourceHandler.TextureAtlasPath base,
                                    AnimationRegion medkitAnim, ResourceHandler.TextureAtlasPath medkit,
                                    AnimationRegion starAnim, ResourceHandler.TextureAtlasPath star,
                                    AnimationRegion w16aAnim, ResourceHandler.TextureAtlasPath w16a) {
        if (selectedObjectClass == Empty.class) {
            switchAnimation(baseAnim, base);
        } else if (selectedObjectClass == Medkit.class) {
            switchAnimation(medkitAnim, medkit);
        } else if (selectedObjectClass == STAR.class) {
            switchAnimation(starAnim, star);
        } else if (selectedObjectClass == W16A.class) {
            switchAnimation(w16aAnim, w16a);
        } else {
            switchAnimation(baseAnim, base);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (isFiring) {
            batch.draw(pointlight,
                    getBody().getPosition().x - pScaleCenter(pointlight.getWidth()),
                    getBody().getPosition().y - pScaleCenter(pointlight.getHeight()),
                    pScale(pointlight.getWidth()),
                    pScale(pointlight.getHeight()));
        }
        super.draw(batch);
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
    }

    private void switchAnimation(AnimationRegion animationRegion, ResourceHandler.TextureAtlasPath textureAtlasPath) {
        this.animationRegion = animationRegion;
        setRegion(getMatch().getResources().getTextureAtlas(textureAtlasPath).findRegion(animationRegion.getRegion()));
        animator = new Animator(this, animationRegion);
        setBounds(0, 0, pScale(animationRegion.getFrameWidth()), pScale(animationRegion.getFrameHeight()));
        setOrigin(pScaleCenter(animationRegion.getFrameWidth()), pScaleCenter(animationRegion.getFrameHeight()));
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
