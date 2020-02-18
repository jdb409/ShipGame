package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;

public interface AI {
    void update(AIControlledShip ship, PlayerControllerShip player, float runTime);
    void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime);
    void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime);
    void die(AIControlledShip ship, float runTime);
}
