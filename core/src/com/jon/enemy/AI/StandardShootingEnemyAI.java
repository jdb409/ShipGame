package com.jon.enemy.AI;

import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.enemy.HorizontalMovement;

import java.util.Iterator;
import java.util.Random;

import lombok.Data;

@Data
public class StandardShootingEnemyAI implements AI {
    private int bulletSpeed = 2;
    private int horizontalSpeed;
    private HorizontalMovement horizontalMovement;

    public StandardShootingEnemyAI() {
        horizontalMovement = new HorizontalMovement();
        horizontalSpeed = 2;
    }

    @Override
    public void update(AIControlledShip ship) {
        updatePosition(ship);
        updateBullets(ship);
    }

    @Override
    public void updatePosition(AIControlledShip ship) {
        horizontalMovement.update(ship, horizontalSpeed);
    }

    @Override
    public void updateBullets(AIControlledShip ship) {
        Random r = new Random();
        long shotTime = r.nextInt(2500 - 2000) + 2000;
        if (System.currentTimeMillis() - ship.getLastBulletFired() > shotTime) {
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

    private void spawnBullets(AIControlledShip ship) {
        Bullet bullet =
                new Bullet(ship.getX() + ship.getWidth() / 3,
                        ship.getY(),
                        ship.getBulletWidth(),
                        ship.getBulletHeight(),
                        AssetLoader.redShot);
        bullet.setSpeed(bulletSpeed);
        bullet.moveDown();
        ship.getBullets().add(bullet);
        ship.setLastBulletFired(System.currentTimeMillis());
    }

}
