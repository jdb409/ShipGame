package com.jon.enemy.AI;

import com.badlogic.gdx.utils.Array;
import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.LevelConfig;
import com.jon.enemy.HorizontalMovement;

import java.util.Iterator;
import java.util.Random;

import lombok.Data;

@Data
public class StandardShootingEnemyAI implements AI {
    private int bulletSpeed = 2;
    private int horizontalSpeed;
    private HorizontalMovement horizontalMovement;
    private float nextShot;

    public StandardShootingEnemyAI() {
        horizontalMovement = new HorizontalMovement();
        horizontalSpeed = 2;
        nextShot = this.getNextShot();
    }

    @Override
    public void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
        updateBullets(ship, player, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        horizontalMovement.update(ship, horizontalSpeed);
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        if (!ship.isDead()) {
            double shouldShoot = Math.round(Math.random() * LevelConfig.chanceToShoot);
            boolean ableToShoot = shouldShoot == 1;
            //if not able to shoot,
            // we still want to set last bullet fired as now because it updates so frequently.
            if ((System.currentTimeMillis() - ship.getLastBulletFired() > this.nextShot)) {
                if (ableToShoot) {
                    spawnBullets(ship);
                } else {
                    ship.setLastBulletFired(System.currentTimeMillis());
                }
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

    private float getNextShot() {
        Random r = new Random();
        return r.nextInt(LevelConfig.shootingSpeedMax - LevelConfig.shootingSpeedMin) + LevelConfig.shootingSpeedMin;
    }

    @Override
    public void die(AIControlledShip ship, float runTime) {
        ship.setBullets(new Array<>());
        ship.setDead(true);
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
        this.nextShot = this.getNextShot();
    }

}
