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
    private int level = 1;
    private PlayerControllerShip playerControlledShip;
    private long lastDropTime;
    private long lastHeroBullet;
    private int shipsDestroyed;
    Array<AIControlledShip> enemyShips;
    private AlienInvaderGame game;
    private Long spawnEnemyWaitingTime = 0L;
    private float runTime;

    public World(AlienInvaderGame game) {
        init(game);
    }

    public void update(float delta) {
        runTime += delta;
        handleEnemySpawn();
        if (GameState.RUNNING.equals(gameState)) {
            playerControlledShip.update(runTime);
            handleCollision();
        } else if (GameState.GAME_OVER.equals(gameState)) {
            playerControlledShip.update(runTime);
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
                handlePlayerEnemyCollision(enemyShip, enemyIterator);
                handlePlayerBulletEnemyCollision(enemyShip, enemyIterator);
            }
        }
    }

    private void handlePlayerBulletEnemyCollision(AIControlledShip enemyShip, Iterator<AIControlledShip> enemyIterator) {
        Array<Bullet> playerBullets = playerControlledShip.getBullets();
        Iterator<Bullet> playerBulletIterator = playerBullets.iterator();
        while (playerBulletIterator.hasNext()) {
            Bullet bullet = playerBulletIterator.next();
            if (enemyShip.getRectangle().overlaps(bullet.getRectangle())) {
                playerBulletIterator.remove();
                handleEnemyCollision(enemyShip, enemyIterator);
            }
        }
    }

    private void handlePlayerEnemyCollision(AIControlledShip enemyShip, Iterator<AIControlledShip> enemyIterator) {
        if (playerControlledShip.getRectangle().overlaps(enemyShip.getRectangle())) {
            handleEnemyCollision(enemyShip, enemyIterator);
            handlePlayerCollision();
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

    private void handlePlayerCollision() {
        if (System.currentTimeMillis() - playerControlledShip.getLastHit() < 500) return;
        playerControlledShip.setHit(true);
        playerControlledShip.decrementHealth(1);
        playerControlledShip.setLastHit(System.currentTimeMillis());
        playerControlledShip.setAnimationStart(System.currentTimeMillis());
        playerControlledShip.setWidth(playerControlledShip.getWidth() * 2f);
        if (playerControlledShip.getHealth() <= 0) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void handleEnemyCollision(AIControlledShip enemyShip, Iterator<AIControlledShip> enemyIterator) {
        if (System.currentTimeMillis() - enemyShip.getLastHit() < 500) return;
        enemyShip.decrementHealth(1);
        enemyShip.setLastHit(System.currentTimeMillis());
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
        playerControlledShip = new PlayerControllerShip(heroStart, 0, PLAYER_SHIP_WIDTH, PLAYER_SHIP_HEIGHT);
        enemyShips = new Array<>();
        spawnEnemies();
    }

    private void handleEnemySpawn() {
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
            for (int i = 0; i < 6; i++) {
                float enemyX = i * (ENEMY_WIDTH + 40);
                float enemyY = (WINDOW_HEIGHT - 20) - (l * ENEMY_HEIGHT);
                Random rand = new Random();
                int type = rand.nextInt(10);
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

    public void dispose() {

    }
}
