package com.jon;

import com.badlogic.gdx.InputProcessor;
import com.jon.GameObjects.Ship;


public class InputHandler implements InputProcessor {
    private World world;
    private Ship ship;
    private float scaleFactorX;
    private float scaleFactorY;
    private int lastX;
    private int lastY;

    public InputHandler(World world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        ship = world.getShip();
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ship.resetSpeed();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        //only want the direction
        // need to take into consideration how far finger moved when determing distance
//
        if (screenX == lastX) {
            lastX = screenX;
            lastY = screenY;
            ship.resetSpeedX();
            return false;
        }

        if (screenY == lastY) {
            lastX = screenX;
            lastY = screenY;
            ship.resetSpeedY();
            return false;
        }

        if (Math.abs(screenX - lastX) > 2) {
            if (screenX > lastX) {
                ship.moveRight();
            }

            if (screenX < lastX) {
                ship.moveLeft();
            }
            if (Math.abs(screenY - lastY) > 10) {
                if (screenY > lastY) {
                    ship.moveDown();
                }

                if (screenY < lastY) {
                    ship.moveUp();
                }
            }
            lastX = screenX;
            lastY = screenY;
            return false;
        }

        if (Math.abs(screenY - lastY) > 2) {
            if (screenY > lastY) {
                ship.moveDown();

            }

            if (screenY < lastY) {
                ship.moveUp();

            }
            if (Math.abs(screenX - lastX) > 10) {
                if (screenX > lastX) {
                    ship.moveRight();

                }

                if (screenX < lastX) {
                    ship.moveLeft();

                }
            }
            lastX = screenX;
            lastY = screenY;
            return false;

        }
        lastX = screenX;
        lastY = screenY;
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
