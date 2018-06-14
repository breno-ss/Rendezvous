package com.bressio.rendezvous.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_HEIGHT;
import static com.bressio.rendezvous.scheme.PlayerSettings.GAME_WIDTH;

public final class InputTracker extends InputAdapter {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ESC = 4;
    public static final int M = 5;
    public static final int E = 6;
    private static final int MAX = 7;

    private static boolean[] keyPresses;

    private static Vector2 mousePosition;
    private final Vector3 mousePosition3D;
    private final OrthographicCamera camera;

    public InputTracker(OrthographicCamera camera) {
        keyPresses = new boolean[InputTracker.MAX];
        mousePosition = new Vector2();
        mousePosition3D = new Vector3();
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                keyPresses[LEFT] = true;
                break;
            case Input.Keys.D:
                keyPresses[RIGHT] = true;
                break;
            case Input.Keys.W:
                keyPresses[UP] = true;
                break;
            case Input.Keys.S:
                keyPresses[DOWN] = true;
                break;
            case Input.Keys.ESCAPE:
                keyPresses[ESC] = true;
                break;
            case Input.Keys.M:
                keyPresses[M] = true;
                break;
            case Input.Keys.E:
                keyPresses[E] = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                keyPresses[LEFT] = false;
                break;
            case Input.Keys.D:
                keyPresses[RIGHT] = false;
                break;
            case Input.Keys.W:
                keyPresses[UP] = false;
                break;
            case Input.Keys.S:
                keyPresses[DOWN] = false;
                break;
            case Input.Keys.ESCAPE:
                keyPresses[ESC] = false;
                break;
            case Input.Keys.M:
                keyPresses[M] = false;
                break;
            case Input.Keys.E:
                keyPresses[E] = false;
                break;
        }
        return true;
    }

    public static boolean isPressed(int key) {
        return keyPresses[key];
    }

    public static int getButtonsPressed() {
        int count = 0;
        for (int i = 0; i < MAX; i++) {
            if (keyPresses[i]) {
                count++;
            }
        }
        return count;
    }

    public int getRelativeX() {
        return (Gdx.input.getX() < GAME_WIDTH / 2 ?
                -((GAME_WIDTH / 2) - Gdx.input.getX()) : Gdx.input.getX() - (GAME_WIDTH / 2));
    }

    public int getRelativeY() {
        return (Gdx.input.getY() < GAME_HEIGHT / 2 ?
                -((GAME_HEIGHT / 2) - Gdx.input.getY()) : Gdx.input.getY() - (GAME_HEIGHT / 2));
    }

    public static Vector2 getMousePos() {
        return mousePosition;
    }

    public void update() {
        mousePosition3D.x = Gdx.input.getX();
        mousePosition3D.y = Gdx.input.getY();
        mousePosition3D.z = 0;
        camera.unproject(mousePosition3D);
        mousePosition.x = mousePosition3D.x;
        mousePosition.y = mousePosition3D.y;
    }

    public void resetSecondaryKeys() {
        for (int i = 4; i < MAX; i++) {
            keyPresses[i] = false;
        }
    }

    public void resetAllKeys() {
        for (int i = 0; i < MAX; i++) {
            keyPresses[i] = false;
        }
    }
}
