package com.jon.enemy.AI;

import com.badlogic.gdx.utils.Array;
import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.LevelConfig;
import com.jon.enemy.FullScreenHorizontalMovement;
import com.jon.enemy.HorizontalMovement;

import java.util.Iterator;
import java.util.Random;

public class FirstBossAI implements AI {
    private int bulletSpeed = 2;
    private int horizontalSpeed;
    private HorizontalMovement horizontalMovement;
    private float nextShot;

    public FirstBossAI() {
        horizontalMovement = new FullScreenHorizontalMovement(360);
        horizontalSpeed = 2;
        nextShot = this.getNextShot();
    }

    @Override
    public void update(AIControlledShip ship, float runTime) {
        updatePosition(ship, runTime);
        updateBullets(ship, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, float runTime) {
        horizontalMovement.update(ship, horizontalSpeed);
    }

    @Override
    public void updateBullets(AIControlledShip ship, float runTime) {
        if (!ship.isDead()) {
//            double shouldShoot = Math.round(Math.random() * LevelConfig.chanceToShoot);
//            boolean ableToShoot = shouldShoot == 1;
            //if not able to shoot,
            // we still want to set last bullet fired as now because it updates so frequently.
            if ((System.currentTimeMillis() - ship.getLastBulletFired() > this.nextShot)) {
//                if (ableToShoot) {
                    spawnBullets(ship);
//                } else {
                    ship.setLastBulletFired(System.currentTimeMillis());
//                }
            }
            Iterator<Bullet> bulletIterator = ship.getBullets().iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                bullet.update(runTime);
                if (bullet.getY() < 0) {
                    bulletIterator.remove();
                }
            }
        }
    }

    @Override
    public void die(AIControlledShip ship, float runTime) {
        ship.setBullets(new Array<>());
        ship.setDead(true);
    }

    private float getNextShot() {
        Random r = new Random();
        return r.nextInt(LevelConfig.shootingSpeedMax - LevelConfig.shootingSpeedMin) + LevelConfig.shootingSpeedMin;
    }

    private void spawnBullets(AIControlledShip ship) {
        Bullet bullet =
                new Bullet(ship.getX() + ship.getWidth() / 3,
                        ship.getY(),
                        40,
                        60,
                        AssetLoader.bossShot1);
        bullet.setSpeed(bulletSpeed);
        bullet.moveDown();
        ship.getBullets().add(bullet);
        ship.setLastBulletFired(System.currentTimeMillis());
        this.nextShot = this.getNextShot();
    }
}
