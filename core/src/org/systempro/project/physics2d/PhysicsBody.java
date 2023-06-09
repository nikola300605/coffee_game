package org.systempro.project.physics2d;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class PhysicsBody {
    public static float SCALE=20;
    public Body body;

    public Vector2 getPosition(){
        Vector2 v = new Vector2(body.getPosition());
        v.x*=SCALE;
        v.y*=SCALE;
        return v;
    }
    public Vector2 getVelocity(){
        return new Vector2(body.getLinearVelocity());
    }
    public PhysicsBody setVelocity(Vector2 velocity){
        body.setLinearVelocity(velocity);
        return this;
    }
    public float getAngle(){
        return body.getAngle();
    }
    public PhysicsBody setType(BodyDef.BodyType type){
        body.setType(type);
        return this;
    }
    public void delete() {
        body.getWorld().destroyBody(body);
    }
    public abstract void debugDraw(ShapeRenderer renderer);
}
