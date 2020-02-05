package com.jon.GameObjects;

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

    public AIControlledShip(float x, float y, float width, float height, float speed, int health, AI ai, TextureRegion image) {
        super(x, y, width, height, speed);
        this.ai = ai;
        bulletWidth = DEFAULT_BULLET_WIDTH;
        bulletHeight = DEFAULT_BULLET_HEIGHT;
        this.image = image;
        bullets = new Array<>();
        this.health = health;
    }

    public void decrementHealth(int amount) {
        health -= amount;
    }

    @Override
    public void update() {
        ai.update(this);
    }

    public TextureRegion getImage() {
        return image;
    }
}
