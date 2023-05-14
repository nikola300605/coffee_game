package org.systempro.project.cofeeGame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.systempro.project.platformer.TestScreen;

public class InputController implements InputProcessor {

    GameScreen game;
    public InputController(GameScreen game){
        this.game=game;
    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            game.player.keyUp = true;
            System.out.println(game.player.hitbox.getVelocity());
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
            return true;
        }
        if(keycode == Input.Keys.D){
            game.player.keyRight = false;
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
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
