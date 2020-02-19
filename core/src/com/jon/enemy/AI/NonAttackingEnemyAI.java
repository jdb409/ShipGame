package com.jon.enemy.AI;

import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.enemy.HorizontalMovement;

public class NonAttackingEnemyAI implements AI {
    private HorizontalMovement horizontalMovement;

    public NonAttackingEnemyAI() {
        this.horizontalMovement = new HorizontalMovement();
    }

    @Override
    public void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        horizontalMovement.update(ship);
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {

    }

    @Override
    public void die(AIControlledShip ship, float runTime) {
        ship.setBullets(new Array<>());
        ship.setDead(true);
    }
}
