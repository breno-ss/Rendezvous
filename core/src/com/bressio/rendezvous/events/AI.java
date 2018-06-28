package com.bressio.rendezvous.events;

import com.badlogic.gdx.math.Vector2;
import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.tiles.Loot;
import com.bressio.rendezvous.forge.WorldBuilder;
import com.bressio.rendezvous.scheme.MathUtils;

import java.util.ArrayList;

public class AI {

    private WorldBuilder worldBuilder;
    private Soldier soldier;
    private Vector2 target;

    private enum State {
        IDLE, CALCULATING_ROUTE, SEEKING_LOOT
    }

    private State state;

    public AI(Soldier soldier) {
        state = State.IDLE;
        this.soldier = soldier;
    }

    public void update(float delta) {
        switch (state) {
            case CALCULATING_ROUTE:
                calculateRoute();
                break;
            case SEEKING_LOOT:
                seekLoot();
        }
    }

    public void wakeUp(WorldBuilder worldBuilder) {
        this.worldBuilder = worldBuilder;
        setState(State.CALCULATING_ROUTE);
    }

    private void calculateRoute() {
        ArrayList<Loot> loot = worldBuilder.getLoot();
        double nearestLootDistance = 0;
        Loot nearestLoot = null;

        for (int i = 0; i < loot.size(); i++) {
            if (i == 0 || MathUtils.distance(
                    soldier.getBody().getPosition(), loot.get(i).getBody().getPosition()
            ) < nearestLootDistance) {
                nearestLoot = loot.get(i);
                nearestLootDistance = MathUtils.distance(
                        soldier.getBody().getPosition(), loot.get(i).getBody().getPosition()
                );
            }
        }
        if (nearestLoot != null) {
            target = nearestLoot.getBody().getPosition();
        }
        setState(State.SEEKING_LOOT);
    }

    private void seekLoot() {
        ((SteeringBehavior)soldier).seek(target);
    }

    private void setState(State state) {
        this.state = state;
    }
}
