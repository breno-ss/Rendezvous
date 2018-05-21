package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.entities.Player;
import com.bressio.rendezvous.gui.HUD;
import com.bressio.rendezvous.helpers.PhysicsManager;
import com.bressio.rendezvous.helpers.PlayerSettings;
import com.bressio.rendezvous.helpers.WorldBuilder;

// TODO: remove PPM
public class Match implements Screen {

    private final int WIDTH;
    private final int HEIGHT;
    private final float PPM;

    private Rendezvous game;
    private TextureAtlas atlas;

    private OrthographicCamera camera;
    private Viewport viewport;
    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer boxColliderRenderer;

    private Player player;

    public Match(Rendezvous game) {
        WIDTH = PlayerSettings.GAME_WIDTH;
        HEIGHT = PlayerSettings.GAME_HEIGHT;
        PPM = PhysicsManager.PPM;

        atlas = new TextureAtlas("animations/entities.pack");

        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1366 / PPM, 768 / PPM, camera);
        hud = new HUD(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tiles/tilemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        camera.position.set(3200 / PPM, 3200 / PPM, 0);

        world = new World(PhysicsManager.gravity, true);
        boxColliderRenderer = new Box2DDebugRenderer();

        new WorldBuilder(world, map);

        player = new Player(world, this,3200, 3200);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.getBody().applyForce(new Vector2(-6, 0), player.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.getBody().applyForce(new Vector2(6, 0), player.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.getBody().applyForce(new Vector2(0, -6), player.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.getBody().applyForce(new Vector2(0, 6), player.getBody().getWorldCenter(), true);
        }
    }

    private void update(float delta) {
        handleInput(delta);
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        camera.position.set(player.getBody().getPosition(), 0);
        camera.update();
        renderer.setView(camera);
    }

    public TextureAtlas getAtlas() {
        return atlas;
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

        boxColliderRenderer.render(world, camera.combined);

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        boxColliderRenderer.dispose();
    }
}
