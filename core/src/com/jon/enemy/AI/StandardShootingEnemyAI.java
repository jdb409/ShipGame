package com.jon.enemy.AI;

import com.badlogic.gdx.utils.TimeUtils;
import com.jon.AssetLoader;
import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;

import java.util.Iterator;

import lombok.Data;

@Data
public class StandardShootingEnemyAI implements AI {
    boolean moveRight = false;
    int numFramesDir = 0;
    int framesToMove = 60;


    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
        updateBullets(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        float fullShipLength = ship.getX() + ship.getWidth();
        if (moveRight) {
            moveRight(ship);
            numFramesDir++;
            if (numFramesDir == framesToMove) {
                moveRight = false;
                numFramesDir = 0;
            }
        } else {
            moveLeft(ship);
            numFramesDir++;
            if (numFramesDir == framesToMove) {
                moveRight = true;
                numFramesDir = 0;
            }
        }
        if (ship.getX() <= 0) {
            moveRight = true;
            numFramesDir = 0;
        }

        if (fullShipLength >= Constants.WINDOW_WIDTH) {
            moveRight = false;
            numFramesDir = 0;
        }
    }

    @Override
    public void updateBullets(AIControlledShip ship) {
        if (TimeUtils.nanoTime() - ship.getLastBulletFired() > 1000000000) {
            spawnBullets(ship);
        }

        Iterator<Bullet> bulletIterator = ship.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if (bullet.getY() < 0) {
                bulletIterator.remove();
            }
        }
    }

    public void moveDown(AIControlledShip ship) {
        ship.setY(ship.getY() - 75);
    }

    public void moveLeft(AIControlledShip ship) {
        ship.setX(ship.getX() - ship.getSpeed());
    }

    public void moveRight(AIControlledShip ship) {
        ship.setX(ship.getX() + ship.getSpeed());
    }

    private void spawnBullets(AIControlledShip ship) {
        Bullet bullet =
                new Bullet(ship.getX() + ship.getWidth() / 3,
                        ship.getY(),
                        ship.getBulletWidth(),
                        ship.getBulletHeight(),
                        AssetLoader.redShot);
        bullet.setSpeed(2);
        bullet.moveDown();
        ship.getBullets().add(bullet);
        ship.setLastBulletFired(TimeUtils.nanoTime());
    }

}
