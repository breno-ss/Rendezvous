package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public final class ResourceHandler implements Disposable {

    public enum TexturePath {
        MENU_BACKGROUND("textures/gui/backgrounds/menu-background.png"),
        MENU_LOGO("textures/gui/logos/menu-logo.png"),
        MATCH_MAP("textures/gui/maps/map-expanded.png"),
        MATCH_MINIMAP("textures/gui/maps/minimap.png"),
        MATCH_MINIMAP_FRAME("textures/gui/maps/minimap-frame.png"),
        PLAYER_MARK("textures/gui/maps/player-mark.png"),
        BLACK_BACKGROUND("textures/gui/backgrounds/black-background.png"),
        EVENT_BACKGROUND("textures/gui/backgrounds/event-background.png");
        private String path;
        TexturePath(String path) { this.path = path; }
    }

    public enum SkinPaths {
        BUTTON_SKIN("skins/button.json", "bombard", FontPath.BOMBARD, 36, TextureAtlasPath.BUTTON_ATLAS),
        WINDOW_SKIN("skins/vis/skin/x2/uiskin.json", "bombard", FontPath.BOMBARD, 26, TextureAtlasPath.WINDOW_ATLAS);
        private String path;
        private String fontName;
        private FontPath fontPath;
        private int fontSize;
        private TextureAtlasPath atlasPath;
        SkinPaths(String path, String fontName, FontPath fontPath, int fontSize, TextureAtlasPath atlasPath) {
            this.path = path;
            this.fontName = fontName;
            this.fontPath = fontPath;
            this.fontSize = fontSize;
            this.atlasPath = atlasPath;
        }
    }

    public enum TextureAtlasPath {
        BUTTON_ATLAS("textures/gui/buttons/buttons.pack"),
        ENTITY_ATLAS("textures/animations/entities.pack"),
        WINDOW_ATLAS("skins/vis/skin/x2/uiskin.atlas");
        private String path;
        TextureAtlasPath(String path) { this.path = path; }
    }

    public enum PixmapPath {
        MENU_CURSOR("textures/cursors/menu-cursor.png"),
        MATCH_CURSOR("textures/cursors/match-cursor.png");
        private String path;
        PixmapPath(String path) { this.path = path; }
    }

    public enum TiledMapPath {
        TILEMAP("tiles/map.tmx"),
        OVER_TILEMAP("tiles/overmap.tmx");
        private String path;
        TiledMapPath(String path) { this.path = path; }
    }

    public enum FontPath {
        BOMBARD("fonts/BOMBARD.ttf");
        public String path;
        FontPath(String path) { this.path = path; }
    }

    private AssetManager assetManager;

    public ResourceHandler() {
        assetManager = new AssetManager();
    }

    public void loadMainMenuResources() {
        assetManager.load(TexturePath.MENU_BACKGROUND.path, Texture.class);
        assetManager.load(TexturePath.MENU_LOGO.path, Texture.class);
        assetManager.load(PixmapPath.MENU_CURSOR.path, Pixmap.class);
        assetManager.finishLoading();
    }

    public void loadMatchResources() {
        assetManager.load(PixmapPath.MATCH_CURSOR.path, Pixmap.class);
        assetManager.load(PixmapPath.MENU_CURSOR.path, Pixmap.class);
        assetManager.load(TextureAtlasPath.ENTITY_ATLAS.path, TextureAtlas.class);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(TiledMapPath.TILEMAP.path, TiledMap.class);
        assetManager.load(TiledMapPath.OVER_TILEMAP.path, TiledMap.class);
        assetManager.load(TexturePath.BLACK_BACKGROUND.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MAP.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MINIMAP.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MINIMAP_FRAME.path, Texture.class);
        assetManager.load(TexturePath.PLAYER_MARK.path, Texture.class);
        assetManager.load(TexturePath.EVENT_BACKGROUND.path, Texture.class);
        assetManager.finishLoading();
    }

    public Texture getTexture(TexturePath texture) {
        return assetManager.get(texture.path, Texture.class);
    }

    public TextureAtlas getTextureAtlas(TextureAtlasPath textureAtlas) {
        return assetManager.get(textureAtlas.path, TextureAtlas.class);
    }

    public Skin getSkin(SkinPaths skinPaths) {
        Skin skin = new Skin();
        skin.add(skinPaths.fontName, FontGenerator.generate(skinPaths.fontPath, skinPaths.fontSize, false));
        skin.addRegions(new TextureAtlas(Gdx.files.internal(skinPaths.atlasPath.path)));
        skin.load(Gdx.files.internal(skinPaths.path));
        return skin;
    }

    public Pixmap getPixmap(PixmapPath pixmap) {
        return assetManager.get(pixmap.path, Pixmap.class);
    }

    public TiledMap getTiledMap(TiledMapPath tiledmap) {
        return assetManager.get(tiledmap.path, TiledMap.class);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
