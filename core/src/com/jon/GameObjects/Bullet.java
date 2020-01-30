package com.jon.GameObjects;


public class Bullet extends MoveableGameObject {
    private static float DEFAULT_SPEED = 5;

    public Bullet(float x, float y, float width, float height) {
        super(x,y,width,height,DEFAULT_SPEED);
    }

    @Override
    public void update() {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
    }
}
