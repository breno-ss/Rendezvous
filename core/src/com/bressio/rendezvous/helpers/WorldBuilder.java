package com.bressio.rendezvous.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static com.bressio.rendezvous.helpers.PhysicsManager.pScale;

public class WorldBuilder {

    private enum Layer {
        BARRIER(3);
        Layer(int layer) { this.layer = layer; }
        private final int layer;
    }

    public WorldBuilder(World world, TiledMap map) {

        for (MapObject object : map.getLayers().get(Layer.BARRIER.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new BodyBuilder(world, pScale(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withWidth(pScale(rect.getWidth() / 2))
                    .withHeight(pScale(rect.getHeight() / 2))
                    .build();
        }

        // build weapons
//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            new Weapon(world, map, rect);
//        }
    }
}
