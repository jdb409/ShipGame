package com.jon.enemy;

import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.LevelConfig;
import com.jon.enemy.AI.AI;
import com.jon.enemy.AI.BulletSurroundingBossAI;
import com.jon.enemy.AI.DivingEnemyAI;
import com.jon.enemy.AI.HomingBoss;
import com.jon.enemy.AI.PursuitShootingBossAI;
import com.jon.enemy.AI.NonAttackingEnemyAI;
import com.jon.enemy.AI.StandardShootingEnemyAI;
import com.jon.enums.EnemyType;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;

public class EnemyFactory {
    private static float SHOOTING_ENEMY_SPEED = 2;
    private static float DIVING_ENEMY_SPEED = 10;

    private EnemyFactory() {
    }

    public static AIControlledShip create(EnemyType type,
                                          PlayerControllerShip player,
                                          float x,
                                          float y) {
        switch (type) {
            case DIVING:
                AI divingAI = new DivingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        DIVING_ENEMY_SPEED,
                        LevelConfig.normalEnemyHealth,
                        divingAI,
                        AssetLoader.getInstance().getFishShip(),
                        AssetLoader.getInstance().getYellowShipExplosion(),
                        false,
                        player);
            case STANDARD_SHOOTING:
                AI shootingAI = new StandardShootingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        SHOOTING_ENEMY_SPEED,
                        LevelConfig.normalEnemyHealth,
                        shootingAI,
                        AssetLoader.getInstance().getRedShip(),
                        AssetLoader.getInstance().getRedShipExplosion(),
                        false,
                        player);
            case BOSS:
                return getBoss(x, y, player);
            case NON_ATTACKING:
                AI nonAttackingAI = new NonAttackingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        SHOOTING_ENEMY_SPEED,
                        LevelConfig.normalEnemyHealth,
                        nonAttackingAI,
                        AssetLoader.getInstance().getRedShip(),
                        AssetLoader.getInstance().getRedShipExplosion(),
                        false,
                        player);
            default:
                System.out.println("Enemy Factory - should not reach here");
                return null;
        }
    }

    private static AIControlledShip getBoss(float x, float y, PlayerControllerShip player) {
        int aiChoice = (int) Math.floor(Math.random() * 3);
        switch (aiChoice) {
            case (0):
                return new AIControlledShip(x - 50,
                        y - 200,
                        100,
                        200,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new PursuitShootingBossAI(),
                        AssetLoader.getInstance().getWhiteBossShip(),
                        AssetLoader.getInstance().getWhiteBossShipExplosion(),
                        true,
                        player);
            case (1):
                return new AIControlledShip(x - 50,
                        y - 300,
                        75,
                        150,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new BulletSurroundingBossAI(),
                        AssetLoader.getInstance().getOrangeBossShip(),
                        AssetLoader.getInstance().getOrangeBossShipExplosion(),
                        true,
                        player);
            case (2):
                return new AIControlledShip(x - 50,
                        y - 200,
                        75,
                        150,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new HomingBoss(),
                        AssetLoader.getInstance().getGreenBossShip(),
                        AssetLoader.getInstance().getGreenBossShipExplosion(),
                        true,
                        player);
            default:
                System.out.println("should not reach here");
                return new AIControlledShip(x - 50,
                        y - 200,
                        100,
                        200,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new PursuitShootingBossAI(),
                        AssetLoader.getInstance().getWhiteBossShip(),
                        AssetLoader.getInstance().getWhiteBossShipExplosion(),
                        true,
                        player);
        }
    }
}
