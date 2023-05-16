package org.systempro.project.cofeeGame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.systempro.project.physics2d.Collider;
import org.systempro.project.physics2d.PlazmaBody;

public class Player implements Collider {

    public PlazmaBody hitbox;
    public boolean keyUp,keyDown,keyLeft,keyRight,onGround;

    public Player(PlazmaBody hitbox){
        for(Fixture fixture:hitbox.body.getFixtureList()){
            fixture.setRestitution(0);
        }
        this.hitbox=hitbox;
        keyRight=false;
        keyLeft=false;
        keyUp = false;
        onGround=true;
        hitbox.fixtureBottom.setUserData(this);
        hitbox.sensorTop.setUserData(this);
        hitbox.fixtureCenter.setUserData(this);
        hitbox.sensorBottom.setUserData(this);
        hitbox.sensorRight.setUserData(this);
        hitbox.sensorLeft.setUserData(this);
    }

    public Player(World world, float x, float y, float width, float height){
        this(new PlazmaBody(world, x, y, width, height));
    }

    public void update(float delta){
        Vector2 speed = hitbox.getVelocity();
        if (keyLeft) speed.x = -4;
        if (keyRight) speed.x = 4;
        if(keyUp) speed.y = 4;
        if(onGround) speed.y = 8;
        if(keyDown) speed.y = -4;
        if(speed.y<0)speed.y-=9.81*delta;
        hitbox.setVelocity(speed);
    }
    public void draw(ShapeRenderer renderer){
        hitbox.debugDraw(renderer);
    }

    public void teleport(float x, float y, Vector2 speed){
        World world = hitbox.body.getWorld();
        hitbox.delete();
        hitbox = new PlazmaBody(world, x, y, 10,20);
        hitbox.setVelocity(speed);
        onGround = false;
        for(Fixture fixture:hitbox.body.getFixtureList()){
            fixture.setRestitution(0);
        }
        hitbox.fixtureBottom.setUserData(this);
        hitbox.sensorTop.setUserData(this);
        hitbox.fixtureCenter.setUserData(this);
        hitbox.sensorBottom.setUserData(this);
        hitbox.sensorRight.setUserData(this);
        hitbox.sensorLeft.setUserData(this);
    }
    @Override
    public void beginContact(Fixture fix1, Fixture fix2) {
        if(fix1 == hitbox.sensorBottom && fix2.getUserData() instanceof Platform){
            onGround = true;
            System.out.println("ground");
        }
        //if(fix1 == hitbox.sensorTop && fix2.getUserData() instanceof Platform){
          //  for(Fixture fixture:hitbox.body.getFixtureList()){
            //    fixture.setFriction(0);
            //}
        //}
    }

    @Override
    public void endContact(Fixture fix1, Fixture fix2) {
        if(fix1 == hitbox.sensorBottom && fix2.getUserData() instanceof Platform){
            onGround = false;
            System.out.println("air");
        }
       // if(fix1 == hitbox.sensorTop && fix2.getUserData() instanceof Platform){
         //   fix1.setFriction(0);
        //}
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        WorldManifold worldManifold = contact.getWorldManifold();
        Fixture f1=contact.getFixtureA();
        Fixture f2=contact.getFixtureB();
        System.out.println(f1.getUserData());
        System.out.println(f2 == hitbox.sensorTop);
        if(f2 == hitbox.sensorTop && f1.getUserData() instanceof Platform){
            for(int i = 0; i < 1000; i ++){
                contact.setEnabled(false);
            }
            System.out.println("contact");

        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
