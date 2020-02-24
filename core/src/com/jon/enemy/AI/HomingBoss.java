package com.jon.enemy.AI;

import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;

import static com.jon.Constants.PLAYER_SHIP_START_Y;


public class HomingBoss implements AI {
    private float originalX;
    private float originalY;
    private boolean attackingPlayer;
    private long lastAttackEnd;
    private long startAttackTime;

    public HomingBoss() {
        lastAttackEnd = System.currentTimeMillis();
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        checkBounds(ship);
        checkAttack(ship, player);
        ship.setX(ship.getX() + (ship.getVelocity().x * 20));
        ship.setY(ship.getY() + (ship.getVelocity().y * 20));
    }

    private void checkAttack(AIControlledShip ship, PlayerControllerShip player) {
        //start attack. 2 second break
        if (!attackingPlayer && (System.currentTimeMillis() - lastAttackEnd > 1000)) {
            attackingPlayer = true;
            startAttackTime = System.currentTimeMillis();
            //get original coords to go back to
            originalX = ship.getX();
            originalY = ship.getY();
            ship.setVelocity(player.getX() - ship.getX(), ((player.getY() - ship.getY())));
        }

        //end attack, go back.  2 second attack
        else if (attackingPlayer && (System.currentTimeMillis() - startAttackTime > 1000)) {
            attackingPlayer = false;
            lastAttackEnd = System.currentTimeMillis();
            ship.setVelocity(originalX - ship.getX(), ((originalY - ship.getY())));
        }
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {
    }

    public void checkBounds(AIControlledShip ship) {
        checkX(ship);
        checkY(ship);
    }


    private void checkX(AIControlledShip ship) {
        if (ship.getX() < 0) {
            ship.resetSpeed();
        }

        if (ship.getX() > Constants.WINDOW_WIDTH - ship.getWidth()) {
            ship.resetSpeed();
        }
    }

    private void checkY(AIControlledShip ship) {
        float topLimit = Constants.WINDOW_HEIGHT - Constants.TOP_AD_BUFFER - ship.getHeight();
        if (ship.getY() < PLAYER_SHIP_START_Y) {
            ship.setY(PLAYER_SHIP_START_Y + 20);
            ship.resetSpeed();
        }
        if (ship.getY() > topLimit) {
            ship.setY(topLimit - 20);
            ship.resetSpeed();
        }
    }

}
