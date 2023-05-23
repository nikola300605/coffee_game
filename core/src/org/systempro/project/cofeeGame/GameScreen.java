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

    private int lastPlatformIndex = 0;

    public int randRange(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void generatePlatform(float y){
        int platformCount = 300;
        int width = 30;
        int height =5;
        Vector2 position = new Vector2(0,y);
        for(int i = 0; i < platformCount; i++){
            position.y += randRange(2f,8f);
            position.x = randRange((float) -Gdx.graphics.getWidth() /4, Gdx.graphics.getWidth());
            Platform platform = new Platform(world,position.x,position.y,width,height);
            platforms.add(platform);
            lastPlatformIndex++;
        }


    }

    public void restartGame(){
        for(Platform platform : platforms){
            platform.hitbox.delete();
        }
        platforms.clear();
        lastPlatformIndex = 0;
        Platform beginingPlatform = new Platform(world,-5,-5, 10, 20);
        platforms.add(beginingPlatform);
        generatePlatform(0);
        player.teleport(5,5, new Vector2(0,0));
        player.onGround = true;

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

        platforms.add(new Platform(world, -5, -5, 100, 50));


        screenHeight = Gdx.graphics.getHeight();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        world.step(delta,10,10);
        player.update(delta);

        //update camera
        float y = player.hitbox.getPosition().y;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        if(y > camera2d.getPosition().y){
            camera2d.setPosition(0,y);
        }

        camera2d.update();


        shapeRenderer.setProjectionMatrix(camera2d.combined4);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(shapeRenderer);


        if(Math.abs(player.hitbox.getPosition().x) >= (float) Gdx.graphics.getWidth() /4){
            System.out.println(player.hitbox.body.getPosition().x);
            float xp = player.hitbox.getPosition().x;
            float yp = player.hitbox.getPosition().y;
            Vector2 speed = player.hitbox.getVelocity();
            player.teleport(-xp, yp,speed);

        }

        if(player.hitbox.getPosition().y < camera2d.getPosition().y - (float) screenHeight /4){
            restartGame();
        }

        if(platforms.size() <= 1){
            generatePlatform(0);
        }
        else if(player.hitbox.getPosition().y > (platforms.get(lastPlatformIndex).hitbox.getPosition().y - Gdx.graphics.getHeight())){
            generatePlatform(platforms.get(lastPlatformIndex).hitbox.getPosition().y - 8);
        }

        Gdx.input.setInputProcessor(new InputController(this));

        //System.out.println(player.hitbox.getPosition().y);
        for(Platform platform : platforms){
            platform.hitbox.debugDraw(shapeRenderer);
        }
        shapeRenderer.end();
    }
}
