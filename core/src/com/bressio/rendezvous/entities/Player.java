package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.bressio.rendezvous.helpers.PhysicsManager;

public class Player extends Sprite {

    private World world;
    private Body rigidbody;
    private final int PPM;

    public Player(World world, Texture texture, float x, float y) {
        PPM = PhysicsManager.PPM;
        start();
    }

    private void start() {

    }

    public void update() {

    }

    public Body getRigidbody() {
        return rigidbody;
    }
}
