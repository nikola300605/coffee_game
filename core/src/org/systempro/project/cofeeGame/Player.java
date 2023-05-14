package org.systempro.project.cofeeGame;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
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
        keyDown=false;
        keyRight=false;
        keyUp=false;
        keyLeft=false;
        onGround=true;
        hitbox.fixtureBottom.setUserData(this);
        hitbox.fixtureTop.setUserData(this);
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
        if(keyDown) speed.y = -4;
        if(speed.y<0)speed.y-=9.81*delta;
        hitbox.setVelocity(speed);
    }
    public void draw(ShapeRenderer renderer){
        hitbox.debugDraw(renderer);
    }
    @Override
    public void beginContact(Fixture fix1, Fixture fix2) {

    }

    @Override
    public void endContact(Fixture fix1, Fixture fix2) {

    }
}
