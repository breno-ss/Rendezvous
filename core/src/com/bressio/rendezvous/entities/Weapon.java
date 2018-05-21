package com.bressio.rendezvous.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Weapon extends InteractiveObject{
    public Weapon(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
