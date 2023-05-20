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

    public Bullet bullet;

    public int screenHeight;

    private float prevY = 0;

    private float prevX = 0;


    public void generatePlatform(){
        int width = 30;
        int height =5;
        int sign;
        double rand = Math.random();
        if(rand > 0.5){
            sign = 1;
        }
        else{
            sign = 0;
        }
        float x = (float) (Math.random() * Gdx.graphics.getWidth()) / 4;
        if(sign == 0){
            x = -x + (float) width /2;
        }
        else {
            x = x - (float) width/2;
        }
        float playerY = player.hitbox.body.getPosition().y;
        float playerX = player.hitbox.body.getPosition().x;
        if(prevY == 0){
            prevY = playerY;
        }
        if(prevX == 0) prevX = playerX;

        float y = (float) (Math.random() * Gdx.graphics.getHeight()/4);
        float dist = (float) Math.sqrt(Math.pow((x - playerX),2) + Math.pow((y - prevY),2));
        int i = 0;
        while (y > (prevY + 24)){
            y = y - 8;
            if(sign == 0){
                x = x + 8;
            }
            else{
                x = x - 8;
            }

            dist = (float) Math.sqrt(Math.pow((x - prevX),2) + Math.pow((y - prevY),2));
            System.out.println((prevY + 8) < y);
            System.out.println(prevY);
            System.out.println(y);
            i++;

            System.out.println("   End while    "  + i);
        }
        //System.out.println(dist);
        //System.out.println(y);
        Platform platform = new Platform(world, x , y, width, height);
        prevY = y;
        prevX = x;
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
            generatePlatform();
        }
        platforms.add(new Platform(world, -5, -5, 100, 50));
        bullet = null;

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
        camera2d.setPosition(0,y);
        camera2d.update();

        Gdx.input.setInputProcessor(new InputController(this));

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

        for(Platform platform : platforms){
            platform.hitbox.debugDraw(shapeRenderer);
        }
        shapeRenderer.end();
    }
}
