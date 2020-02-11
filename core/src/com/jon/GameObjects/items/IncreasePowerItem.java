package com.jon.GameObjects.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jon.GameObjects.PlayerControllerShip;

public class IncreasePowerItem extends Item {

    public IncreasePowerItem(float x, float y, float width, float height, TextureRegion image) {
        super(x, y, width, height, image);
        this.moveDown();
    }

    @Override
    public void apply(PlayerControllerShip ship) {
        ship.setBulletFrequency(ship.getBulletFrequency() - 10);
    }
}
