package org.systempro.project.physics2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public interface Collider {
    void beginContact(Fixture fix1,Fixture fix2);
    void endContact(Fixture fix1,Fixture fix2);
    void preSolve(Contact contact, Manifold oldManifold);

    void postSolve(Contact contact, ContactImpulse impulse);
}
