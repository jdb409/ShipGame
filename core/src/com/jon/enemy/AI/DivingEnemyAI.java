package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.LevelConfig;
import com.jon.enemy.HorizontalMovement;

import java.util.Random;

import static com.jon.Constants.PLAYER_SHIP_HEIGHT;

public class DivingEnemyAI implements AI {
    private long lastDove;
    private boolean isDiving;
    private HorizontalMovement horizontalMovement;
    private float additionalDivingDepth;
    //the lower this number the more often the ship dives
    private float diveFrequency = 5000;
    private float nextDive;
    private Random r;
    public DivingEnemyAI() {
        r = new Random();
        horizontalMovement = new HorizontalMovement();
        additionalDivingDepth = 20;
        //delay first dive by 1 second
        lastDove = System.currentTimeMillis() - LevelConfig.divingFrequencyMin;
        nextDive = this.getNextDive();
    }

    @Override
    public void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        if (!isDiving) {
            horizontalMovement.update(ship);
        }
        ship.setY(ship.getY() + ship.getVelocity().y);
        checkY(ship);
        checkDive(ship);
    }


    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {
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

    private void checkDive(AIControlledShip ship) {
        if ((System.currentTimeMillis() - lastDove) > this.nextDive) {
            dive(ship);
            isDiving = true;
        }

        if (isDiving && (System.currentTimeMillis() - lastDove) > 2000) {
            isDiving = false;
        }
    }


    private float getNextDive() {
        return r.nextInt(LevelConfig.divingFrequencyMax - LevelConfig.divingFrequencyMin)
                + LevelConfig.divingFrequencyMin;
    }

    private void checkY(AIControlledShip ship) {
        float lessDepth = (float) Math.floor(Math.random() * 5) + 3;
        float maxDivingDepth = (PLAYER_SHIP_HEIGHT + ship.getHeight() + lessDepth) - additionalDivingDepth;

        if (ship.getY() < maxDivingDepth) {
            ship.resetSpeed();
            ship.moveUp();
        }
        if (ship.getY() >= ship.getOriginalY()) {
            ship.resetSpeed();
        }
    }

}
