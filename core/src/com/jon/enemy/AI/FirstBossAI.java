package com.jon.enemy.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jon.AssetLoader;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.enemy.FullScreenHorizontalMovement;
import com.jon.enemy.HorizontalMovement;

import java.util.Iterator;

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
    public void update(AIControlledShip ship, PlayerControllerShip player, float runTime) {
        updatePosition(ship, player, runTime);
        updateBullets(ship, player, runTime);
    }

    @Override
    public void updatePosition(AIControlledShip ship, PlayerControllerShip player, float runTime) {
//        horizontalMovement.update(ship, horizontalSpeed);
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
}
