package com.bressio.rendezvous.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.entities.Entity;
import com.bressio.rendezvous.scenes.Match;

public class EntityObject extends Entity {

    EntityObject(World world, Match match, float x, float y, String animationRegion) {
        super(world, match, x, y, animationRegion);
    }

    @Override
    protected void create() {

    }
}
