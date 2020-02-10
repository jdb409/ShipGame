package com.jon;

import com.badlogic.gdx.InputProcessor;
import com.jon.GameObjects.PlayerControllerShip;

import static com.jon.Constants.PLAYER_SHIP_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;


public class InputHandler implements InputProcessor {
    private World world;
    private PlayerControllerShip ship;
    private float scaleFactorX;
    private float scaleFactorY;
    private int lastScreenX;
    private int lastScreenY;

    public InputHandler(World world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        ship = world.getPlayerControlledShip();
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
        System.out.println(scaleX(screenX) + ":" + scaleY(screenY));
        if (GameState.GAME_OVER.equals(World.gameState)) {
            world.restart();
            lastScreenX =  (int) (Constants.WINDOW_WIDTH / 2 - PLAYER_SHIP_WIDTH / 2);
            lastScreenY = 0;
        } else if (GameState.RUNNING.equals(World.gameState)){
            lastScreenX = scaleX(screenX);
            lastScreenY = scaleY(screenY);
        } else {
            lastScreenX =  (int) (Constants.WINDOW_WIDTH / 2 - PLAYER_SHIP_WIDTH / 2);
            lastScreenY = 0;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ship.resetSpeed();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (GameState.RUNNING.equals(World.gameState)) {
            float relY = ship.getY() - (WINDOW_HEIGHT - screenY);
            float relX = ship.getX() - screenX;
            if (relX > -60 && relX < 15
                    && relY > -120 && relY < 60) {
                ship.setCenter(screenX, WINDOW_HEIGHT - screenY);
            } else {
                ship.setX(ship.getX() + (screenX - lastScreenX));
                ship.setY(ship.getY() - (screenY - lastScreenY));
            }
        }
        lastScreenX = screenX;
        lastScreenY = screenY;
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
