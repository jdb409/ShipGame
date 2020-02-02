package com.jon.GameObjects;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Data;

@Data
public class Bullet extends MoveableGameObject {
    private static float DEFAULT_SPEED = 5;
    private TextureRegion image;

    public Bullet(float x, float y, float width, float height, TextureRegion image) {
        super(x, y, width, height, DEFAULT_SPEED);
        this.image = image;
    }

    @Override
    public void update() {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
    }


}
