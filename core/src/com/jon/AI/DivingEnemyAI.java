package com.jon.AI;

import com.badlogic.gdx.utils.TimeUtils;
import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;

import java.sql.Time;

public class DivingEnemyAI implements AI {
    boolean moveRight = false;
    int numFramesDir = 0;
    int framesToMove = 60;

    boolean lastX;
    private long lastDove;
    public boolean isDiving = true;

    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        if (!isDiving) {
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
        } else {
            ship.setX(ship.getX());
        }
        ship.setY(ship.getY() + ship.getVelocity().y);
        if (ship.getY() < 100) {
            ship.resetSpeed();
            ship.moveUp();
        }
        if (ship.getY() > 680) {
            ship.resetSpeed();
        }

        checkDive(ship);
    }


    @Override
    public void updateBullets(AIControlledShip ship) {
        return;
    }

    private void dive(AIControlledShip ship) {
        lastDove = System.currentTimeMillis();
        ship.moveDown();
    }

    private void checkDive(AIControlledShip ship) {
        if ((System.currentTimeMillis() - lastDove) > 5000) {
            dive(ship);
            isDiving = true;
        }

        if (isDiving && (System.currentTimeMillis() - lastDove) > 2000) {
            isDiving = false;
        }
    }

    public void moveLeft(AIControlledShip ship) {
        ship.setX(ship.getX() - 2);
    }

    public void moveRight(AIControlledShip ship) {
        ship.setX(ship.getX() + 2);
    }

}
