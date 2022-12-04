package org.systempro.project.platformer;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface Collider {
    void beginContact(Fixture fix1,Fixture fix2);
    void endContact(Fixture fix1,Fixture fix2);
}
