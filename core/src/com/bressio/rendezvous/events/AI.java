package com.bressio.rendezvous.events;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.bressio.rendezvous.entities.Enemy;
import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.tiles.Loot;
import com.bressio.rendezvous.forge.WorldBuilder;
import com.bressio.rendezvous.scenes.Match;
import com.bressio.rendezvous.scheme.MathUtils;

import java.util.ArrayList;

public class AI {

    private Match match;
    private WorldBuilder worldBuilder;
    private Soldier soldier;
    private Vector2 target;
    private Vector2 altTarget;
    private ArrayList<Vector2> spatialMemory;

    private boolean isLooting;
    private boolean isGoingByAltPath;
    private boolean isAltPathTimedOut;

    private enum State {
        IDLE, CALCULATING_ROUTE, SEEKING_LOOT, LOOTING, FINDING_PATH
    }

    private State state;
    private State previousState;

    public AI(Match match, Soldier soldier) {
        state = State.IDLE;
        this.match = match;
        this.soldier = soldier;
        init();
    }

    private void init() {
        spatialMemory = new ArrayList<>();
    }

    public void update(float delta) {
        switch (state) {
            case CALCULATING_ROUTE:
                calculateRoute();
                break;
            case SEEKING_LOOT:
                seekLoot();
                break;
            case LOOTING:
                loot();
                break;
            case FINDING_PATH:
                findPath();
                break;
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
            ) < nearestLootDistance && !spatialMemory.contains(loot.get(i).getBody().getPosition())) {
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
        if (MathUtils.distance(soldier.getBody().getPosition(), target) < 1) {
            spatialMemory.add(target);
            soldier.getBody().getFixtureList().first().setSensor(false);
            setState(State.LOOTING);
        } else {
            if (((SteeringBehavior)soldier).seek(target)) {
                if (soldier.getBody().getLinearVelocity().x == 0 && soldier.getBody().getLinearVelocity().y == 0) {
                    if (!match.getCamera().frustum.pointInFrustum(
                            soldier.getBody().getPosition().x, soldier.getBody().getPosition().y, 0
                    )) {
                        soldier.getBody().getFixtureList().first().setSensor(true);
                        ((Enemy)soldier).setVisible(false);
                        Timer.schedule(new Timer.Task(){
                            @Override
                            public void run() {
                                ((Enemy)soldier).setVisible(true);
                                soldier.getBody().getFixtureList().first().setSensor(false);
                            }
                        }, 1);
                    } else {
                        setState(State.FINDING_PATH);
                    }
                }
            }
        }
    }

    private void loot() {
        if (!isLooting) {
            isLooting = true;
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    isLooting = false;
                    setState(State.CALCULATING_ROUTE);
                }
            }, 3);
        }
    }

    private void findPath() {
        if (!isGoingByAltPath) {
            altTarget = new Vector2();
            altTarget.x = soldier.getBody().getPosition().x + MathUtils.randomRange(-5, 5);
            altTarget.y = soldier.getBody().getPosition().y + MathUtils.randomRange(-5, 5);
            isGoingByAltPath = true;
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    isAltPathTimedOut = true;
                }
            }, 5);
        } else {
            if (MathUtils.distance(soldier.getBody().getPosition(), altTarget) < 1 || isAltPathTimedOut) {
                setState(State.CALCULATING_ROUTE);
                isGoingByAltPath = false;
                isAltPathTimedOut = false;
            } else {
                ((SteeringBehavior)soldier).seek(altTarget);
            }
        }
    }

    private void setState(State state) {
        previousState = this.state;
        this.state = state;
    }
}
