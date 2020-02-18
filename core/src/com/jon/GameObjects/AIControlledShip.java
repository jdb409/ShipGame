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

    private int health;
    private long lastHit;
    private boolean dead = false;

    private TextureRegion image;
    //animation
    private Animation<TextureRegion> deathAnimation;
    private float animTime;
    private boolean removeFromScreen = false;

    //bullets
    private long lastBulletFired;
    private float bulletHeight;
    private float bulletWidth;

    private float runTime;


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
        this.health = health;
        this.image = image;
        this.deathAnimation = deathAnimation;
        this.bulletWidth = 10;
        this.bulletHeight = 15;
        this.bullets = new Array<>();
    }

    @Override
    public void update(float runTime) {
//        ai.update(this, runTime);
//        this.runTime = runTime;
    }

    public void update(PlayerControllerShip player, float runTime) {
        ai.update(this, player, runTime);
        this.runTime = runTime;
    }

    public void handleCollision() {
        //500ms of invincibility
        if (System.currentTimeMillis() - this.getLastHit() < 500) {
            return;
        }
        this.decrementHealth(1);
        this.setLastHit(System.currentTimeMillis());
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
            //getKeyFrame needs to know how long its been running.  runtime is total, need just this frame
            animTime += Gdx.graphics.getDeltaTime();
            return deathAnimation.getKeyFrame(animTime, false);
        } else {
            return image;
        }
    }


    private void decrementHealth(int amount) {
        health -= amount;
    }

}
