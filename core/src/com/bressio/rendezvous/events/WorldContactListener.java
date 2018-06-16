package com.bressio.rendezvous.events;

import com.badlogic.gdx.physics.box2d.*;
import com.bressio.rendezvous.entities.tiles.InteractiveTile;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class WorldContactListener implements ContactListener {

    private void sendContactMessage(Contact contact, boolean isEndingContact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int combination = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (combination){
            case PLAYER_TAG | BUILDING_TAG:
            case PLAYER_TAG | LOOT_TAG:
                if(fixtureA.getFilterData().categoryBits == PLAYER_TAG) {
                    if (!isEndingContact) {
                        ((InteractiveTile) fixtureB.getUserData()).onPlayerEnter();
                    } else {
                        ((InteractiveTile) fixtureB.getUserData()).onPlayerLeave();
                    }
                } else {
                    if (!isEndingContact) {
                        ((InteractiveTile) fixtureA.getUserData()).onPlayerEnter();
                    } else {
                        ((InteractiveTile) fixtureA.getUserData()).onPlayerLeave();
                    }
                }
                break;
        }
    }

    @Override
    public void beginContact(Contact contact) {
        sendContactMessage(contact, false);
    }

    @Override
    public void endContact(Contact contact) {
        sendContactMessage(contact, true);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
