package com.bressio.rendezvous.events;

import com.badlogic.gdx.physics.box2d.*;
import com.bressio.rendezvous.entities.InteractiveObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "player" || fixtureB.getUserData() == "player") {
            Fixture player = fixtureA.getUserData() == "player" ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof InteractiveObject) {
               ((InteractiveObject) object.getUserData()).onPlayerEnter();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "player" || fixtureB.getUserData() == "player") {
            Fixture player = fixtureA.getUserData() == "player" ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof InteractiveObject) {
                ((InteractiveObject) object.getUserData()).onPlayerLeave();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
