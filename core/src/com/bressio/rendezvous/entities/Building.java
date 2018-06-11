package com.bressio.rendezvous.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.BUILDING_TAG;
import static com.bressio.rendezvous.scheme.PhysicsAdapter.pUnscale;

public class Building extends InteractiveObject{

    private int[][] ceilingTileIds;
    private final int CEILING_LAYER = 23;

    public Building(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds, true, BUILDING_TAG);
        getFixture().setUserData(this);
        storeCeilingTileIds();
    }

    private void storeCeilingTileIds() {
        ceilingTileIds = new int[31][31];
        TiledMapTileLayer layer = (TiledMapTileLayer) getMap().getLayers().get(CEILING_LAYER);
        for (int i = -15; i <= 15; i++) {
            for (int j = -15; j <= 15; j++) {
                if (layer.getCell((int)((pUnscale(getBody().getPosition().x) / 32) - i),
                        (int)((pUnscale(getBody().getPosition().y) / 32) - j)) != null) {
                    ceilingTileIds[i + 15][j + 15] = layer.getCell((int)((pUnscale(getBody().getPosition().x) / 32) - i),
                            (int)((pUnscale(getBody().getPosition().y) / 32) - j)).getTile().getId();
                }
            }
        }
    }

    private void hideCeiling() {
        TiledMapTileLayer layer = (TiledMapTileLayer) getMap().getLayers().get(CEILING_LAYER);
        for (int i = -15; i <= 15; i++) {
            for (int j = -15; j <= 15; j++) {
                if (layer.getCell((int)((pUnscale(getBody().getPosition().x) / 32) - i),
                        (int)((pUnscale(getBody().getPosition().y) / 32) - j)) != null) {
                    layer.getCell((int)((pUnscale(getBody().getPosition().x) / 32) - i),
                            (int)((pUnscale(getBody().getPosition().y) / 32) - j)).setTile(getMap().getTileSets().getTile(40));
                }
            }
        }
    }

    private void showCeiling() {
        TiledMapTileLayer layer = (TiledMapTileLayer) getMap().getLayers().get(CEILING_LAYER);
        for (int i = -15; i <= 15; i++) {
            for (int j = -15; j <= 15; j++) {
                if (layer.getCell((int)((pUnscale(getBody().getPosition().x) / 32) - i),
                        (int)((pUnscale(getBody().getPosition().y) / 32) - j)) != null) {
                    layer.getCell((int) ((pUnscale(getBody().getPosition().x) / 32) - i),
                            (int) ((pUnscale(getBody().getPosition().y) / 32) - j)).setTile(getMap().getTileSets().getTile(ceilingTileIds[i + 15][j + 15]));
                }
            }
        }
    }

    @Override
    public void onPlayerEnter() {
        hideCeiling();
    }

    @Override
    public void onPlayerLeave() {
        showCeiling();
    }
}
