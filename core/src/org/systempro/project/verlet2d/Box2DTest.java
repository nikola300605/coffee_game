package org.systempro.project.verlet2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.physics2d.CircleBody;
import org.systempro.project.physics2d.RectBody;

import java.util.ArrayList;

public class Box2DTest extends BasicScreen {
    World world;
    ArrayList<CircleBody> particles=new ArrayList<>();
    ShapeRenderer renderer=new ShapeRenderer();
    @Override
    public void show() {
        world=new World(new Vector2(0,-15),false);
        new RectBody(world,400,610,800,10).setType(BodyDef.BodyType.StaticBody);
        new RectBody(world,400,-10,800,10).setType(BodyDef.BodyType.StaticBody);
        new RectBody(world,-10,300,10,600).setType(BodyDef.BodyType.StaticBody);
        new RectBody(world,810,300,10,600).setType(BodyDef.BodyType.StaticBody);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            float x=Gdx.input.getX();
            float y=Gdx.graphics.getHeight()-Gdx.input.getY();
            CircleBody body=new CircleBody(world,x,y,10);
            body.body.getFixtureList().first().setRestitution(0.9f);
            body.body.getFixtureList().first().setFriction(0.0f);
            body.body.setLinearDamping(0);
            particles.add(body);
        }

        world.step(delta,8,8);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for(CircleBody body:particles){
            body.debugDraw(renderer);
        }
        renderer.end();
    }
}
