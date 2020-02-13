package com.jon.GameObjects.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jon.GameObjects.PlayerControllerShip;


public class IncreaseBulletSpeedItem extends Item {
    public final int BULLET_SPEED_ITEM_INCREASE = 10;
    public final int MIN_BULLET_FREQUENCY = 100;

    public IncreaseBulletSpeedItem(float x, float y, float width, float height, TextureRegion image) {
        super(x, y, width, height, image);
        this.moveDown();
    }

    @Override
    public void apply(PlayerControllerShip ship) {
        ship.setBulletFrequency(ship.getBulletFrequency() - BULLET_SPEED_ITEM_INCREASE);
    }
}
