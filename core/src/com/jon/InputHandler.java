package com.jon;

import com.badlogic.gdx.InputProcessor;
import com.jon.GameObjects.PlayerControllerShip;


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
        if (GameState.GAME_OVER.equals(world.getGameState())) {
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
        System.out.println("screen: " + scaleX(screenX));
        System.out.println("x: " + ship.getX());
        if (GameState.RUNNING.equals(world.getGameState())) {
            useRelativeY(screenY);

            if (Math.abs(scaleX(screenX) - ship.getX()) > 20) {
                useRelativeX(screenX);
            } else {
                ship.setX(scaleX(screenX));
            }
        }
        return false;
    }

    private void useRelativeDirections(int screenX, int screenY) {
        useRelativeX(screenX);
        useRelativeY(screenY);
    }

    private void useRelativeX(int screenX) {
        if (Math.abs(screenX - lastX) > 10) {
            if (screenX > lastX) {
                ship.moveRight();
            }
            if (screenX < lastX) {
                ship.moveLeft();
            }
        } else {
            ship.resetSpeedX();
        }
        lastX = screenX;
    }

    private void useRelativeY(int screenY) {
        if (Math.abs(screenY - lastY) > 10) {
            if (screenY > lastY) {
                ship.moveDown();
            }
            if (screenY < lastY) {
                ship.moveUp();
            }
        } else {
            ship.resetSpeedY();
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
