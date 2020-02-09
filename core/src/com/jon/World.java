package com.jon;

import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.enemy.EnemyFactory;
import com.jon.enemy.EnemyType;
import com.jon.screens.MainMenuScreen;

import java.util.Iterator;
import java.util.Random;

import lombok.Data;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;
import static com.jon.Constants.HANDLE_COLLISION;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;

@Data
public class World {
    public static GameState gameState = GameState.READY;
    private final int NUM_ENEMY_PER_ROW = 6;
    private int level = 1;
    private PlayerControllerShip playerControlledShip;
    private Array<AIControlledShip> enemyShips;

    private long lastDropTime;
    private long lastHeroBullet;
    private int shipsDestroyed;
    private AlienInvaderGame game;
    private Long spawnEnemyWaitingTime = 0L;
    private float runTime;

    public World(AlienInvaderGame game) {
        init(game);
    }

    public void update(float delta) {
        runTime += delta;
        handleEnemySpawn();
        playerControlledShip.update(runTime);

        if (GameState.RUNNING.equals(gameState)) {
            handleCollision();
        } else if (GameState.GAME_OVER.equals(gameState)) {
            handleGameOver();
        }
    }

    public void restart() {
        game.setScreen(new MainMenuScreen(game, game.camera));
    }

    private void handleCollision() {
        Iterator<AIControlledShip> enemyIterator = enemyShips.iterator();
        while (enemyIterator.hasNext()) {
            AIControlledShip enemyShip = enemyIterator.next();
            enemyShip.update(runTime);
            if (enemyShip.isRemoveFromScreen()) {
                enemyIterator.remove();
            }
            if (HANDLE_COLLISION) {
                handleEnemyBulletPlayerCollision(enemyShip);
                handlePlayerEnemyCollision(enemyShip);
                handlePlayerBulletEnemyCollision(enemyShip);
            }
        }
    }

    private void handlePlayerBulletEnemyCollision(AIControlledShip enemyShip) {
        Array<Bullet> playerBullets = playerControlledShip.getBullets();
        Iterator<Bullet> playerBulletIterator = playerBullets.iterator();
        while (playerBulletIterator.hasNext()) {
            Bullet bullet = playerBulletIterator.next();
            if (enemyShip.getRectangle().overlaps(bullet.getRectangle())) {
                playerBulletIterator.remove();
                handleEnemyCollision(enemyShip);
            }
        }
    }

    private void handleEnemyBulletPlayerCollision(AIControlledShip enemyShip) {
        Iterator<Bullet> enemyBulletIterator = enemyShip.getBullets().iterator();
        while (enemyBulletIterator.hasNext()) {
            Bullet bullet = enemyBulletIterator.next();
            if (bullet.getRectangle().overlaps(playerControlledShip.getRectangle())) {
                enemyBulletIterator.remove();
                handlePlayerCollision();
            }
        }
    }

    private void handlePlayerEnemyCollision(AIControlledShip enemyShip) {
        if (playerControlledShip.getRectangle().overlaps(enemyShip.getRectangle())) {
            handleEnemyCollision(enemyShip);
            handlePlayerCollision();
        }
    }

    private void handlePlayerCollision() {
        // if player has been hit, give 500ms window of invincibility
        playerControlledShip.handleCollision(1);
        if (playerControlledShip.getHealth() <= 0) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void handleEnemyCollision(AIControlledShip enemyShip) {
        enemyShip.handleCollision();
        if (enemyShip.getHealth() <= 0) {
            if (!enemyShip.isDead()) {
                enemyShip.die();
                shipsDestroyed++;
            }
        }
    }

    private void handleGameOver() {
        playerControlledShip.setSpeed(0);
        playerControlledShip.setBullets(new Array<Bullet>());
        for (AIControlledShip enemyShip : enemyShips) {
            enemyShip.setSpeed(0);
        }
    }

    private void init(AlienInvaderGame game) {
        this.game = game;
        float heroStart = Constants.WINDOW_WIDTH / 2 - PLAYER_SHIP_WIDTH / 2;
        playerControlledShip = new PlayerControllerShip(heroStart, 30, PLAYER_SHIP_WIDTH, PLAYER_SHIP_HEIGHT, AssetLoader.blueShipExplosion, AssetLoader.blueShipHitAnim);
        enemyShips = new Array<>();
        spawnEnemies();
    }

    private void handleEnemySpawn() {
        //if no enemies are left, start timer
        if (enemyShips.size == 0 && spawnEnemyWaitingTime == 0L) {
            level++;
            spawnEnemyWaitingTime = System.currentTimeMillis();
            gameState = GameState.READY;
        }

        if (spawnEnemyWaitingTime != 0L && System.currentTimeMillis() - spawnEnemyWaitingTime >= 2000) {
            gameState = GameState.RUNNING;
            setRunning();
            spawnEnemies();
            spawnEnemyWaitingTime = 0L;
        }

    }

    private void setRunning() {
        gameState = GameState.RUNNING;
        playerControlledShip.resetOrigin();
    }

    private void spawnEnemies() {
        for (int l = 1; l <= level; l++) {
            for (int i = 0; i < NUM_ENEMY_PER_ROW; i++) {
                float enemyX = i * (ENEMY_WIDTH + 40);
                float enemyY = (WINDOW_HEIGHT - 20) - (l * ENEMY_HEIGHT);
                Random rand = new Random();
                int type = rand.nextInt(2);
                AIControlledShip aiControlledShip;
                if (type % 2 == 0) {
                    aiControlledShip = EnemyFactory.create(EnemyType.DIVING, enemyX, enemyY);
                } else {
                    aiControlledShip = EnemyFactory.create(EnemyType.STANDARD_SHOOTING, enemyX, enemyY);
                }
                enemyShips.add(aiControlledShip);
            }
        }

    }

}
