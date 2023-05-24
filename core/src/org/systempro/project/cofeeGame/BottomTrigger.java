package org.systempro.project.cofeeGame;

import com.badlogic.gdx.physics.box2d.*;
import org.systempro.project.physics2d.Collider;
import org.systempro.project.physics2d.RectBody;

public class BottomTrigger implements Collider {

    public RectBody hitbox;

    public BottomTrigger(RectBody hitbox){
        this.hitbox=hitbox;
        hitbox.getFixture().setRestitution(0);
        hitbox.getFixture().setSensor(true);
        hitbox.setType(BodyDef.BodyType.StaticBody);
        hitbox.getFixture().setUserData(this);
    }

    public BottomTrigger(World world, float x, float y, float width, float height){
        this(new RectBody(world, x, y, width, height));
    }
    @Override
    public void beginContact(Fixture fix1, Fixture fix2) {

    }

    @Override
    public void endContact(Fixture fix1, Fixture fix2) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
