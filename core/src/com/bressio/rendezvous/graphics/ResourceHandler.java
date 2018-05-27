package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import com.bressio.rendezvous.languages.Internationalization;

public final class ResourceHandler implements Disposable {

    public enum AnimationAtlas {
        ENTITIES("textures/animations/entities.pack");
        AnimationAtlas(String path) { this.path = path; }
        private String path;
        private String getPath() { return path; }
    }

    public enum TileMap {
        TILEMAP("tiles/tilemap.tmx");
        TileMap(String path) { this.path = path; }
        private String path;
        private String getPath() { return path; }
    }

    public enum Cursor {
        MATCH_CURSOR("textures/cursors/match-cursor.png"),
        MENU_CURSOR("textures/cursors/menu-cursor.png");
        Cursor(String path) { this.path = path; }
        private String path;
        private String getPath() { return path; }
    }

    public enum AnimationRegion {
        PLAYER("player_animation", 0, 1, 64, 6, .1f, 0, 0, 64, 64);
        AnimationRegion(String region, int startRow, int startColumn, int frameSize, int amountFrames, float frameDuration,
                        int idleTextureX, int idleTextureY, int idleTextureWidth, int idleTextureHeight) {
            this.region = region;
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.frameSize = frameSize;
            this.amountFrames = amountFrames;
            this.frameDuration = frameDuration;
            this.idleTextureX = idleTextureX;
            this.idleTextureY = idleTextureY;
            this.idleTextureWidth = idleTextureWidth;
            this.idleTextureHeight = idleTextureHeight;
        }

        private String region;
        private int startRow;
        private int startColumn;
        private int frameSize;
        private int amountFrames;
        private float frameDuration;
        private int idleTextureX;
        private int idleTextureY;
        private int idleTextureWidth;
        private int idleTextureHeight;

        public String getRegion() { return region; }
        public int getStartRow() { return startRow; }
        public int getStartColumn() { return startColumn; }
        public int getFrameSize() { return frameSize; }
        public int getAmountFrames() { return amountFrames; }
        public float getFrameDuration() { return frameDuration; }
        public int getIdleTextureX() { return idleTextureX; }
        public int getIdleTextureY() { return idleTextureY; }
        public int getIdleTextureWidth() { return idleTextureWidth; }
        public int getIdleTextureHeight() { return idleTextureHeight; }
    }

    private Internationalization i18n;
    private TextureAtlas atlas;
    private TmxMapLoader mapLoader;
    private TiledMap map;

    public ResourceHandler(AnimationAtlas animationAtlas, TileMap tileMap) {
        i18n = new Internationalization();
        atlas = new TextureAtlas(animationAtlas.getPath());
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(tileMap.getPath());
    }

    public TiledMap getMap() {
        return map;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Internationalization getI18n() {
        return i18n;
    }

    public static String getCursor(Cursor cursor) {
        return cursor.getPath();
    }

    @Override
    public void dispose() {
        map.dispose();
        atlas.dispose();
    }
}
