package com.jon.GameObjects.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jon.GameObjects.MoveableGameObject;
import com.jon.GameObjects.PlayerControllerShip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class Item extends MoveableGameObject {
    private static float ITEM_SPEED = 5f;

    private TextureRegion image;

    public Item(float x, float y, float width, float height, TextureRegion image) {
        super(x, y, width, height, ITEM_SPEED);
        this.image = image;
    }

    public abstract void apply(PlayerControllerShip ship);


    @Override
    public void update(float runtime) {
        rectangle.x += velocity.x;
        rectangle.y += velocity.y;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
