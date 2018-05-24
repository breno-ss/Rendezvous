package com.bressio.rendezvous.helpers;

import com.badlogic.gdx.math.Vector2;

public class PhysicsManager {

    public static final Vector2 GRAVITY = new Vector2(0, 0);
    private static final float SCALE = 100;

    public static float pScale(float number) {
        return number / SCALE;
    }

    public static Vector2 pScale(float x, float y) {
        return new Vector2(x / SCALE, y / SCALE);
    }

    public static float pScaleCenter(float number) {
        return number / 2 / SCALE;
    }

    public static Vector2 pScaleCenter(float x, float y) {
        return new Vector2(x / 2 / SCALE, y / 2 / SCALE);
    }

    public static float pCenter(float number) {
        return number / 2;
    }
}
