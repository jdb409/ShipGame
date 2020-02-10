package com.jon.enemy;

import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;
import com.jon.LevelConfig;

public class HorizontalMovement {
    boolean moveRight = false;
    int numFramesDir = 0;
    int framesToMove = 60;

    public void update(AIControlledShip ship, int speed){
        float fullShipLength = ship.getX() + ship.getWidth();
        if (moveRight) {
            moveRight(ship, speed);
            numFramesDir++;
            if (numFramesDir == framesToMove) {
                moveRight = false;
                numFramesDir = 0;
            }
        } else {
            moveLeft(ship, speed);
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

    private void moveLeft(AIControlledShip ship, int speed) {
        ship.setX(ship.getX() - LevelConfig.horizontalSpeed);
    }

    private void moveRight(AIControlledShip ship, int speed) {
        ship.setX(ship.getX() + LevelConfig.horizontalSpeed);
    }

}
