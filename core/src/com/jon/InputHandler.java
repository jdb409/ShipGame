package com.jon;

import com.badlogic.gdx.InputProcessor;
import com.jon.GameObjects.Ship;

import static com.jon.Constants.WINDOW_HEIGHT;

public class InputHandler implements InputProcessor {
    private World world;
    private Ship ship;

    public InputHandler(World world) {
        this.world = world;
        ship = world.getShip();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 19) {
            ship.moveUp();
        }
        if (keycode == 20) {
            ship.moveDown();
        }
        if (keycode == 21) {
            ship.moveLeft();
        }
        if (keycode == 22) {
            ship.moveRight();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode >= 19 && keycode <= 22) {
            ship.resetSpeed();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ship.setCenter(screenX, WINDOW_HEIGHT - screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        ship.setCenter(screenX, WINDOW_HEIGHT - screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
