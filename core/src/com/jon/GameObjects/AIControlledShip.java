package com.jon.GameObjects;

import com.badlogic.gdx.utils.Array;
import com.jon.AI.AI;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AIControlledShip extends MoveableGameObject {

    private AI ai;
    private Array<Bullet> bullets;
    private static final float DEFAULT_BULLET_WIDTH = 15;
    private static final float DEFAULT_BULLET_HEIGHT = 24;
    private float lastBulletFired;
    private float bulletHeight;
    private float bulletWidth;

    public AIControlledShip(float x, float y, float width, float height, float speed, AI ai) {
        super(x, y, width, height, speed);
        this.ai = ai;
        bulletWidth = DEFAULT_BULLET_WIDTH;
        bulletHeight = DEFAULT_BULLET_HEIGHT;
        bullets = new Array<>();
    }

    @Override
    public void update() {
        ai.update(this);
    }
}
