package org.systempro.project.cofeeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.physics2d.CollisionListener;

import java.util.ArrayList;

public class GameScreen extends BasicScreen {

    public Camera2d camera2d;
    public ShapeRenderer shapeRenderer;
    public World world;
    public ArrayList<Platform> platforms;
    public Player player;

    @Override
    public void show() {

        world=new World(new Vector2(0,-10),false);
        world.setContactListener(new CollisionListener());

        camera2d = new Camera2d();
        camera2d.setPosition(0,0);
        camera2d.setScale(0.5f,0.5f);
        camera2d.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera2d.update();

        shapeRenderer = new ShapeRenderer();

        player = new Player(world,5, 5, 10, 20);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        world.step(delta,10,10);
        player.update(delta);

        //update camera
        float x = player.hitbox.getPosition().x;
        float y = player.hitbox.getPosition().y;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera2d.setPosition(0,y);
        camera2d.update();

        Gdx.input.setInputProcessor(new InputController(this));

        shapeRenderer.setProjectionMatrix(camera2d.combined4);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(shapeRenderer);
        shapeRenderer.end();
    }
}
