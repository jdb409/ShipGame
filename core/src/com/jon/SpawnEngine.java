package com.jon;

import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.GameObjects.items.Item;
import com.jon.GameObjects.items.ItemFactory;
import com.jon.enemy.EnemyFactory;
import com.jon.enums.EnemyType;
import com.jon.enums.ItemType;

import java.util.Random;

import static com.jon.Constants.BOSS_LEVEL_MODULO;
import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;
import static com.jon.Constants.TOP_ENEMY_BUFFER;
import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

public class SpawnEngine {
    private static Random rand = new Random();

    public static void spawnEnemies(Array<AIControlledShip> enemyShips, PlayerControllerShip player) {
        if (LevelConfig.getLevel() % BOSS_LEVEL_MODULO == 0) {
            handleBossLevelEnemySpawn(enemyShips, player);
        } else {
            handleNormalLevelEnemySpawn(enemyShips, player);
        }
    }

    public static void handleItemSpawn(AIControlledShip enemyShip, PlayerControllerShip playerControlledShip, Array<Item> items) {
        double shouldSpawnItem = Math.round(Math.random() * playerControlledShip.getChanceToSpawnItem());
        boolean ableToSpawn = shouldSpawnItem == 0;
        if (ableToSpawn) {
            int r = rand.nextInt(3);
            ItemType type = ItemType.from(r);
            Item item = ItemFactory.create(type, enemyShip.getX(), enemyShip.getY());
            items.add(item);
        }
    }

    private static void handleBossLevelEnemySpawn(Array<AIControlledShip> enemyShips, PlayerControllerShip player) {
        AIControlledShip boss = EnemyFactory.create(EnemyType.BOSS,
                player,
                WINDOW_WIDTH / 2,
                WINDOW_HEIGHT - TOP_ENEMY_BUFFER);
        enemyShips.add(boss);
    }

    private static void handleNormalLevelEnemySpawn(Array<AIControlledShip> enemyShips, PlayerControllerShip player) {
        int totalX = LevelConfig.numEnemyPerRow * (ENEMY_WIDTH + 40);
        int xLeft = WINDOW_WIDTH - totalX;
        for (int l = 1; l <= LevelConfig.numRows; l++) {
            for (int i = 0; i < LevelConfig.numEnemyPerRow; i++) {
                float enemyX = i * (ENEMY_WIDTH + 40) + xLeft;
                float enemyY = (WINDOW_HEIGHT - TOP_ENEMY_BUFFER) - (l * ENEMY_HEIGHT);
                double type = Math.floor(Math.random() * 9);
                AIControlledShip aiControlledShip;
                if (type >= 0 && type < 4) {
                    aiControlledShip = EnemyFactory.create(EnemyType.DIVING, player, enemyX, enemyY);
                } else if (type >= 4 && type < 8) {
                    aiControlledShip = EnemyFactory.create(EnemyType.STANDARD_SHOOTING, player, enemyX, enemyY);
                } else {
                    aiControlledShip = EnemyFactory.create(EnemyType.NON_ATTACKING, player, enemyX, enemyY);
                }
                enemyShips.add(aiControlledShip);
            }
        }
    }
}
