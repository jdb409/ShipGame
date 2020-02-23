package com.jon.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.enemy.AI.AI;
import com.jon.enemy.AI.BulletSurroundingBossAI;
import com.jon.enemy.AI.DivingEnemyAI;
import com.jon.enemy.AI.PursuitShootingBossAI;
import com.jon.enemy.AI.NonAttackingEnemyAI;
import com.jon.enemy.AI.StandardShootingEnemyAI;
import com.jon.enums.EnemyType;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;

public class EnemyFactory {
    private static float SHOOTING_ENEMY_SPEED = 2;
    private static float DIVING_ENEMY_SPEED = 10;

    public static AIControlledShip create(EnemyType type, float x, float y) {
        switch (type) {
            case DIVING:
                AI divingAI = new DivingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        DIVING_ENEMY_SPEED,
                        2,
                        divingAI,
                        AssetLoader.fishShip,
                        AssetLoader.yellowShipExplosion,
                        false);
            case STANDARD_SHOOTING:
                AI shootingAI = new StandardShootingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        SHOOTING_ENEMY_SPEED,
                        2,
                        shootingAI,
                        AssetLoader.redShip,
                        AssetLoader.redShipExplosion,
                        false);
            case BOSS:
                return getBoss(x, y);
            case NON_ATTACKING:
                AI nonAttackingAI = new NonAttackingEnemyAI();
                return new AIControlledShip(x,
                        y,
                        ENEMY_WIDTH,
                        ENEMY_HEIGHT,
                        SHOOTING_ENEMY_SPEED,
                        2,
                        nonAttackingAI,
                        AssetLoader.redShip,
                        AssetLoader.redShipExplosion,
                        false);
            default:
                System.out.println("Enemy Factory - should not reach here");
                return null;
        }
    }

    private static AIControlledShip getBoss(float x, float y) {
        int aiChoice = (int) Math.floor(Math.random() * 2);
        switch (aiChoice) {
            case (0):
                return new AIControlledShip(x - 50,
                        y - 200,
                        100,
                        200,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new PursuitShootingBossAI(),
                        AssetLoader.whiteBossShip,
                        AssetLoader.whiteBossShipExplosion,
                        true);
            case (1):
                return new AIControlledShip(x - 50,
                        y - 300,
                        75,
                        150,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new BulletSurroundingBossAI(),
                        AssetLoader.orangeBossShip,
                        AssetLoader.orangeBossShipExplosion,
                        true);
            default:
                System.out.println("should not return here");
                return new AIControlledShip(x - 50,
                        y - 200,
                        100,
                        200,
                        SHOOTING_ENEMY_SPEED,
                        20,
                        new PursuitShootingBossAI(),
                        AssetLoader.whiteBossShip,
                        AssetLoader.whiteBossShipExplosion,
                        true);

        }
    }
}
