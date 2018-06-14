package com.bressio.rendezvous.forge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.entities.Building;
import com.bressio.rendezvous.entities.Chest;
import com.bressio.rendezvous.entities.Crate;
import com.bressio.rendezvous.entities.Loot;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;
import java.util.Random;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class WorldBuilder {

    private TiledMap map;

    private enum Layer {
        OCEAN(0),
        ROCK(1),
        TREE(2),
        BUILDING(3),
        PLAYER_SPAWN_POINTS(4),
        BUILDING_SENSOR(5),
        CHEST(6),
        CRATE(7);
        Layer(int layer) { this.layer = layer; }
        private final int layer;
    }

    private ArrayList<Crate> crates;
    private ArrayList<Chest> chests;

    public WorldBuilder(World world, TiledMap map, SpriteBatch batch, ResourceHandler resources, Match match) {

        chests = new ArrayList<>();
        crates = new ArrayList<>();

        this.map = map;

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

        // trees
        for (MapObject object : map.getLayers().get(Layer.TREE.layer).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse rect = ((EllipseMapObject) object).getEllipse();
            new BodyBuilder(world, pScale(rect.x + pCenter(rect.width), rect.y + pCenter(rect.width)))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withRadius(pScaleCenter(rect.width))
                    .build();
        }

        // buildings
        for (MapObject object : map.getLayers().get(Layer.BUILDING.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new BodyBuilder(world, pScale(rect.getX() + pCenter(rect.getWidth()), rect.getY() + pCenter(rect.getHeight())))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withWidth(pScaleCenter(rect.getWidth()))
                    .withHeight(pScaleCenter(rect.getHeight()))
                    .build();
        }

        // chests
        for (MapObject object : map.getLayers().get(Layer.CHEST.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            chests.add(new Chest(world, map, rect, batch, resources, match));
        }

        // crates
        for (MapObject object : map.getLayers().get(Layer.CRATE.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            crates.add(new Crate(world, map, rect, batch, resources, match));
        }

        // building sensors
        for (MapObject object : map.getLayers().get(Layer.BUILDING_SENSOR.layer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Building(world, map, rect);
        }
    }

    public Vector2 getPlayerSpawnPoint() {
        Vector2[] spawnPoints = new Vector2[20];
        int count = 0;

        for (MapObject object : map.getLayers().get(Layer.PLAYER_SPAWN_POINTS.layer).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse rect = ((EllipseMapObject) object).getEllipse();
            spawnPoints[count] = new Vector2(rect.x, rect.y);
            count++;
        }

        int randomIndex = new Random().nextInt(spawnPoints.length);
        return spawnPoints[randomIndex];
    }

    public ArrayList<Loot> getLoot() {
        ArrayList<Loot> loot = new ArrayList<>();
        loot.addAll(crates);
        loot.addAll(chests);
        return loot;
    }
}
