package com.jon.GameObjects;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jon.Constants;

import java.util.Iterator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.jon.Constants.WINDOW_HEIGHT;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerControllerShip extends MoveableGameObject {
    private static float DEFAULT_SPEED = 15;
    private static float DEFAULT_BULLET_WIDTH = 15;
    private static float DEFAULT_BULLET_HEIGHT = 24;

    private Array<Bullet> bullets;
    private float lastBulletFired;

    public PlayerControllerShip(float x, float y, float width, float height) {
        super(x, y, width, height, DEFAULT_SPEED);
        bullets = new Array<>();
    }

    @Override
    public void update() {
        updatePosition();
        updateBullets();
    }

    private void updatePosition() {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
        checkX();
        checkY();
    }

    private void updateBullets() {
        if (TimeUtils.nanoTime() - lastBulletFired > 700000000) {
            spawnBullets();
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if (bullet.getY() > WINDOW_HEIGHT) {
                bulletIterator.remove();
            }
        }
    }

    //how often do we spawn bullets?  Depending on current ships.
    private void spawnBullets() {
        Bullet bullet =
                new Bullet(this.getX() + this.getWidth() / 3,
                        this.getY() + this.getHeight(),
                        DEFAULT_BULLET_WIDTH,
                        DEFAULT_BULLET_HEIGHT);
        bullet.moveUp();
        bullets.add(bullet);
        lastBulletFired = TimeUtils.nanoTime();
    }

    private void checkX() {
        if ((this.getX() + this.getWidth() / 2) < 0) {
            this.setX((this.getX() + this.getWidth() / 2));
        }

        if (this.getX() > Constants.WINDOW_WIDTH - this.getWidth() / 2) {
            this.setX(Constants.WINDOW_WIDTH - this.getWidth() / 2);
        }
    }

    private void checkY() {
        if (this.getY() < 0) {
            this.setY(0);
        }
        if (this.getY() > Constants.WINDOW_HEIGHT - this.getHeight()) {
            this.setY(Constants.WINDOW_HEIGHT - this.getHeight());
        }
    }
}
