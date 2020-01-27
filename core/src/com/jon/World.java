package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jon.AI.AI;
import com.jon.AI.StandardEnemyAI;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;

import java.util.Iterator;

import lombok.Data;

import static com.jon.Constants.SHIP_HEIGHT;
import static com.jon.Constants.SHIP_WIDTH;
import static com.jon.Constants.WINDOW_HEIGHT;

@Data
public class World {
    private PlayerControllerShip playerControlledShip;
    private long lastDropTime;
    private long lastHeroBullet;
    private int dropsGathered;
    Array<AIControlledShip> enemyShips;


    public World() {
        float midX = Constants.WINDOW_WIDTH / 2 - SHIP_WIDTH / 2;
        playerControlledShip = new PlayerControllerShip(midX, 0, SHIP_WIDTH, SHIP_HEIGHT);
        enemyShips = new Array<>();
        spawnEnemies();
    }

    public void update() {
        playerControlledShip.update();
        Array<Bullet> heroBullets = playerControlledShip.getBullets();
        Iterator<AIControlledShip> iter = enemyShips.iterator();
        while (iter.hasNext()) {
            AIControlledShip enemyShip = iter.next();
            enemyShip.update();

            Iterator<Bullet> bulletIterator = heroBullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if (enemyShip.getRectangle().overlaps(bullet.getRectangle())) {
                    iter.remove();
                    bulletIterator.remove();
                    dropsGathered++;
                }
            }
        }

    }

    private void spawnEnemies() {
        for (int i = 0; i < 10; i++) {
            AI enemyAI = new StandardEnemyAI();
            float randomX = MathUtils.random(0, Constants.WINDOW_WIDTH);
            AIControlledShip aiControlledShip = new AIControlledShip(randomX, 650, SHIP_WIDTH, SHIP_HEIGHT, 5, enemyAI);
            enemyShips.add(aiControlledShip);
        }

    }

    public void dispose() {

    }
}
