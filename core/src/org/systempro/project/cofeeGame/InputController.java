package org.systempro.project.cofeeGame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;


public class InputController implements InputProcessor {

    GameScreen game;
    float mouseX;
    float mouseY;
    public InputController(GameScreen game){
        this.game=game;
        float mouseX = 0;
        float mouseY = 0;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            game.player.keyUp = true;
            return true;
        }
        if(keycode == Input.Keys.S){
            game.player.keyDown = true;
            return true;
        }
        if(keycode == Input.Keys.A){
            game.player.keyLeft = true;
            return true;
        }
        if(keycode == Input.Keys.D){
            game.player.keyRight = true;
            return true;
        }

        if(keycode == Input.Keys.R){
            game.restartGame();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            game.player.keyUp = false;
            return true;
        }
        if(keycode == Input.Keys.S){
            game.player.keyDown = false;
            return true;
        }
        if(keycode == Input.Keys.A){
            game.player.keyLeft = false;
            float x = game.player.hitbox.getVelocity().x = 0;
            game.player.hitbox.setVelocity(new Vector2(x,game.player.hitbox.getVelocity().y));
            return true;
        }
        if(keycode == Input.Keys.D){
            game.player.keyRight = false;
            float x = game.player.hitbox.getVelocity().x = 0;
            game.player.hitbox.setVelocity(new Vector2(x,game.player.hitbox.getVelocity().y));
            return true;
        }
        if(keycode == Input.Buttons.LEFT){
            Vector2 direction = new Vector2(0,1);
            if(mouseX > 0.0){
                direction.x = 1.0F;
            }
            else{
                direction.x = -1.0f;
            }
            game.player.isShooting = true;
            return true;

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
