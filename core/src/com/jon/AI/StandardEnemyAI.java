package com.jon.AI;

import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;


public class StandardEnemyAI implements AI {

    boolean moveRight = false;

    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
        updateBullets(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        float fullShipLength = ship.getX() + ship.getWidth();
        if (ship.getX() <= 0) {
            moveDown(ship);
            moveRight(ship);
            moveRight = true;
        } else if ((fullShipLength >= Constants.WINDOW_WIDTH)) {
            moveDown(ship);
            moveLeft(ship);
            moveRight = false;
        } else if (moveRight == true) {
            moveRight(ship);
        } else {
            moveLeft(ship);
        }

        if (ship.getY() <= 0) {
            ship.setY(650);
        }

    }

    public void moveDown(AIControlledShip ship) {
        ship.setY(ship.getY() - 75);
    }

    public void moveLeft(AIControlledShip ship) {
        ship.setX(ship.getX() - ship.getSpeed());
    }

    public void moveRight(AIControlledShip ship) {
        ship.setX(ship.getX() + ship.getSpeed());
    }

    @Override
    public void updateBullets(AIControlledShip ship) {

    }
}
