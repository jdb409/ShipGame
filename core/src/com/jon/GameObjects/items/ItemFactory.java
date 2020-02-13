package com.jon.GameObjects.items;

import com.jon.AssetLoader;

public class ItemFactory {
    private static float ITEM_HEIGHT = 30;
    private static float ITEM_WIDTH = 30;

    public static Item create(ItemType itemType, float x, float y) {
        switch (itemType.getType()) {
            case 0:
                return new IncreaseBulletSpeedItem(x, y, ITEM_WIDTH, ITEM_HEIGHT, AssetLoader.increaseBulletSpeedItem);
            case 1:
                return new IncreaseHealthItem(x, y, ITEM_WIDTH, ITEM_HEIGHT, AssetLoader.increaseHealthItem);
            case 2:
                return new IncreaseBulletWidthItem(x, y, ITEM_WIDTH, ITEM_HEIGHT, AssetLoader.increaseWidthItem);
            default:
                System.out.println("Item Factory - Should not reach here");
                return null;
        }
    }
}
