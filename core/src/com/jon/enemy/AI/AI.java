package com.jon.enemy.AI;

import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;

public interface AI {
    void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime);

    void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime);

    default void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
        updateBullets(ship, player, runTime);
    }

    default void die(AIControlledShip ship, float runTime) {
        ship.setBullets(new Array<>());
        ship.setDead(true);
        ship.setShowHealthBar(false);
    }
}
