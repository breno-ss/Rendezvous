package com.bressio.rendezvous.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import com.bressio.rendezvous.languages.Internationalization;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pCenter;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public class MainMenu implements Screen {

    // game
    private Rendezvous game;
    private ResourceHandler resources;
    private Internationalization i18n;

    // GUI
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Texture logo;

    // rendering
    private OrthographicCamera camera;
    private Viewport viewport;

    public MainMenu(Rendezvous game) {
        this.game = game;
        loadResources();
        setupCamera();
        setupCursor();
        forgeMenu();
    }

    private void loadResources() {
        resources = new ResourceHandler();
        resources.loadMainMenuResources();
        background = resources.getTexture(ResourceHandler.TexturePath.MENU_BACKGROUND);
        logo = resources.getTexture(ResourceHandler.TexturePath.MENU_LOGO);
        skin = resources.getSkin(ResourceHandler.SkinPath.BUTTON_SKIN);
        skin.getFont("default").getData();
        i18n = new Internationalization();
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    private void setupCursor() {
        Pixmap pixmap = resources.getPixmap(ResourceHandler.PixmapPath.MENU_CURSOR);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
        pixmap.dispose();
    }

    private void forgeMenu() {
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left();
        table.setFillParent(true);

        TextButton playButton = new TextButton(i18n.getBundle().get("play"), skin, "white-button");
        TextButton optionsButton = new TextButton(i18n.getBundle().get("settings"), skin, "white-button");
        TextButton helpButton = new TextButton(i18n.getBundle().get("help"), skin, "white-button");
        TextButton exitButton = new TextButton(i18n.getBundle().get("quit"), skin, "white-button");

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Rendezvous game = (Rendezvous)Gdx.app.getApplicationListener();
                game.setScreen(new Match(game));
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(playButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth()) + 20).align(Align.left).padTop(100);
        table.row();
        table.add(optionsButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth()) + 20).align(Align.left).padTop(-20);
        table.row();
        table.add(helpButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth()) + 20).align(Align.left).padTop(-20);
        table.row();
        table.add(exitButton).padLeft(pCenter(GAME_WIDTH) - pCenter(logo.getWidth()) + 20).align(Align.left).padTop(-20);

        stage.addActor(table);
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
        resources.dispose();
    }
}
