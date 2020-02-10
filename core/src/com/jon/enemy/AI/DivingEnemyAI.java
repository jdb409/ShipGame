package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;
import com.jon.LevelConfig;
import com.jon.enemy.HorizontalMovement;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.TOP_ENEMY_BUFFER;
import static com.jon.Constants.WINDOW_HEIGHT;

public class DivingEnemyAI implements AI {
    private int horizontalSpeed;
    private long lastDove;
    private boolean isDiving;
    private HorizontalMovement horizontalMovement;
    private float additionalDivingDepth;
    //the lower this number the more often the ship dives
    private float diveFrequency = 5000;

    public DivingEnemyAI() {
        horizontalSpeed = 2;
        horizontalMovement = new HorizontalMovement();
        additionalDivingDepth = 20;
        //delay first dive by 1 second
        lastDove = System.currentTimeMillis() - 4000;
    }

    @Override
    public void update(AIControlledShip ship, float runTime) {
        updatePosition(ship, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, float runTime) {
        if (!isDiving) {
            horizontalMovement.update(ship, horizontalSpeed);
        }
        ship.setY(ship.getY() + ship.getVelocity().y);
        checkY(ship);
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

    public void setAdditionalDivingDepth(float additionalDivingDepth) {
        this.additionalDivingDepth = additionalDivingDepth;
    }

    private void dive(AIControlledShip ship) {
        if (!ship.isDead()) {
            lastDove = System.currentTimeMillis();
            ship.moveDown();
        }
    }

    private void checkY(AIControlledShip ship) {
        float maxDivingDepth = PLAYER_SHIP_HEIGHT + ship.getHeight() - additionalDivingDepth;

        if (ship.getY() < maxDivingDepth) {
            ship.resetSpeed();
            ship.moveUp();
        }
        int topOfScreen = WINDOW_HEIGHT - ENEMY_HEIGHT - TOP_ENEMY_BUFFER;
        if (ship.getY() >= topOfScreen) {
            ship.resetSpeed();
        }
    }

    private void checkDive(AIControlledShip ship) {
        if ((System.currentTimeMillis() - lastDove) >
                diveFrequency * (1- LevelConfig.diveSpeedMultiplier)) {
            dive(ship);
            isDiving = true;
        }

        if (isDiving && (System.currentTimeMillis() - lastDove) > 2000) {
            isDiving = false;
        }
    }

}
