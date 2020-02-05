package com.jon.enemy.AI;
import com.jon.GameObjects.AIControlledShip;
import com.jon.enemy.HorizontalMovement;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.WINDOW_HEIGHT;

public class DivingEnemyAI implements AI {
    private int horizontalSpeed;
    private long lastDove;
    private boolean isDiving;
    private HorizontalMovement horizontalMovement;

    public DivingEnemyAI() {
        horizontalSpeed = 2;
        horizontalMovement = new HorizontalMovement();
        isDiving = true;
    }

    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        if (!isDiving) {
            horizontalMovement.update(ship, horizontalSpeed);
        } else {
            ship.setX(ship.getX());
        }

        ship.setY(ship.getY() + ship.getVelocity().y);
        if (ship.getY() < 100) {
            ship.resetSpeed();
            ship.moveUp();
        }
        if (ship.getY() >= WINDOW_HEIGHT - ENEMY_HEIGHT - 20) {
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

}
