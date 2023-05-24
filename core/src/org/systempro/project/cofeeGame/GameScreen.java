package org.systempro.project.cofeeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import org.systempro.project.BasicScreen;
import org.systempro.project.camera.Camera2d;
import org.systempro.project.physics2d.CollisionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends BasicScreen {

    public Camera2d camera2d;
    public ShapeRenderer shapeRenderer;
    public World world;
    public ArrayList<Platform> platforms;
    public Player player;
    public BottomTrigger bottomTrigger;
    public float screenHeight;
    public float screenWidth;

    public Texture spriteSheet;
    public Texture doodlerLeft;
    public Texture doodlerRight;
    public Texture doodleLeftJump;
    public Texture doodleRightJump;
    public Sprite doodleSprite;
    public TextureRegion[][] regions;
    public Sprite platform;
    public SpriteBatch batch;

    public Texture background;
    public Sprite backgroundSprite;

    public ArrayList<Sprite> platformSprites;

    public Map<Platform, Sprite> platformSpriteHashMap;

    private int scale = 5;

    private int lastPlatformIndex = 0;

    public int randRange(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void generatePlatform(float y){
        int platformCount = 100;
        int width = 30;
        int height =5;
        Vector2 position = new Vector2(0,y);
        for(int i = 0; i < platformCount; i++){
            position.y += randRange(20f,30f);
            position.x = randRange((float) -100f, 100f);
            System.out.println(position.x);
            System.out.println(Math.abs(position.x));
            System.out.println("\n \n \n");
            while(Math.abs(position.x) - Math.abs(platforms.get(lastPlatformIndex).hitbox.getPosition().x) >= 50){
                position.x = randRange((float) -100f, 100f);
            }
            Platform platform = new Platform(world,position.x,position.y,width,height);
            platforms.add(platform);
            Sprite platformSprite = new Sprite(regions[0][0]);
            platformSprite.setScale(0.5f);
            platformSprite.setPosition(position.x - platformSprite.getWidth()/2, position.y - platformSprite.getHeight()/2);
            platformSpriteHashMap.put(platform,platformSprite);
            platformSprites.add(platformSprite);
            lastPlatformIndex++;
        }


    }

    public void restartGame(){
        camera2d.setPosition(5,5 - screenHeight/4);
        for(Platform platform : platforms){
            platform.hitbox.delete();
        }
        for(Sprite platformSprite : platformSprites){
            platformSprite.getTexture().dispose();
        }
        platformSprites.clear();
        platforms.clear();
        spriteSheet = new Texture("coffeeGame/game-tiles.png");
        regions = TextureRegion.split(spriteSheet,64,16);
        lastPlatformIndex = 0;
        Platform beginingPlatform = new Platform(world,-5,-5, 100, 50);
        platforms.add(beginingPlatform);
        Sprite beginingSprite = new Sprite(regions[0][0]);
        beginingSprite.setScale(0.5f);
        beginingSprite.setPosition(-5 - beginingSprite.getWidth()/2, -5 - beginingSprite.getHeight()/2);
        platformSprites.add(beginingSprite);
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

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        shapeRenderer = new ShapeRenderer();

        player = new Player(world,5, 5, 10, 20);
        bottomTrigger = new BottomTrigger(world,-10f,-10f,screenWidth/4, 10f);
        platforms = new ArrayList<>();

        platforms.add(new Platform(world, -5, -5, 100, 50));

        batch = new SpriteBatch();
        spriteSheet = new Texture("coffeeGame/game-tiles.png");
        doodlerLeft = new Texture("coffeeGame/Doodler_left.png");
        doodlerRight = new Texture("coffeeGame/Doodler_right.png");
        doodleLeftJump = new Texture("coffeeGame/Doodler_left_jump.png");
        doodleRightJump = new Texture("coffeeGame/Doodler_right_jump.png");
        background = new Texture("coffeeGame/bck.png");
        regions = TextureRegion.split(spriteSheet,64,16);
        //platform = new Sprite(regions[0][0]);
        //platform.setPosition(10,10);
        backgroundSprite = new Sprite(background);
        backgroundSprite.setScale(2f);
        doodleSprite = new Sprite(doodlerLeft);
        doodleSprite.setPosition(300,300);
        doodleSprite.setOrigin(0f,0f);

        //platform.setScale(0.5f);
        doodleSprite.setScale(0.5f);

        platformSprites = new ArrayList<>();
        platformSpriteHashMap = new HashMap<>();
        Sprite beginningPlatform = new Sprite(regions[0][0]);
        beginningPlatform.setScale(0.5f);
        beginningPlatform.setPosition(-5 - beginningPlatform.getWidth()/2, -5 - beginningPlatform.getHeight()/2);
        platformSprites.add(beginningPlatform);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        world.step(delta,10,10);
        player.update(delta);

        //update camera
        float y = player.hitbox.getPosition().y;
        float x = player.hitbox.getPosition().x;
        float width = player.hitbox.width;
        float height = player.hitbox.height;
        if(y > camera2d.getPosition().y){
            camera2d.setPosition(0,y);
        }
        camera2d.update();

        if(!player.onGround){
            if(player.goingLeft){
                doodleSprite = new Sprite(doodlerLeft);
                doodleSprite.setScale(0.5f);
                doodleSprite.setOrigin(0f,0f);
            }
            else if(player.goingRight){
                doodleSprite = new Sprite(doodlerRight);
                doodleSprite.setScale(0.5f);
                doodleSprite.setOrigin(0f,0f);
            }
        }
        else{
            if(player.goingLeft){
                doodleSprite = new Sprite(doodleLeftJump);
                doodleSprite.setScale(0.5f);
                doodleSprite.setOrigin(0f,0f);
            }
            else if(player.goingRight){
                doodleSprite = new Sprite(doodleRightJump);
                doodleSprite.setScale(0.5f);
                doodleSprite.setOrigin(0f,0f);
            }
        }
        backgroundSprite.setPosition(camera2d.getPosition().x - backgroundSprite.getWidth()/2, camera2d.getPosition().y - backgroundSprite.getHeight()/2);

        doodleSprite.setPosition(x - doodleSprite.getWidth()/4,y - doodleSprite.getHeight()/4);

        shapeRenderer.setProjectionMatrix(camera2d.combined4);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //player.draw(shapeRenderer);


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
            //platform.hitbox.debugDraw(shapeRenderer);
        }
        shapeRenderer.end();

        batch.begin();
        batch.setProjectionMatrix(camera2d.combined4);
        backgroundSprite.draw(batch);
        for(Sprite platformSprite : platformSprites){
            platformSprite.draw(batch);
        }
        doodleSprite.draw(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        doodlerRight.dispose();
        doodlerLeft.dispose();
        doodleRightJump.dispose();
        doodleLeftJump.dispose();
    }
}
