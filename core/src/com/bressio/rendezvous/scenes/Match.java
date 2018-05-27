package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.entities.Player;
import com.bressio.rendezvous.events.InputTracker;
import com.bressio.rendezvous.forge.WorldBuilder;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.gui.HUD;
import com.bressio.rendezvous.scheme.PhysicsAdapter;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;
import static com.bressio.rendezvous.scheme.PlayerSettings.*;

public class Match implements Screen {

    // game
    private Rendezvous game;

    // rendering
    private OrthographicCamera camera;
    private Viewport viewport;
    private HUD hud;
    private OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer collisionDebugRenderer;

    // world
    private World world;
    private Player player;

    // events
    private InputTracker input;

    // resources
    private ResourceHandler resources;

    Match(Rendezvous game) {
        this.game = game;
        loadResources();
        setupCamera();
        setupRenderer();
        setupCursor();
        forgeWorld();
        setupInputTracker();
    }

    private void loadResources() {
        resources = new ResourceHandler(ResourceHandler.AnimationAtlas.ENTITIES, ResourceHandler.TileMap.TILEMAP);
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(pScale(GAME_WIDTH), pScale(GAME_HEIGHT), camera);
        viewport.apply();
        hud = new HUD(game.getBatch(), resources);
        camera.position.set(pScale((float) Math.sqrt(MAP_AREA)), pScale((float) Math.sqrt(MAP_AREA)), 0);
        camera.update();
    }

    private void setupRenderer() {
        renderer = new OrthogonalTiledMapRenderer(resources.getMap(), PhysicsAdapter.getScale());
        collisionDebugRenderer = new Box2DDebugRenderer();
    }

    private void setupCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(ResourceHandler.getCursor(ResourceHandler.Cursor.MATCH_CURSOR)));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, pCenter(pixmap.getWidth()), pCenter(pixmap.getHeight())));
        pixmap.dispose();
    }

    private void forgeWorld() {
        world = new World(GRAVITY, true);
        new WorldBuilder(world, resources.getMap());
        player = new Player(world, this, 32, 5, 10, 3200, 3200);
    }

    private void setupInputTracker() {
        input = new InputTracker(camera);
        Gdx.input.setInputProcessor(input);
    }

    private void update(float delta) {
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        camera.position.set(player.getBody().getPosition(), 0);
        camera.update();
        renderer.setView(camera);
        input.update();
    }

    public ResourceHandler getResources() {
        return resources;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        if (DEBUG_MODE) {
            collisionDebugRenderer.render(world, camera.combined);
        }

        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        player.draw(game.getBatch());
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
        collisionDebugRenderer.dispose();
    }
}
