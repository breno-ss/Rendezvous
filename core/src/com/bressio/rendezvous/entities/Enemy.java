package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.Vector2;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;
import static com.bressio.rendezvous.scheme.PhysicsAdapter.WATER_TAG;

public class Enemy extends Soldier{

    public Enemy(Match match, float radius, float linearDamping, int speed, Vector2 position) {
        super(match, position, radius, linearDamping, speed, AnimationRegion.SOLDIER, ENEMY_TAG,
                (short) (DEFAULT_TAG | BUILDING_TAG | LOOT_TAG | WATER_TAG | PLAYER_TAG | BULLET_TAG), null);
        setUserData();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        getInventory().update(delta);
    }

    private void setUserData() {
        getFixture().setUserData(this);
    }
}
