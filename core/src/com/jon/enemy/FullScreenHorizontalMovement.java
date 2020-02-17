package com.jon.enemy;

import com.jon.GameObjects.AIControlledShip;

public class FullScreenHorizontalMovement extends HorizontalMovement {
    public FullScreenHorizontalMovement(int framesToMove) {
        super();
        this.framesToMove = framesToMove;
    }

    @Override
    protected void moveLeft(AIControlledShip ship, int speed) {
        ship.setX(ship.getX() - speed);
    }

    @Override
    protected void moveRight(AIControlledShip ship, int speed) {
        ship.setX(ship.getX() + speed);
    }
}
