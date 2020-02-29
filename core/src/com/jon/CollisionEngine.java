package com.jon;

import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.GameObjects.items.Item;
import com.jon.enums.GameState;
import com.jon.enums.ScoreEvent;
import com.jon.enums.Sound;

import java.util.Iterator;

import static com.jon.Constants.HANDLE_COLLISION;
import static com.jon.Constants.WINDOW_HEIGHT;

public class CollisionEngine {
    private PlayerControllerShip playerControlledShip;
    private Array<AIControlledShip> enemyShips;
    private Array<Item> items;
    private float runTime;

    public CollisionEngine(PlayerControllerShip playerControlledShip, Array<AIControlledShip> enemyShips, Array<Item> items) {
        this.playerControlledShip = playerControlledShip;
        this.enemyShips = enemyShips;
        this.items = items;
    }

    public void handleShipCollision() {
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

    public void handleItemCollision() {
        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            item.update(runTime);
            if (item.getRectangle().overlaps(playerControlledShip.getRectangle())) {
                SoundEngine.playSound(Sound.GET_ITEM, 1.0f);
                item.apply(playerControlledShip);
                itemIterator.remove();
                LevelConfig.modifyScore(ScoreEvent.RECEIVED_ITEM);
            }
            checkItemBounds(item, itemIterator);
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
            checkPlayerBulletBound(bullet, playerBulletIterator);
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
            checkEnemyBulletBound(bullet, enemyBulletIterator);
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
            World.gameState = GameState.GAME_OVER;
        }
    }

    private void handleEnemyCollision(AIControlledShip enemyShip) {
        enemyShip.handleCollision();
        if (enemyShip.getHealth() <= 0) {
            if (!enemyShip.isDead()) {
                enemyShip.die();
                SpawnEngine.handleItemSpawn(enemyShip, playerControlledShip, items);
                LevelConfig.modifyScore(ScoreEvent.DESTROY_ENEMY);
            }
        }
    }
    private void checkItemBounds(Item item, Iterator<Item> itemIterator) {
        if (item.getY() <= 0 || item.getY() >= WINDOW_HEIGHT) {
            itemIterator.remove();
        }
    }

    private void checkPlayerBulletBound(Bullet item, Iterator<Bullet> bulletIterator) {
        if (item.getY() >= WINDOW_HEIGHT) {
            bulletIterator.remove();
        }
    }

    private void checkEnemyBulletBound(Bullet item, Iterator<Bullet> bulletIterator) {
        if (item.getY() <= 0 || item.getY() >= WINDOW_HEIGHT) {
            bulletIterator.remove();
        }
    }

}
