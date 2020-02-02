package com.jon.enemy.AI;

import com.jon.GameObjects.AIControlledShip;

public interface AI {
    void update(AIControlledShip ship);
    void updatePosition(AIControlledShip ship);
    void updateBullets(AIControlledShip ship);
}
