package com.jon.enemy;

import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;
import com.jon.LevelConfig;

public class HorizontalMovement {
    boolean moveRight = false;
    int numFramesDir = 0;
    int framesToMove = 60;

    public void update(AIControlledShip ship) {
        float fullShipLength = ship.getX() + ship.getWidth();
        if (moveRight) {
            moveRight(ship);
            numFramesDir++;
            if (numFramesDir == framesToMove) {
                moveRight = false;
                numFramesDir = 0;
            }
        } else {
            moveLeft(ship);
            numFramesDir++;
            if (numFramesDir == framesToMove) {
                moveRight = true;
                numFramesDir = 0;
            }
        }
        if (ship.getX() <= 0) {
            moveRight = true;
            numFramesDir = 0;
        }

        if (fullShipLength >= Constants.WINDOW_WIDTH) {
            moveRight = false;
            numFramesDir = 0;
        }
    }

    protected void moveLeft(AIControlledShip ship) {
        ship.setX(ship.getX() - LevelConfig.horizontalSpeed);
    }

    protected void moveRight(AIControlledShip ship) {
        ship.setX(ship.getX() + LevelConfig.horizontalSpeed);
    }

}
