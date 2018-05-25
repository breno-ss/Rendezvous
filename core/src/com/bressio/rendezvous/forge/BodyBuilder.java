package com.bressio.rendezvous.forge;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyBuilder{

    private World world;
    private Vector2 position;

    private float width;
    private float height;
    private float linearDamping;
    private float radius;
    private boolean fixedRotation;
    private BodyDef.BodyType bodyType;

    public BodyBuilder(World world, Vector2 position){
        this.world = world;
        this.position = position;
    }

    public BodyBuilder withWidth(float width) {
        this.width = width;
        return this;
    }

    public BodyBuilder withHeight(float height) {
        this.height = height;
        return this;
    }

    public BodyBuilder withLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public BodyBuilder withRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public BodyBuilder withFixedRotation() {
        this.fixedRotation = true;
        return this;
    }

    public BodyBuilder withBodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
        return this;
    }

    public Body build(){
        Body body;
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();


        bodyDef.linearDamping = linearDamping;
        bodyDef.fixedRotation = fixedRotation;
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        body = world.createBody(bodyDef);

        if (width != 0 && height != 0) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width, height);
            fixtureDef.shape = shape;
        } else if (radius != 0) {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);
            fixtureDef.shape = shape;
        }

        body.createFixture(fixtureDef);
        return body;
    }

}