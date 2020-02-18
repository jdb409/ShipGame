package com.jon.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import lombok.Data;

@Data
abstract public class MoveableGameObject implements GameObject {
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected Rectangle rectangle;
    protected float height;
    protected float width;
    protected float speed;
    private float originalX;
    private float originalY;

    public MoveableGameObject(float x, float y, float width, float height, float speed) {
        this.originalX = x;
        this.originalY = y;
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        rectangle = new Rectangle(x, y, width, height);
        this.speed = speed;
    }

    public void setCenter(float x, float y) {
        rectangle.setCenter(x, y);
    }

    public void moveLeft() {
        velocity.x = -speed;
    }

    public void moveRight() {
        velocity.x = speed;
    }

    public void moveDown() {
        velocity.y = -speed;
    }

    public void moveUp() {
        velocity.y = speed;
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y).nor();
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    public float getX() {
        return rectangle.x;
    }

    public float getY() {
        return rectangle.y;
    }

    public float getWidth() {
        return rectangle.width;
    }

    public float getHeight() {
        return rectangle.height;
    }

    public void setX(float x) {
        rectangle.x = x;
    }

    public void setY(float y) {
        rectangle.y = y;
    }

    abstract public void update(float runTime);

    public void setWidth(float width) {
        rectangle.width = width;
    }

    public void setHeight(float height) {
        rectangle.height = height;
    }

    public void resetSpeedX() {
        velocity.x = 0;
    }

    public void resetSpeedY() {
        velocity.y = 0;
    }

    public void resetSpeed() {
        velocity.set(0, 0);
    }

    public void resetOrigin() {
        this.setX(this.originalX);
        this.setY(this.originalY);
    }
}
