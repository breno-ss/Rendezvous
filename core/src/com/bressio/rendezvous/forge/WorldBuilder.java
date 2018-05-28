package com.bressio.rendezvous.forge;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScale;
import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScaleCenter;

public class WorldBuilder {

    private enum Layer {
        OCEAN(2),
        ROCK(5);
        Layer(int layer) { this.layer = layer; }
        private final int layer;
    }

    public WorldBuilder(World world, TiledMap map) {

        // ocean
        for (MapObject object : map.getLayers().get(Layer.OCEAN.layer).getObjects().getByType(PolygonMapObject.class)) {
            Polygon rect = ((PolygonMapObject) object).getPolygon();
            new BodyBuilder(world, pScale(rect.getX() + pCenter(rect.getOriginX()), rect.getY() + pCenter(rect.getOriginY())))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withVertices(pScale(rect.getVertices()))
                    .build();
        }

        // large rocks
        for (MapObject object : map.getLayers().get(Layer.ROCK.layer).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse rect = ((EllipseMapObject) object).getEllipse();
            new BodyBuilder(world, pScale(rect.x + pCenter(rect.width), rect.y + pCenter(rect.width)))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withRadius(pScaleCenter(rect.width))
                    .build();
        }

//        for (MapObject object : map.getLayers().get(Layer.OCEAN.layer).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            new BodyBuilder(world, pScale(rect.getX() + pCenter(rect.getWidth()), rect.getY() + pCenter(rect.getHeight())))
//                    .withBodyType(BodyDef.BodyType.StaticBody)
//                    .withWidth(pScaleCenter(rect.getWidth()))
//                    .withHeight(pScaleCenter(rect.getHeight()))
//                    .build();
//        }

        // build weapons
//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            new Weapon(world, map, rect);
//        }
    }
}
