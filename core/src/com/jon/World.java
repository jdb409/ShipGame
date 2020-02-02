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

import static com.jon.Constants.ENEMY_WIDTH;
import static com.jon.Constants.HANDLE_COLLISION;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_WIDTH;

@Data
public class World {
    public GameState gameState = GameState.READY;
    private PlayerControllerShip playerControlledShip;
    private long lastDropTime;
    private long lastHeroBullet;
    private int shipsDestroyed;
    Array<AIControlledShip> enemyShips;
    private AlienInvaderGame game;
    private Long spawnEnemyWaitingTime = null;

    public World(AlienInvaderGame game) {
        init(game);
    }

    public void update() {
        handleEnemySpawn();
        if (GameState.RUNNING.equals(gameState)) {
            playerControlledShip.update();
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
            enemyShip.update();

            if (HANDLE_COLLISION) {
                handleEnemyBulletCollision(enemyShip);
                handlePlayerEnemyCollision(enemyShip);
                handlePlayerBulletCollision(enemyShip, enemyIterator);
            }
        }
    }

    private void handlePlayerBulletCollision(AIControlledShip enemyShip, Iterator<AIControlledShip> enemyIterator) {
        Array<Bullet> playerBullets = playerControlledShip.getBullets();
        Iterator<Bullet> playerBulletIterator = playerBullets.iterator();

        while (playerBulletIterator.hasNext()) {
            Bullet bullet = playerBulletIterator.next();
            if (enemyShip.getRectangle().overlaps(bullet.getRectangle())) {
                enemyIterator.remove();
                playerBulletIterator.remove();
                shipsDestroyed++;
            }
        }
    }

    private void handleEnemyBulletCollision(AIControlledShip enemyShip) {
        Iterator<Bullet> enemyBulletIterator = enemyShip.getBullets().iterator();
        while (enemyBulletIterator.hasNext()) {
            Bullet bullet = enemyBulletIterator.next();
            if (bullet.getRectangle().overlaps(playerControlledShip.getRectangle())) {
                enemyBulletIterator.remove();
                gameState = GameState.GAME_OVER;
            }
        }
    }

    private void handlePlayerEnemyCollision(AIControlledShip enemyShip) {
        if (playerControlledShip.getRectangle().overlaps(enemyShip.getRectangle())) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void handleGameOver() {
        playerControlledShip.setSpeed(0);
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
        if (enemyShips.size == 0 && spawnEnemyWaitingTime == null) {
            spawnEnemyWaitingTime = System.currentTimeMillis();
        }

        if (spawnEnemyWaitingTime != null && System.currentTimeMillis() - spawnEnemyWaitingTime >= 1000) {
            spawnEnemies();
            spawnEnemyWaitingTime = null;
        }

    }

    private void spawnEnemies() {
        for (int i = 0; i < 6; i++) {
            float x = i * (ENEMY_WIDTH + 40);
            Random rand = new Random();
            int type = rand.nextInt(10);
            AIControlledShip aiControlledShip;
            if (type % 2 == 0) {
                aiControlledShip = EnemyFactory.create(EnemyType.DIVING, x);
            } else {
                aiControlledShip = EnemyFactory.create(EnemyType.STANDARD_SHOOTING, x);
            }
            enemyShips.add(aiControlledShip);
        }

    }

    public void dispose() {

    }
}
