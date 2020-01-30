package com.jon;

import com.badlogic.gdx.utils.Array;
import com.jon.AI.AI;
import com.jon.AI.StandardEnemyAI;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.screens.MainMenuScreen;

import java.util.Iterator;

import lombok.Data;

import static com.jon.Constants.ENEMY_HEIGHT;
import static com.jon.Constants.ENEMY_WIDTH;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;

@Data
public class World {
    public GameState gameState = GameState.READY;
    private PlayerControllerShip playerControlledShip;
    private long lastDropTime;
    private long lastHeroBullet;
    private int shipsDestroyed;
    Array<AIControlledShip> enemyShips;
    private AlienInvaderGame game;

    public World(AlienInvaderGame game) {
        init(game);
    }

    public void update() {
        if (enemyShips.size == 0) {
            spawnEnemies();
        }

        if (GameState.RUNNING.equals(gameState)) {
            playerControlledShip.update();
            Array<Bullet> playerBullets = playerControlledShip.getBullets();
            handleCollision(playerBullets, enemyShips, playerControlledShip);
        } else if (GameState.GAME_OVER.equals(gameState)) {
            handleGameOver();
        }

    }

    public void restart() {
        System.out.println("restarting");
        game.setScreen(new MainMenuScreen(game, game.camera));
    }

    private void handleCollision(Array<Bullet> playerBullets, Array<AIControlledShip> enemyShips, PlayerControllerShip ship) {
        Iterator<AIControlledShip> enemyIterator = enemyShips.iterator();
        while (enemyIterator.hasNext()) {
            AIControlledShip enemyShip = enemyIterator.next();
            enemyShip.update();

            Iterator<Bullet> enemyBulletIterator = enemyShip.getBullets().iterator();
            while (enemyBulletIterator.hasNext()) {
                Bullet bullet = enemyBulletIterator.next();
                if (bullet.getRectangle().overlaps(ship.getRectangle())) {
                    enemyBulletIterator.remove();
                    gameState = GameState.GAME_OVER;
                }
            }

            Iterator<Bullet> playerBulletIterator = playerBullets.iterator();
            if (ship.getRectangle().overlaps(enemyShip.getRectangle())) {
                gameState = GameState.GAME_OVER;
            }

            while (playerBulletIterator.hasNext()) {
                Bullet bullet = playerBulletIterator.next();
                if (enemyShip.getRectangle().overlaps(bullet.getRectangle())) {
                    enemyIterator.remove();
                    playerBulletIterator.remove();
                    shipsDestroyed++;
                }
            }
        }
    }

    private void handleGameOver() {
        playerControlledShip.setSpeed(0);
        for (AIControlledShip enemyShip : enemyShips) {
            enemyShip.setSpeed(0);
        }
    }


//    private void reset() {
//        playerControlledShip = null;
//        enemyShips = null;
//        init();
//    }

    private void init(AlienInvaderGame game) {
        float heroStart = Constants.WINDOW_WIDTH / 2 - PLAYER_SHIP_WIDTH / 2;
        this.game = game;
        playerControlledShip = new PlayerControllerShip(heroStart, 0, PLAYER_SHIP_WIDTH, PLAYER_SHIP_HEIGHT);
        enemyShips = new Array<>();
        spawnEnemies();
    }

    private void spawnEnemies() {
        for (int i = 0; i < 3; i++) {
            AI enemyAI = new StandardEnemyAI();
            float x = i * (ENEMY_WIDTH +70);
            System.out.println(x);
            AIControlledShip aiControlledShip = new AIControlledShip(x, WINDOW_HEIGHT - ENEMY_HEIGHT - 20, ENEMY_WIDTH, ENEMY_HEIGHT, 4, enemyAI);
            enemyShips.add(aiControlledShip);
        }

    }

    public void dispose() {

    }
}
