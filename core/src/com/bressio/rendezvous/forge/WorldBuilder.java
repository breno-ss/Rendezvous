package com.bressio.rendezvous.forge;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class WorldBuilder {

    private enum Layer {
        BARRIER(3);
        Layer(int layer) { this.layer = layer; }
        private final int layer;
    }

    public WorldBuilder(World world, TiledMap map) {

        for (MapObject object : map.getLayers().get(Layer.BARRIER.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new BodyBuilder(world, pScale(rect.getX() + pCenter(rect.getWidth()), rect.getY() + pCenter(rect.getHeight())))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withWidth(pScaleCenter(rect.getWidth()))
                    .withHeight(pScaleCenter(rect.getHeight()))
                    .build();
        }

        // build weapons
//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            new Weapon(world, map, rect);
//        }
    }
}
