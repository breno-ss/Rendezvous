package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bressio.rendezvous.entities.objects.NPCInventory;
import com.bressio.rendezvous.events.AI;
import com.bressio.rendezvous.events.SteeringBehavior;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class Enemy extends Soldier implements SteeringBehavior {

    private AI ai;
    private Vector2 target;

    public Enemy(Match match, float radius, float linearDamping, int speed, Vector2 position) {
        super(match, position, radius, linearDamping, speed, AnimationRegion.SOLDIER, ENEMY_TAG,
                (short) (DEFAULT_TAG | BUILDING_TAG | LOOT_TAG | WATER_TAG | PLAYER_TAG | BULLET_TAG), null);
        setUserData();
    }

    private void updateRotation(float delta) {
        if (target != null) {
            float angle = MathUtils.radiansToDegrees * MathUtils.atan2(
                    target.y - (getY() + pCenter(getHeight())),
                    target.x - (getX() + pCenter(getWidth())));

            if(angle < 0){
                angle += 360;
            }
            setRotation(angle - 90);
        }
    }

    @Override
    protected void init() {
        super.init();
        setInventory(new NPCInventory(getMatch()));
        ai = new AI(this);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        getInventory().update(delta);
        ai.update(delta);
        updateRotation(delta);
    }

    @Override
    protected void verifyItems() {

    }

    @Override
    public void seek(Vector2 target) {
        this.target = target;
        Vector2 direction = new Vector2();
        direction.x = target.x - getBody().getPosition().x;
        direction.y = target.y - getBody().getPosition().y;
        direction = direction.nor();
        getBody().applyForce(new Vector2(direction.x * getSpeed(), direction.y * getSpeed()), getBody().getWorldCenter(), true);
    }

    private void setUserData() {
        getFixture().setUserData(this);
    }

    public AI getAi() {
        return ai;
    }
}
