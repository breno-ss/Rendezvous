package com.bressio.rendezvous.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bressio.rendezvous.events.InputTracker;
import com.bressio.rendezvous.graphics.AnimationRegion;
import com.bressio.rendezvous.scenes.Match;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.*;

public class Player extends Soldier {

    public Player(World world, Match match, float radius, float linearDamping, int speed, Vector2 position) {
        super(world, match, position.x, position.y, radius, linearDamping, speed, AnimationRegion.PLAYER, PLAYER_TAG,
                (short) (DEFAULT_TAG | BUILDING_TAG | LOOT_TAG), "player");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleKeyboardInput(delta);
        handleMouseInput(delta);
    }

    private void handleKeyboardInput(float delta) {

        float directionX = 0;
        float directionY = 0;

        if (InputTracker.isPressed(InputTracker.LEFT)){
            directionX -= getSpeed();
        }

        if(InputTracker.isPressed(InputTracker.RIGHT)){
            directionX += getSpeed();
        }

        if (InputTracker.isPressed(InputTracker.UP)){
            directionY += getSpeed();
        }

        if(InputTracker.isPressed(InputTracker.DOWN)){
            directionY -= getSpeed();
        }

        if (directionX != 0 || directionY != 0) {
            if (InputTracker.getButtonsPressed() == 1) {
                getBody().applyForce(new Vector2(directionX, directionY), getBody().getWorldCenter(), true);
            } else {
                float normalization = (float) (
                        (getSpeed() + Math.pow(InputTracker.getButtonsPressed(), 2)) /
                        (getSpeed() * InputTracker.getButtonsPressed()));
                getBody().applyForce(
                        new Vector2(directionX * normalization, directionY * normalization),
                        getBody().getWorldCenter(), true);
            }
        }
    }

    private void handleMouseInput(float delta) {
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(
                InputTracker.getMousePos().y - (getY() + pCenter(getHeight())),
                InputTracker.getMousePos().x - (getX() + pCenter(getWidth())));

        if(angle < 0){
            angle += 360;
        }
        setRotation(angle - 90);
    }

    public void takeDangerZoneDamage() {
        setHealth(-5);
    }
}
