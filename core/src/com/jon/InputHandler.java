package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.jon.GameObjects.PlayerControllerShip;

import static com.jon.Constants.WINDOW_HEIGHT;


public class InputHandler implements InputProcessor {
    private World world;
    private PlayerControllerShip ship;
    private float scaleFactorX;
    private float scaleFactorY;
    private int lastX;
    private int lastY;

    public InputHandler(World world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        ship = world.getPlayerControlledShip();
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        lastX = (int) ship.getX();
        lastY = (int) ship.getY();
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
        if (GameState.GAME_OVER.equals(World.gameState)) {
            world.restart();
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
        if (GameState.RUNNING.equals(World.gameState)) {
            float relY = ship.getY() - (WINDOW_HEIGHT - scaleY(screenY));
            float relX = ship.getX() - scaleX(screenX);
            if (relX > -60 && relX < 15
                    && relY > -120 && relY < 60) {
                ship.setCenter(scaleX(screenX), WINDOW_HEIGHT - scaleY(screenY));
            } else {
                useRelativeDirections(screenX, screenY);
            }
        }
        return false;
    }

    private void useRelativeDirections(int screenX, int screenY) {
        useRelativeX(screenX);
        useRelativeY(screenY);
    }

    private void useRelativeX(int screenX) {
        float speed = 600 * Gdx.graphics.getDeltaTime();
        if (Math.abs(screenX - lastX) > 5) {
            //move right
            if (screenX > lastX) {
                ship.setX(ship.getX() + speed);
            }
            //move left
            if (screenX < lastX) {
                ship.setX(ship.getX() - speed);
            }
        } else {
            ship.resetSpeed();
        }
        lastX = screenX;
    }

    private void useRelativeY(int screenY) {
        float speed = 700 * Gdx.graphics.getDeltaTime();

        if (Math.abs(screenY - lastY) > 5) {
            //move down
            if (screenY > lastY) {
                ship.setY(ship.getY() - speed);
            }
            //move up
            if (screenY < lastY) {
                ship.setY(ship.getY() + speed);
            }
        } else {
            ship.resetSpeed();
        }
        lastY = screenY;
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
