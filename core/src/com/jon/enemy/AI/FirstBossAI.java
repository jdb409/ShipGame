package com.jon.enemy.AI;

import com.badlogic.gdx.utils.Array;
import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;

import java.util.Iterator;

import static com.jon.Constants.WINDOW_HEIGHT;

public class FirstBossAI implements AI {
    private int bulletSpeed = 2;
    private float nextShot;
    private boolean inPursuit;
    private long lastPursuitEnd;
    private long startPursuitTime;


    public FirstBossAI() {
        nextShot = this.getNextShot();
    }

    @Override
    public void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
        updateBullets(ship, player, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        checkInPursuit();
        if (inPursuit) {
            ship.setVelocity(player.getX() - ship.getX(), ((player.getY() - ship.getY())));
            ship.setX(ship.getX() + (ship.getVelocity().x * 2));
            ship.setY(ship.getY() + (ship.getVelocity().y * 2));
        }
    }

    private void checkInPursuit() {
        if (!inPursuit && (System.currentTimeMillis() - lastPursuitEnd > 5000)) {
            inPursuit = true;
            startPursuitTime = System.currentTimeMillis();
        }

        if (inPursuit && (System.currentTimeMillis() - startPursuitTime > 2000)) {
            inPursuit = false;
            lastPursuitEnd = System.currentTimeMillis();
        }
    }

    @Override
    public void updateBullets(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        if (inPursuit) {
            ship.getBullets().forEach(b -> b.setY(WINDOW_HEIGHT + 100));
            return;
        }

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

    @Override
    public void die(AIControlledShip ship, float runTime) {
        ship.setBullets(new Array<>());
        ship.setDead(true);
    }

    private float getNextShot() {
        return 500;
    }

    private void spawnBullets(AIControlledShip ship, PlayerControllerShip player) {
        Bullet bullet =
                new Bullet(ship.getX() + ship.getWidth() / 3,
                        ship.getY() - 30,
                        15,
                        30,
                        AssetLoader.bossShot1);
        bullet.setSpeed(bulletSpeed);

        //move towards player
        bullet.setVelocity(player.getX() - bullet.getX(), ((player.getY() - bullet.getY())));
        bullet.setVelocityY(bullet.getVelocity().y * 10);
        bullet.setVelocityX(bullet.getVelocity().x * 10);
        ship.getBullets().add(bullet);
        ship.setLastBulletFired(System.currentTimeMillis());
        this.nextShot = this.getNextShot();
    }

    //    public void checkX(AIControlledShip ship) {
//        float fullShipLength = ship.getX() + ship.getWidth();
//        if (ship.getX() <= 0) {
//        }
//
//        if (fullShipLength >= Constants.WINDOW_WIDTH) {
//        }
//    }
}
