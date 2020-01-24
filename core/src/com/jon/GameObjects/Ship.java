package com.jon.GameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jon.Constants;

import lombok.Data;

import static com.jon.Constants.SHIP_HEIGHT;
import static com.jon.Constants.SHIP_WIDTH;

@Data
public class Ship implements GameObject {
    public static float SHIP_SPEED;
    private Vector2 velocity;
    //maybe don't need??
    private Vector2 acceleration;
    private Rectangle rectangle;
    private float x;
    private float y;
    private float height;
    private float width;

    public Ship(float x, float y, float width, float height) {
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        rectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {
        velocity.add(this.acceleration);
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
        checkX();
        checkY();
    }

    public void setCenter(float x, float y){
        rectangle.setCenter(x,y);
    }

    private void checkX() {
        if (this.getX() < 0) {
            this.setX(0);
        }

        if (this.getX() > Constants.WINDOW_WIDTH - SHIP_WIDTH) {
            this.setX(Constants.WINDOW_WIDTH - SHIP_WIDTH);
        }
    }

    private void checkY() {
        if (this.getY() < 0) {
            this.setY(0);
        }
        if (this.getY() > Constants.WINDOW_HEIGHT - SHIP_HEIGHT) {
            this.setY(Constants.WINDOW_HEIGHT - SHIP_HEIGHT);
        }
    }

    public void moveLeft() {
        velocity.x = -20;
    }

    public void moveRight() {
        velocity.x = 20;
    }

    public void moveDown() {
        velocity.y = -20;
    }

    public void moveUp() {
        velocity.y = 20;
    }

    public void resetSpeed() {
        velocity.set(0, 0);
    }

    public float getX() {
        return rectangle.x;
    }

    public float getY() {
        return rectangle.y;
    }

    public float getWidth() { return rectangle.width; }

    public float getHeight() {
        return rectangle.height;
    }

    public void setX(float x) {
        rectangle.x = x;
    }

    public void setY(float y) {
        rectangle.y = y;
    }

    public void setWidth(float width) {
        rectangle.width = width;
    }

    public void setHeight(float height) {
        rectangle.height = height;
    }

}
