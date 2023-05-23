package org.systempro.project.cofeeGame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.systempro.project.physics2d.CircleBody;
import org.systempro.project.physics2d.Collider;
import org.systempro.project.physics2d.RectBody;


public class Bullet implements Collider {
    public CircleBody hitbox;

    private double _speed = 2.0;
    private boolean passThrough = false;

    public Bullet(CircleBody hitbox){
        for(Fixture fixture:hitbox.body.getFixtureList()){
            fixture.setRestitution(0);
        }
        this.hitbox=hitbox;
        for(Fixture fixture: hitbox.body.getFixtureList()){
            fixture.setUserData(this);
        }
    }

    public Bullet(World world, float x, float y, float r){
        this(new CircleBody(world, x, y, r));
    }

    public void update(Vector2 direction){
        Vector2 speed = hitbox.getVelocity();
        if(direction.x < 0){
            speed.x -=  _speed;
        }
        else{
            speed.x += _speed;
        }
        speed.y += _speed;
        hitbox.setVelocity(speed);
    }

    public void draw(ShapeRenderer shapeRenderer){
        hitbox.debugDraw(shapeRenderer);
    }

    public void delete(){
        hitbox.delete();
    }

    @Override
    public void beginContact(Fixture fix1, Fixture fix2) {
        if(fix1.getUserData() instanceof Bullet && fix2.isSensor()){
            hitbox.delete();
        }
    }

    @Override
    public void endContact(Fixture fix1, Fixture fix2) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture f1=contact.getFixtureA();
        Fixture f2=contact.getFixtureB();
        if(passThrough) contact.setEnabled(false);
        if(f2.getUserData() instanceof Bullet && !f1.isSensor()){
            contact.setEnabled(false);
            passThrough = true;
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        contact.setEnabled(true);
        passThrough = false;
    }
}
