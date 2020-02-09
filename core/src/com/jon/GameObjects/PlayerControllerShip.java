package com.jon.GameObjects;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jon.AssetLoader;
import com.jon.Constants;
import com.jon.GameState;
import com.jon.World;

import java.util.Iterator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.jon.Constants.WINDOW_HEIGHT;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayerControllerShip extends MoveableGameObject {
    private static int DEFAULT_HEALTH = 3;
    private static TextureRegion defaultImage = AssetLoader.blueShip;
    private static TextureRegion bulletImage = AssetLoader.blueShot;
    private static float DEFAULT_SPEED = 15;
    private static float DEFAULT_BULLET_WIDTH = 15;
    private static float DEFAULT_BULLET_HEIGHT = 24;

    private Array<Bullet> bullets;
    private float lastBulletFired;
    private long lastHit;
    private int health;
    private boolean hit;
    private float runTime;
    private long animationStart = 0L;

    public PlayerControllerShip(float x, float y, float width, float height) {
        super(x, y, width, height, DEFAULT_SPEED);
        bullets = new Array<>();
        this.health = DEFAULT_HEALTH;
    }

    public PlayerControllerShip(float x, float y, float width, float height, int health) {
        super(x, y, width, height, DEFAULT_SPEED);
        bullets = new Array<>();
        this.health = health;
    }

    @Override
    public void update(float runTime) {
        this.runTime = runTime;
        updatePosition();
        updateBullets();
    }

    public void decrementHealth(int amount) {
        this.health -= amount;
    }

    private void updatePosition() {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
        checkX();
        checkY();
    }

    private void updateBullets() {
        if (TimeUtils.nanoTime() - lastBulletFired > 300000000) {
            spawnBullets();
        }

        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(runTime);
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
                        DEFAULT_BULLET_HEIGHT,
                        bulletImage);
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
        if (this.getY() < 25) {
            this.setY(25);
        }
        if (this.getY() > Constants.WINDOW_HEIGHT - this.getHeight()) {
            this.setY(Constants.WINDOW_HEIGHT - this.getHeight());
        }
    }

    public TextureRegion getImage() {
        if (GameState.GAME_OVER.equals(World.gameState)) {
            return AssetLoader.blueShipExplosion.getKeyFrame(this.runTime);
        }
        if (animationStart != 0L && System.currentTimeMillis() - animationStart > 500) {
            hit = false;
            animationStart = 0L;
            this.setWidth(this.getWidth() / 2);
        }
        if (!hit) {
            return defaultImage;
        } else {
            return AssetLoader.blueShipHitAnim.getKeyFrame(this.runTime);
        }
    }

    public TextureRegion getOriginalImage(){
        return defaultImage;
    }
}
