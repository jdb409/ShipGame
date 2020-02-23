package com.jon.enemy.AI;

import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;

import java.util.Iterator;


public class BulletSurroundingBossAI implements AI {
    private int bulletSpeed = 2;
    private float nextShot;

    public BulletSurroundingBossAI() {
        this.nextShot = this.getNextShot();
    }


    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        ship.setVelocity(player.getX() - ship.getX(), ((player.getY() - ship.getY())));
        ship.setX(ship.getX() + (ship.getVelocity().x * .5f));
        ship.setY(ship.getY() + (ship.getVelocity().y * .5f));
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {

        if (!ship.isDead()) {
            if ((System.currentTimeMillis() - ship.getLastBulletFired() > this.nextShot)) {
                spawnBullets(ship, player);
                ship.setLastBulletFired(System.currentTimeMillis());
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

    private void spawnBullets(AIControlledShip ship, PlayerControllerShip player) {

        for (int w = 0; w < ship.getWidth() - 5; w += ship.getWidth() / 3) {
            Bullet bullet =
                    new Bullet(ship.getX() + w,
                            ship.getY() + ship.getHeight() + 5,
                            15,
                            30,
                            AssetLoader.orangeBossShot1);
            bullet.setSpeed(bulletSpeed);
            bullet.moveUp();
            ship.getBullets().add(bullet);
        }

        for (int w = 0; w < ship.getWidth() - 5; w += ship.getWidth() / 3) {
            Bullet bullet =
                    new Bullet(ship.getX() + w,
                            ship.getY() - 5,
                            15,
                            30,
                            AssetLoader.orangeBossShot1);
            bullet.setSpeed(bulletSpeed);
            bullet.moveDown();
            ship.getBullets().add(bullet);
        }

        for (int h = 0; h < ship.getHeight() - 5; h += ship.getHeight() / 3) {
            Bullet bullet =
                    new Bullet(ship.getX() - 5,
                            ship.getY() + h,
                            15,
                            30,
                            AssetLoader.orangeBossShot1);
            bullet.setSpeed(bulletSpeed);
            bullet.moveLeft();
            ship.getBullets().add(bullet);
        }

        for (int h = 0; h < ship.getHeight() - 5; h += ship.getHeight() / 3) {
            Bullet bullet =
                    new Bullet(ship.getX() + ship.getWidth() + 5,
                            ship.getY() + h,
                            15,
                            30,
                            AssetLoader.orangeBossShot1);
            bullet.setSpeed(bulletSpeed);
            bullet.moveRight();
            ship.getBullets().add(bullet);
        }
        this.nextShot = this.getNextShot();
    }


    private float getNextShot() {
        return 2000;
    }
}
