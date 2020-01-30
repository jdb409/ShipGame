package com.jon.AI;

import com.badlogic.gdx.utils.TimeUtils;
import com.jon.Constants;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;

import java.util.Iterator;


public class StandardEnemyAI implements AI {

    boolean moveRight = false;

    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
        updateBullets(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        float fullShipLength = ship.getX() + ship.getWidth();
        if (ship.getX() <= 0) {
//            moveDown(ship);
            moveRight(ship);
            moveRight = true;
        } else if ((fullShipLength >= Constants.WINDOW_WIDTH)) {
//            moveDown(ship);
            moveLeft(ship);
            moveRight = false;
        } else if (moveRight == true) {
            moveRight(ship);
        } else {
            moveLeft(ship);
        }
    }

    @Override
    public void updateBullets(AIControlledShip ship) {
        if (TimeUtils.nanoTime() - ship.getLastBulletFired() > 1000000000) {
//            spawnBullets(ship);
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
                        ship.getBulletHeight());
        bullet.setSpeed(5);
        bullet.moveDown();
        ship.getBullets().add(bullet);
        ship.setLastBulletFired(TimeUtils.nanoTime());
    }

}
