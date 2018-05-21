package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.gui.HUD;
import com.bressio.rendezvous.helpers.PhysicsManager;
import com.bressio.rendezvous.helpers.PlayerSettings;

public class Match implements Screen {

    private final int WIDTH;
    private final int HEIGHT;
    private final int PPM;

    private Rendezvous game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Match(Rendezvous game) {
        WIDTH = PlayerSettings.GAME_WIDTH;
        HEIGHT = PlayerSettings.GAME_HEIGHT;
        PPM = PhysicsManager.PPM;

        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1366, 768, camera);
        hud = new HUD(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tiles/tilemap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(3200, 3200, 0);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= 1000 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += 1000 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= 1000 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += 1000 * delta;
        }
    }

    private void update(float delta) {
        handleInput(delta);
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

//        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
//
//        game.getBatch().begin();
//        game.getBatch().end();
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

    }
}
