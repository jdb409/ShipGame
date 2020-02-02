package com.jon.enemy;

import com.jon.AssetLoader;
import com.jon.enemy.AI.AI;
import com.jon.enemy.AI.DivingEnemyAI;
import com.jon.enemy.AI.StandardShootingEnemyAI;
import com.jon.GameObjects.AIControlledShip;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;

public class EnemyFactory {
    private static float SHOOTING_ENEMY_SPEED = 2;
    private static float DIVING_ENEMY_SPEED = 10;

    public static AIControlledShip create(EnemyType type, float x) {
        switch (type) {
            case DIVING:
                AI divingAI = new DivingEnemyAI();
                return new AIControlledShip(x,
                        WINDOW_HEIGHT - ENEMY_HEIGHT - 20,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        DIVING_ENEMY_SPEED,
                        divingAI,
                        AssetLoader.redShip);
            case STANDARD_SHOOTING:
                AI shootingAI = new StandardShootingEnemyAI();
                return new AIControlledShip(x,
                        WINDOW_HEIGHT - ENEMY_HEIGHT - 20,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        SHOOTING_ENEMY_SPEED,
                        shootingAI,
                        AssetLoader.fishShip);
            default:
                System.out.println("should not reach here");
                return null;
        }
    }
}
