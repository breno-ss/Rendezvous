package com.bressio.rendezvous.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class Player extends Soldier {

    public Player(World world, Match match, float radius, float linearDamping, float x, float y) {
        super(world, match, x, y, radius, linearDamping, ResourceHandler.AnimationRegion.PLAYER);
    }
}
