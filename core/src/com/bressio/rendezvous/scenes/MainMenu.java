package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bressio.rendezvous.Rendezvous;
import com.bressio.rendezvous.graphics.ResourceHandler;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class MainMenu implements Screen {

    // game
    private Rendezvous game;

    // GUI
    private TextureAtlas atlas;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Texture logo;

    // rendering
    private OrthographicCamera camera;
    private Viewport viewport;

    public MainMenu(Rendezvous game) {
        this.game = game;

        background = new Texture("textures/gui/backgrounds/menu-background.png");
        logo = new Texture("textures/gui/logos/menu-logo.png");

        atlas = new TextureAtlas("textures/gui/buttons/buttons.pack");
        skin = new Skin(Gdx.files.internal("skins/button.json"), atlas);
        skin.getFont("default").getData();
        setupCamera();
        setupCursor();
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.left();

        TextButton playButton = new TextButton("PLAY", skin, "white-button");
        TextButton optionsButton = new TextButton("SETTINGS", skin, "white-button");
        TextButton helpButton = new TextButton("HELP", skin, "white-button");
        TextButton exitButton = new TextButton("QUIT", skin, "white-button");

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Game game = (Game)Gdx.app.getApplicationListener();
                game.setScreen(new Match((Rendezvous) game));
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(playButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth())).align(Align.left).padTop(100);
        mainTable.row();
        mainTable.add(optionsButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth())).align(Align.left).padTop(-20);
        mainTable.row();
        mainTable.add(helpButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth())).align(Align.left).padTop(-20);
        mainTable.row();
        mainTable.add(exitButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth())).align(Align.left).padTop(-20);

        stage.addActor(mainTable);
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    private void setupCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(ResourceHandler.getCursor(ResourceHandler.Cursor.MENU_CURSOR)));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        pixmap.dispose();
    }

    private void update(float delta) {
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().draw(logo,
                pCenter(GAME_WIDTH) - pCenter(logo.getWidth()),
                GAME_HEIGHT - 300);
        game.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}
