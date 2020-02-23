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
//                int aiChoice = (int) Math.floor(Math.random() * 2);
                int aiChoice = 1;
                AI bossAI;
                TextureRegion image;
                Animation<TextureRegion> deathAnim;
                switch (aiChoice) {
                    case (0):
                        bossAI = new PursuitShootingBossAI();
                        image = AssetLoader.whiteBossShip;
                        deathAnim = AssetLoader.whiteBossShipExplosion;
                    case (1):
                        bossAI = new PursuitShootingBossAI();
                        image = AssetLoader.greenBossShip;
                        deathAnim = AssetLoader.greenBossShipExplosion;
                    default:
                        bossAI = new BulletSurroundingBossAI();
                        image = AssetLoader.orangeBossShip;
                        deathAnim = AssetLoader.orangeBossShipExplosion;

                }
                return new AIControlledShip(x - 50,
                        y - 200,
                        100,
                        200,
                        SHOOTING_ENEMY_SPEED,
                        5,
                        bossAI,
                        image,
                        deathAnim,
                        true);
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
}
