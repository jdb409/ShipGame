package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;

public interface AI {
    void update(AIControlledShip ship, float runTime);
    void updatePosition(AIControlledShip ship, float runTime);
    void updateBullets(AIControlledShip ship, float runTime);
    void die(AIControlledShip ship, float runTime);
}
