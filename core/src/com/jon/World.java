package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.GameObjects.items.Item;
import com.jon.enums.GameState;
import com.jon.enums.ScoreEvent;
import com.jon.screens.MainMenuScreen;

import java.util.Random;

import lombok.Data;

import static com.jon.Constants.HIGHEST_LEVEL;
import static com.jon.Constants.HIGH_SCORE;
import static com.jon.Constants.LAST_LEVEL;
import static com.jon.Constants.PLAYER_SHIP_HEIGHT;
import static com.jon.Constants.PLAYER_SHIP_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;

@Data
public class World {
    public static GameState gameState = GameState.READY;
    private PlayerControllerShip playerControlledShip;
    private Array<AIControlledShip> enemyShips;
    private Array<Item> items;

    private long lastDropTime;
    private long lastHeroBullet;
    private int shipsDestroyed;
    private float runTime;
    private long spawnEnemyWaitingTime;
    private Random rand;

    private AlienInvaderGame game;
    private CollisionEngine collisionEngine;

    public World(AlienInvaderGame game) {
        rand = new Random();
        LevelConfig.restart();
        init(game);
        collisionEngine = new CollisionEngine(playerControlledShip, enemyShips, items);

    }

    public void restart() {
        game.setScreen(new MainMenuScreen(game, game.camera));
    }

    public void update(float delta) {
        runTime += delta;
        checkComplete();
        startLevel();
        switch (gameState) {
            case RUNNING:
                collisionEngine.handleShipCollision();
                collisionEngine.handleItemCollision();
                playerControlledShip.update(runTime);
                return;
            case GAME_OVER:
                handleGameOver();
                return;
            case COMPLETE:
                handleComplete();
                return;
        }
    }

    private void init(AlienInvaderGame game) {
        this.game = game;
        float heroStart = Constants.WINDOW_WIDTH / 2 - PLAYER_SHIP_WIDTH / 2;
        playerControlledShip = new PlayerControllerShip(heroStart,
                25,
                PLAYER_SHIP_WIDTH,
                PLAYER_SHIP_HEIGHT,
                AssetLoader.getInstance().getBlueShipExplosion(),
                AssetLoader.getInstance().getBlueShipHitAnim());
        enemyShips = new Array<>();
        items = new Array<>();
        SpawnEngine.spawnEnemies(enemyShips, playerControlledShip);
    }


    private void checkComplete() {
        //Check if last level was complete.  Defeating boss will set stage at 2
        if (LevelConfig.getLevel() == LAST_LEVEL
                && LevelConfig.getStage() == 2) {
            gameState = GameState.COMPLETE;
        }
    }

    private void startLevel() {
        //if no enemies are left, start timer
        if (gameState == GameState.RUNNING || gameState == GameState.READY) {
            if (enemyShips.size == 0 && spawnEnemyWaitingTime == 0L) {
                setNextWave();
            }

            if (spawnEnemyWaitingTime != 0L && System.currentTimeMillis() - spawnEnemyWaitingTime >= 2000) {
                startNextWave();
            }
        }
    }

    private void setNextWave() {
        LevelConfig.modifyScore(ScoreEvent.WAVE_PASSED);
        LevelConfig.setNextStage();
        spawnEnemyWaitingTime = System.currentTimeMillis();
        gameState = GameState.READY;
    }

    private void startNextWave() {
        gameState = GameState.RUNNING;
        setRunning();
        SpawnEngine.spawnEnemies(enemyShips, playerControlledShip);
        spawnEnemyWaitingTime = 0L;
    }

    private void setRunning() {
        gameState = GameState.RUNNING;
        playerControlledShip.resetOrigin();
        playerControlledShip.getBullets().forEach(b -> b.setY(WINDOW_HEIGHT));
        items.forEach(i -> i.setY(0));
    }

    private void handleGameOver() {
        playerControlledShip.setSpeed(0);
        playerControlledShip.setBullets(new Array<>());
        for (AIControlledShip enemyShip : enemyShips) {
            enemyShip.setSpeed(0);
        }
        setHighScore();
        setHighestCompletedLevel();
    }

    private void handleComplete() {
        setHighScore();
        setHighestCompletedLevel();
    }

    private void setHighScore() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        Integer highScore = prefs.getInteger(HIGH_SCORE);
        if (highScore == null || highScore < LevelConfig.score) {
            prefs.putInteger(HIGH_SCORE, LevelConfig.score);
            prefs.flush();
        }
    }

    private void setHighestCompletedLevel() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        Integer highestLevel = prefs.getInteger(HIGHEST_LEVEL);
        if (highestLevel == null || highestLevel < LevelConfig.getLevel()) {
            prefs.putInteger(HIGHEST_LEVEL, LevelConfig.getLevel());
            prefs.flush();
        }
    }

}
