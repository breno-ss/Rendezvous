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
        EVENT_BACKGROUND("textures/gui/backgrounds/event-background.png"),
        SAFEZONE("textures/world/safezone.png"),
        DANGER_ZONE("textures/world/danger-zone.png"),
        EMPTY_BAR("textures/gui/health/empty-bar.png"),
        HEALTH_BAR("textures/gui/health/health-bar.png"),
        ARMOR_BAR("textures/gui/health/armor-bar.png"),
        HEALTH_ICON("textures/gui/health/health-icon.png"),
        ARMOR_ICON("textures/gui/health/armor-icon.png"),
        VIGNETTE("textures/overlay/vignette.png"),
        INTERACTION_BUTTON("textures/world/interaction-button.png"),
        EXCHANGE_ICON("textures/gui/icons/exchange.png"),
        LOADING_SCREEN("textures/gui/backgrounds/loading-screen.png"),
        GAME_ICON("textures/gui/icons/game-icon.png"),
        EMPTY_SLOT("textures/gui/items/empty-slot.png"),
        MEDKIT_ICON("textures/gui/items/medkit.png"),
        INVENTORY("textures/gui/items/inventory.png"),
        INVISIBLE_SLOT("textures/gui/items/invisible-slot.png"),
        SELECTED_SLOT("textures/gui/items/selected-slot.png"),
        GENERIC_PROGRESS_BAR("textures/gui/bars/generic-progress-bar.png"),
        SOLDIER_BODY("textures/gui/icons/soldier-body.png"),
        COMBAT_HELMET("textures/gui/items/equipment/combat-helmet.png"),
        HALF_HELMET("textures/gui/items/equipment/half-helmet.png"),
        MILITARY_VEST("textures/gui/items/equipment/military-vest.png"),
        SOFT_VEST("textures/gui/items/equipment/soft-vest.png"),
        AW3("textures/gui/items/weapons/aw3.png"),
        G21("textures/gui/items/weapons/g21.png"),
        M20("textures/gui/items/weapons/m20.png"),
        P26("textures/gui/items/weapons/p26.png"),
        STAR("textures/gui/items/weapons/star.png"),
        W16A("textures/gui/items/weapons/w16a.png"),
        FIVE_FIVE_SIX("textures/gui/items/ammo/five-five-six.png"),
        NINE("textures/gui/items/ammo/nine.png"),
        SEVEN_SIX_TWO("textures/gui/items/ammo/seven-six-two.png"),
        BULLET("textures/projectiles/bullet.png"),
        POINTLIGHT("textures/world/pointlight.png");

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
        SOLDIER_ATLAS("textures/animations/soldier-animation.pack"),
        SOLDIER_MEDKIT_ATLAS("textures/animations/soldier-medkit-animation.pack"),
        SOLDIER_MILITARY_VEST_ATLAS("textures/animations/soldier-military-vest-animation.pack"),
        SOLDIER_SOFT_VEST_ATLAS("textures/animations/soldier-soft-vest-animation.pack"),
        SOLDIER_COMBAT_HELMET_ATLAS("textures/animations/soldier-combat-helmet-animation.pack"),
        SOLDIER_HALF_HELMET_ATLAS("textures/animations/soldier-half-helmet-animation.pack"),
        SOLDIER_MV_COMBAT_HELMET_ATLAS("textures/animations/soldier-mv-combat-helmet-animation.pack"),
        SOLDIER_MV_HALF_HELMET_ATLAS("textures/animations/soldier-mv-half-helmet-animation.pack"),
        SOLDIER_SV_COMBAT_HELMET_ATLAS("textures/animations/soldier-sv-combat-helmet-animation.pack"),
        SOLDIER_SV_HALF_HELMET_ATLAS("textures/animations/soldier-sv-half-helmet-animation.pack"),
        SOLDIER_SV_MEDKIT_ATLAS("textures/animations/soldier-sv-medkit-animation.pack"),
        SOLDIER_MV_MEDKIT_ATLAS("textures/animations/soldier-mv-medkit-animation.pack"),
        SOLDIER_CT_MEDKIT_ATLAS("textures/animations/soldier-ct-medkit-animation.pack"),
        SOLDIER_HT_MEDKIT_ATLAS("textures/animations/soldier-ht-medkit-animation.pack"),
        SOLDIER_MV_CT_MEDKIT_ATLAS("textures/animations/soldier-mv-ct-medkit-animation.pack"),
        SOLDIER_MV_HT_MEDKIT_ATLAS("textures/animations/soldier-mv-ht-medkit-animation.pack"),
        SOLDIER_SV_CT_MEDKIT_ATLAS("textures/animations/soldier-sv-ct-medkit-animation.pack"),
        SOLDIER_SV_HT_MEDKIT_ATLAS("textures/animations/soldier-sv-ht-medkit-animation.pack"),
        SOLDIER_STAR_ATLAS("textures/animations/soldier-star-animation.pack"),
        SOLDIER_MV_STAR_ATLAS("textures/animations/soldier-mv-star-animation.pack"),
        SOLDIER_SV_STAR_ATLAS("textures/animations/soldier-sv-star-animation.pack"),
        SOLDIER_CT_STAR_ATLAS("textures/animations/soldier-ct-star-animation.pack"),
        SOLDIER_HT_STAR_ATLAS("textures/animations/soldier-ht-star-animation.pack"),
        SOLDIER_MV_CT_STAR_ATLAS("textures/animations/soldier-mv-ct-star-animation.pack"),
        SOLDIER_MV_HT_STAR_ATLAS("textures/animations/soldier-mv-ht-star-animation.pack"),
        SOLDIER_SV_CT_STAR_ATLAS("textures/animations/soldier-sv-ct-star-animation.pack"),
        SOLDIER_SV_HT_STAR_ATLAS("textures/animations/soldier-sv-ht-star-animation.pack"),
        SOLDIER_W16A_ATLAS("textures/animations/soldier-w16a-animation.pack"),
        SOLDIER_MV_W16A_ATLAS("textures/animations/soldier-mv-w16a-animation.pack"),
        SOLDIER_SV_W16A_ATLAS("textures/animations/soldier-sv-w16a-animation.pack"),
        SOLDIER_CT_W16A_ATLAS("textures/animations/soldier-ct-w16a-animation.pack"),
        SOLDIER_HT_W16A_ATLAS("textures/animations/soldier-ht-w16a-animation.pack"),
        SOLDIER_MV_CT_W16A_ATLAS("textures/animations/soldier-mv-ct-w16a-animation.pack"),
        SOLDIER_MV_HT_W16A_ATLAS("textures/animations/soldier-mv-ht-w16a-animation.pack"),
        SOLDIER_SV_CT_W16A_ATLAS("textures/animations/soldier-sv-ct-w16a-animation.pack"),
        SOLDIER_SV_HT_W16A_ATLAS("textures/animations/soldier-sv-ht-w16a-animation.pack"),
        SOLDIER_G21_ATLAS("textures/animations/soldier-g21-animation.pack"),
        SOLDIER_MV_G21_ATLAS("textures/animations/soldier-mv-g21-animation.pack"),
        SOLDIER_SV_G21_ATLAS("textures/animations/soldier-sv-g21-animation.pack"),
        SOLDIER_CT_G21_ATLAS("textures/animations/soldier-ct-g21-animation.pack"),
        SOLDIER_HT_G21_ATLAS("textures/animations/soldier-ht-g21-animation.pack"),
        SOLDIER_MV_CT_G21_ATLAS("textures/animations/soldier-mv-ct-g21-animation.pack"),
        SOLDIER_MV_HT_G21_ATLAS("textures/animations/soldier-mv-ht-g21-animation.pack"),
        SOLDIER_SV_CT_G21_ATLAS("textures/animations/soldier-sv-ct-g21-animation.pack"),
        SOLDIER_SV_HT_G21_ATLAS("textures/animations/soldier-sv-ht-g21-animation.pack"),

        SOLDIER_P26_ATLAS("textures/animations/soldier-p26-animation.pack"),
        SOLDIER_MV_P26_ATLAS("textures/animations/soldier-mv-p26-animation.pack"),
        SOLDIER_SV_P26_ATLAS("textures/animations/soldier-sv-p26-animation.pack"),
        SOLDIER_CT_P26_ATLAS("textures/animations/soldier-ct-p26-animation.pack"),
        SOLDIER_HT_P26_ATLAS("textures/animations/soldier-ht-p26-animation.pack"),
        SOLDIER_MV_CT_P26_ATLAS("textures/animations/soldier-mv-ct-p26-animation.pack"),
        SOLDIER_MV_HT_P26_ATLAS("textures/animations/soldier-mv-ht-p26-animation.pack"),
        SOLDIER_SV_CT_P26_ATLAS("textures/animations/soldier-sv-ct-p26-animation.pack"),
        SOLDIER_SV_HT_P26_ATLAS("textures/animations/soldier-sv-ht-p26-animation.pack"),

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
        assetManager.load(TexturePath.LOADING_SCREEN.path, Texture.class);
        assetManager.load(TexturePath.GAME_ICON.path, Texture.class);
        assetManager.finishLoading();
    }

    public void loadMatchResources() {
        assetManager.load(PixmapPath.MATCH_CURSOR.path, Pixmap.class);
        assetManager.load(PixmapPath.MENU_CURSOR.path, Pixmap.class);
        assetManager.load(TextureAtlasPath.SOLDIER_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MILITARY_VEST_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SOFT_VEST_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_COMBAT_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HALF_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_COMBAT_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HALF_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_COMBAT_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HALF_HELMET_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_CT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_CT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_CT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HT_MEDKIT_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_CT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_CT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_CT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HT_STAR_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_CT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_CT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_CT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HT_W16A_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_CT_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HT_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_CT_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HT_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_CT_G21_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HT_G21_ATLAS.path, TextureAtlas.class);

        assetManager.load(TextureAtlasPath.SOLDIER_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_CT_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_HT_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_CT_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_MV_HT_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_CT_P26_ATLAS.path, TextureAtlas.class);
        assetManager.load(TextureAtlasPath.SOLDIER_SV_HT_P26_ATLAS.path, TextureAtlas.class);

        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(TiledMapPath.TILEMAP.path, TiledMap.class);
        assetManager.load(TiledMapPath.OVER_TILEMAP.path, TiledMap.class);
        assetManager.load(TexturePath.BLACK_BACKGROUND.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MAP.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MINIMAP.path, Texture.class);
        assetManager.load(TexturePath.MATCH_MINIMAP_FRAME.path, Texture.class);
        assetManager.load(TexturePath.PLAYER_MARK.path, Texture.class);
        assetManager.load(TexturePath.EVENT_BACKGROUND.path, Texture.class);
        assetManager.load(TexturePath.SAFEZONE.path, Texture.class);
        assetManager.load(TexturePath.DANGER_ZONE.path, Texture.class);
        assetManager.load(TexturePath.EMPTY_BAR.path, Texture.class);
        assetManager.load(TexturePath.HEALTH_BAR.path, Texture.class);
        assetManager.load(TexturePath.ARMOR_BAR.path, Texture.class);
        assetManager.load(TexturePath.HEALTH_ICON.path, Texture.class);
        assetManager.load(TexturePath.ARMOR_ICON.path, Texture.class);
        assetManager.load(TexturePath.VIGNETTE.path, Texture.class);
        assetManager.load(TexturePath.INTERACTION_BUTTON.path, Texture.class);
        assetManager.load(TexturePath.EXCHANGE_ICON.path, Texture.class);
        assetManager.load(TexturePath.EMPTY_SLOT.path, Texture.class);
        assetManager.load(TexturePath.MEDKIT_ICON.path, Texture.class);
        assetManager.load(TexturePath.INVENTORY.path, Texture.class);
        assetManager.load(TexturePath.INVISIBLE_SLOT.path, Texture.class);
        assetManager.load(TexturePath.SELECTED_SLOT.path, Texture.class);
        assetManager.load(TexturePath.GENERIC_PROGRESS_BAR.path, Texture.class);
        assetManager.load(TexturePath.SOLDIER_BODY.path, Texture.class);
        assetManager.load(TexturePath.COMBAT_HELMET.path, Texture.class);
        assetManager.load(TexturePath.HALF_HELMET.path, Texture.class);
        assetManager.load(TexturePath.MILITARY_VEST.path, Texture.class);
        assetManager.load(TexturePath.SOFT_VEST.path, Texture.class);
        assetManager.load(TexturePath.AW3.path, Texture.class);
        assetManager.load(TexturePath.G21.path, Texture.class);
        assetManager.load(TexturePath.M20.path, Texture.class);
        assetManager.load(TexturePath.P26.path, Texture.class);
        assetManager.load(TexturePath.STAR.path, Texture.class);
        assetManager.load(TexturePath.W16A.path, Texture.class);
        assetManager.load(TexturePath.FIVE_FIVE_SIX.path, Texture.class);
        assetManager.load(TexturePath.NINE.path, Texture.class);
        assetManager.load(TexturePath.SEVEN_SIX_TWO.path, Texture.class);
        assetManager.load(TexturePath.BULLET.path, Texture.class);
        assetManager.load(TexturePath.POINTLIGHT.path, Texture.class);
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
