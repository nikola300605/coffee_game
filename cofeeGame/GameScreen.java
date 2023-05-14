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

    public int screenHeight;


    public void generateLevel(){
        int width = 10;
        int height = 10;
        int sign = 0;
        double rand = Math.random();
        if(rand > 0.5){
            sign = 1;
        }
        else{
            sign = 0;
        }
        float x = (float) (Math.random() * Gdx.graphics.getWidth()) / 2;
        if(sign == 0){
            x = -x;
        }
        float y = 0;
        float playerY = player.hitbox.body.getPosition().y;
        Platform platform = new Platform(world, x, (float) Math.random() * Gdx.graphics.getHeight(), width, height);
        platforms.add(platform);
    }

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
        platforms = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            generateLevel();
        }
        platforms.add(new Platform(world, -5, -5, 100, 50));

        screenHeight = Gdx.graphics.getHeight();

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

        if(player.hitbox.body.getPosition().y >= screenHeight){}

        Gdx.input.setInputProcessor(new InputController(this));

        shapeRenderer.setProjectionMatrix(camera2d.combined4);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(shapeRenderer);

        if((Math.abs(player.hitbox.body.getPosition().x)) >= Gdx.graphics.getWidth() / 2){
            player.teleport(-player.hitbox.body.getPosition().x, player.hitbox.body.getPosition().y);
        }

        for(Platform platform : platforms){
            platform.hitbox.debugDraw(shapeRenderer);
        }
        shapeRenderer.end();
    }
}
