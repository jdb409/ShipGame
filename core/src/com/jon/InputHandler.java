package com.jon;

import com.badlogic.gdx.InputProcessor;
import com.jon.GameObjects.Ship;

import static com.jon.Constants.WINDOW_HEIGHT;

public class InputHandler implements InputProcessor {
    private World world;
    private Ship ship;
    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(World world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        ship = world.getShip();
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
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
        System.out.format(" before scale -- %d : %d\n",screenX, screenY);
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        System.out.format(" after scale -- %d : %d\n",screenX, screenY);
        ship.setCenter(screenX, WINDOW_HEIGHT - screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
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

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }
}
