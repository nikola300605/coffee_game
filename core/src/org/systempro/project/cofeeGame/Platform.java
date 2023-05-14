package org.systempro.project.cofeeGame;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.systempro.project.physics2d.RectBody;

public class Platform {
    public RectBody hitbox;

    public Platform(RectBody hitbox){
        this.hitbox=hitbox;
        hitbox.getFixture().setRestitution(0);
        hitbox.setType(BodyDef.BodyType.StaticBody);
        hitbox.getFixture().setUserData(this);
    }

    public Platform(World world, float x, float y, float width, float height){
        this(new RectBody(world, x, y, width, height));
    }
}
