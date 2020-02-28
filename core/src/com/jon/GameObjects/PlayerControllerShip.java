package com.jon.GameObjects;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jon.AssetLoader;
import com.jon.Constants;
import com.jon.LevelConfig;
import com.jon.SoundEngine;
import com.jon.enums.GameState;
import com.jon.World;
import com.jon.enums.ScoreEvent;
import com.jon.enums.Sound;

import java.util.Iterator;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.jon.Constants.PLAYER_SHIP_START_Y;
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
    private static float MAX_BULLET_WIDTH = 50;
    public final int START_BULLET_FREQUENCY = 300;

    private Array<Bullet> bullets;
    private long lastBulletFired;
    private long lastHit;
    private int health;
    private boolean hit;
    private float runTime;
    private long animationStart = 0L;
    private long bulletFrequency;
    private Animation<TextureRegion> deathAnimation;
    private Animation<TextureRegion> hitAnimation;
    private int chanceToSpawnItem = 4;
    private float bulletWidth;

    public PlayerControllerShip(float x, float y, float width, float height, Animation<TextureRegion> deathAnimation, Animation<TextureRegion> hitAnimation) {
        this(x, y, width, height, deathAnimation, hitAnimation, DEFAULT_HEALTH);
    }

    public PlayerControllerShip(float x, float y, float width, float height, Animation<TextureRegion> deathAnimation, Animation<TextureRegion> hitAnimation, int health) {
        super(x, y, width, height, DEFAULT_SPEED);
        bullets = new Array<>();
        this.health = health;
        //the smaller the number, the faster bullets will be spawned
        this.bulletFrequency = START_BULLET_FREQUENCY;
        this.deathAnimation = deathAnimation;
        this.hitAnimation = hitAnimation;
        this.bulletWidth = DEFAULT_BULLET_WIDTH;
    }

    @Override
    public void update(float runTime) {
        this.runTime = runTime;
        updatePosition();
        updateBullets();
    }

    public TextureRegion getImage() {
        if (GameState.GAME_OVER.equals(World.gameState)) {
            return this.deathAnimation.getKeyFrame(300);
        }

        //if hit animation has gone on for longer than 500ms, end anim
        if (animationStart != 0L && System.currentTimeMillis() - animationStart > 150) {
            hit = false;
            animationStart = 0L;
        }
        if (!hit) {
            return defaultImage;
        } else {
            return this.hitAnimation.getKeyFrame(this.runTime);
        }
    }

    private void updatePosition() {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
        checkX();
        checkY();
    }

    private void updateBullets() {
        bulletFrequency = Math.max(bulletFrequency, 100);
        if (System.currentTimeMillis() - lastBulletFired > bulletFrequency) {
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
        bulletWidth = Math.min(bulletWidth, MAX_BULLET_WIDTH);
        float newX = this.getX() + this.getWidth() / 3 - (bulletWidth - DEFAULT_BULLET_WIDTH) / 2;
        Bullet bullet =
                new Bullet(newX,
                        this.getY() + this.getHeight(),
                        bulletWidth,
                        DEFAULT_BULLET_HEIGHT,
                        bulletImage);
        bullet.moveUp();
        bullets.add(bullet);
        lastBulletFired = System.currentTimeMillis();
        SoundEngine.playSound(Sound.PLAYER_BULLET, .5f);
    }

    public void handleCollision(int damage) {
        if (System.currentTimeMillis() - this.getLastHit() < 500) {
            return;
        }

        LevelConfig.modifyScore(ScoreEvent.SHIP_HIT);
        this.setHit(true);
        this.decrementHealth(damage);
        this.setLastHit(System.currentTimeMillis());
        this.setAnimationStart(System.currentTimeMillis());
        SoundEngine.playSound(Sound.PLAYER_HURT, 1.0f);
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
        float topLimit = Constants.WINDOW_HEIGHT - Constants.TOP_AD_BUFFER - this.getHeight();
        if (this.getY() < PLAYER_SHIP_START_Y) {
            this.setY(PLAYER_SHIP_START_Y);
        }
        if (this.getY() > topLimit) {
            this.setY(topLimit);
        }
    }

    private void decrementHealth(int amount) {
        this.health -= amount;
    }

    public TextureRegion getOriginalImage() {
        return defaultImage;
    }
}
