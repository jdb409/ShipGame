package com.jon.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jon.enemy.AI.AI;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AIControlledShip extends MoveableGameObject {

    private AI ai;
    private Array<Bullet> bullets;
    private static final float DEFAULT_BULLET_WIDTH = 10;
    private static final float DEFAULT_BULLET_HEIGHT = 15;
    private long lastBulletFired;
    private float bulletHeight;
    private float bulletWidth;
    private TextureRegion image;
    private int health;
    private long lastHit;
    private float runTime;
    private boolean dead = false;
    private Animation<TextureRegion> deathAnimation;
    private float animTime;
    private boolean removeFromScreen = false;

    public AIControlledShip(float x,
                            float y,
                            float width,
                            float height,
                            float speed,
                            int health,
                            AI ai,
                            TextureRegion image,
                            Animation<TextureRegion> deathAnimation) {
        super(x, y, width, height, speed);
        this.ai = ai;
        bulletWidth = DEFAULT_BULLET_WIDTH;
        bulletHeight = DEFAULT_BULLET_HEIGHT;
        bullets = new Array<>();
        this.health = health;
        this.image = image;
        this.deathAnimation = deathAnimation;
    }

    public void decrementHealth(int amount) {
        health -= amount;
    }

    @Override
    public void update(float runTime) {

        ai.update(this, runTime);
        this.runTime = runTime;
    }

    public void die() {
        ai.die(this, runTime);
        animTime = 0f;
    }

    public TextureRegion getImage() {
        if (deathAnimation.isAnimationFinished(animTime)) {
            removeFromScreen = true;
        }
        if (dead) {
            animTime += Gdx.graphics.getDeltaTime();
            return deathAnimation.getKeyFrame(animTime, false);
        } else {
            return image;
        }
    }
}
