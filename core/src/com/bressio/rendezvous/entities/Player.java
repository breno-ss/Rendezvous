package com.bressio.rendezvous.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.scenes.Match;

public class Player extends Soldier {

    public Player(World world, Match match, float x, float y) {
        super(world, match, x, y, "player_animation");
    }
}
