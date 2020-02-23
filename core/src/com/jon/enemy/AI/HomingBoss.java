package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;


public class HomingBoss implements AI {

    public HomingBoss() {
    }


    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        ship.setVelocity(player.getX() - ship.getX(), ((player.getY() - ship.getY())));
        ship.setX(ship.getX() + (ship.getVelocity().x * .5f));
        ship.setY(ship.getY() + (ship.getVelocity().y * .5f));
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {
    }

}
