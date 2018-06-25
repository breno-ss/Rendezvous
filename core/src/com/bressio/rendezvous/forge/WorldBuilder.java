package com.bressio.rendezvous.forge;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bressio.rendezvous.entities.tiles.Building;
import com.bressio.rendezvous.entities.tiles.Chest;
import com.bressio.rendezvous.entities.tiles.Crate;
import com.bressio.rendezvous.entities.tiles.Loot;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;
import java.util.Random;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class WorldBuilder {

    private enum Layer {
        OCEAN(0),
        ROCK(1),
        TREE(2),
        BUILDING(3),
        PLAYER_SPAWN_POINTS(4),
        BUILDING_SENSOR(5),
        CHEST(6),
        CRATE(7);
        Layer(int index) { this.index = index; }
        private final int index;
    }

    private ArrayList<Crate> crates;
    private ArrayList<Chest> chests;
    private Match match;

    public WorldBuilder(Match match) {
        this.match = match;
        init();
        buildPolygonalObject(Layer.OCEAN);
        buildEllipticalObject(Layer.ROCK);
        buildEllipticalObject(Layer.TREE);
        buildRectangularObject(Layer.BUILDING);
        buildChests();
        buildCrates();
        buildBuildingSensors();
    }

    private void init() {
        chests = new ArrayList<>();
        crates = new ArrayList<>();
    }

    private void buildPolygonalObject(Layer layer) {
        for (MapObject object :
                match.getMap().getLayers().get(layer.index).getObjects().getByType(PolygonMapObject.class)) {
            Polygon pol = ((PolygonMapObject) object).getPolygon();
            new BodyBuilder(match.getWorld(), pScale(
                    pol.getX() + pCenter(pol.getOriginX()),
                    pol.getY() + pCenter(pol.getOriginY())))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withMaskBits(PLAYER_TAG)
                    .withCategoryBits(WATER_TAG)
                    .withVertices(pScale(pol.getVertices()))
                    .build();
        }
    }

    private void buildEllipticalObject(Layer layer) {
        for (MapObject object :
                match.getMap().getLayers().get(layer.index).getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ell = ((EllipseMapObject) object).getEllipse();
            new BodyBuilder(match.getWorld(), pScale(ell.x + pCenter(ell.width), ell.y + pCenter(ell.width)))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withRadius(pScaleCenter(ell.width))
                    .build();
        }
    }

    private void buildRectangularObject(Layer layer) {
        for (MapObject object :
                match.getMap().getLayers().get(layer.index).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new BodyBuilder(match.getWorld(), pScale(
                    rect.getX() + pCenter(rect.getWidth()),
                    rect.getY() + pCenter(rect.getHeight())))
                    .withBodyType(BodyDef.BodyType.StaticBody)
                    .withWidth(pScaleCenter(rect.getWidth()))
                    .withHeight(pScaleCenter(rect.getHeight()))
                    .build();
        }
    }

    private void buildChests() {
        for (MapObject object :
                match.getMap().getLayers().get(Layer.CHEST.index).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            chests.add(new Chest(rect, match));
        }
    }

    private void buildCrates() {
        for (MapObject object :
                match.getMap().getLayers().get(Layer.CRATE.index).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            crates.add(new Crate(rect, match));
        }
    }

    private void buildBuildingSensors() {
        for (MapObject object :
                match.getMap().getLayers().get(Layer.BUILDING_SENSOR.index).getObjects()
                        .getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Building(rect, match);
        }
    }

    public Vector2 getPlayerSpawnPoint() {
        Vector2[] spawnPoints = new Vector2[20];
        int count = 0;

        for (MapObject object :
                match.getMap().getLayers().get(Layer.PLAYER_SPAWN_POINTS.index).getObjects()
                        .getByType(EllipseMapObject.class)) {
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
