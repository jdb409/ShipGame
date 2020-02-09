package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;
import com.jon.enemy.HorizontalMovement;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.WINDOW_HEIGHT;

public class DivingEnemyAI implements AI {
    private int horizontalSpeed;
    private long lastDove;
    private boolean isDiving;
    private HorizontalMovement horizontalMovement;
    private float additionalDivingDepth;

    public DivingEnemyAI() {
        horizontalSpeed = 2;
        horizontalMovement = new HorizontalMovement();
        isDiving = true;
        additionalDivingDepth = 20;
    }

    @Override
    public void update(AIControlledShip ship, float runTime) {
        updatePosition(ship, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, float runTime) {
        if (!isDiving) {
            horizontalMovement.update(ship, horizontalSpeed);
        } else {
            ship.setX(ship.getX());
        }

        ship.setY(ship.getY() + ship.getVelocity().y);
        float maxDivingDepth = PLAYER_SHIP_HEIGHT + ship.getHeight() - additionalDivingDepth;
        if (ship.getY() < maxDivingDepth) {
            ship.resetSpeed();
            ship.moveUp();
        }
        if (ship.getY() >= WINDOW_HEIGHT - ENEMY_HEIGHT - 20) {
            ship.resetSpeed();
        }

        checkDive(ship);
    }


    @Override
    public void updateBullets(AIControlledShip ship, float runTime) {
        return;
    }

    @Override
    public void die(AIControlledShip ship, float runTime) {
        ship.setDead(true);
    }

    public void setAdditionalDivingDepth(float additionalDivingDepth){
        this.additionalDivingDepth = additionalDivingDepth;
    }

    private void dive(AIControlledShip ship) {
        if (!ship.isDead()) {
            lastDove = System.currentTimeMillis();
            ship.moveDown();
        }
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
