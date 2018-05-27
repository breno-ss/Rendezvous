package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
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
        MENU_LOGO("textures/gui/logos/menu-logo.png");
        private String path;
        TexturePath(String path) { this.path = path; }
    }

    public enum SkinPath {
        BUTTON_SKIN("skins/button.json");
        private String path;
        SkinPath(String path) { this.path = path; }
    }

    public enum TextureAtlasPath {
        BUTTON_ATLAS("textures/gui/buttons/buttons.pack"),
        ENTITY_ATLAS("textures/animations/entities.pack");
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
        TILEMAP("tiles/tilemap.tmx");
        private String path;
        TiledMapPath(String path) { this.path = path; }
    }

    private AssetManager assetManager;

    public ResourceHandler() {
        assetManager = new AssetManager();
    }

    public void loadMainMenuResources() {
        assetManager.load(TexturePath.MENU_BACKGROUND.path, Texture.class);
        assetManager.load(TexturePath.MENU_LOGO.path, Texture.class);
        assetManager.load(SkinPath.BUTTON_SKIN.path, Skin.class,
                new SkinLoader.SkinParameter(TextureAtlasPath.BUTTON_ATLAS.path));
        assetManager.load(PixmapPath.MENU_CURSOR.path, Pixmap.class);
        assetManager.finishLoading();
    }

    public void loadMatchResources() {
        assetManager.load(PixmapPath.MATCH_CURSOR.path, Pixmap.class);
        assetManager.load(TextureAtlasPath.ENTITY_ATLAS.path, TextureAtlas.class);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(TiledMapPath.TILEMAP.path, TiledMap.class);
        assetManager.finishLoading();
    }

    public Texture getTexture(TexturePath texture) {
        return assetManager.get(texture.path, Texture.class);
    }

    public TextureAtlas getTextureAtlas(TextureAtlasPath textureAtlas) {
        return assetManager.get(textureAtlas.path, TextureAtlas.class);
    }

    public Skin getSkin(SkinPath skin) {
        return assetManager.get(skin.path, Skin.class);
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
