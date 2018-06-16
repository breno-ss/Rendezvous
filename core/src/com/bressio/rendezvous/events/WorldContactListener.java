package com.bressio.rendezvous.events;

import com.badlogic.gdx.physics.box2d.*;
import com.bressio.rendezvous.entities.tiles.InteractiveTile;

public class WorldContactListener implements ContactListener {

    private void sendContactMessage(Contact contact, boolean isLeave) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "player" || fixtureB.getUserData() == "player") {
            Fixture player = fixtureA.getUserData() == "player" ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof InteractiveTile) {
                if (!isLeave) {
                    ((InteractiveTile) object.getUserData()).onPlayerEnter();
                } else {
                    ((InteractiveTile) object.getUserData()).onPlayerLeave();
                }
            }
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
