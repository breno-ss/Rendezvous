package com.bressio.rendezvous.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.helpers.PhysicsManager;

public class Barrier extends Sprite {

    private World world;
    private Body rigidbody;
    private final int PPM;

    public Barrier(World world) {
        PPM = PhysicsManager.PPM;
        start();
    }

    private void start() {

    }
}
