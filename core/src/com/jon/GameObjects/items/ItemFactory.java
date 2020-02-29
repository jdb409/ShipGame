package com.jon.GameObjects.items;

import com.jon.AssetLoader;
import com.jon.enums.ItemType;

public class ItemFactory {
    private static final float ITEM_HEIGHT = 30;
    private static final float ITEM_WIDTH = 30;

    private ItemFactory() {
    }

    public static Item create(ItemType itemType, float x, float y) {
        switch (itemType.getType()) {
            case 0:
                return new IncreaseBulletSpeedItem(x,
                        y,
                        ITEM_WIDTH,
                        ITEM_HEIGHT,
                        AssetLoader.getInstance().getIncreaseBulletSpeedItem());
            case 1:
                return new IncreaseHealthItem(x,
                        y,
                        ITEM_WIDTH,
                        ITEM_HEIGHT,
                        AssetLoader.getInstance().getIncreaseHealthItem());
            case 2:
                return new IncreaseBulletWidthItem(x,
                        y,
                        ITEM_WIDTH,
                        ITEM_HEIGHT,
                        AssetLoader.getInstance().getIncreaseHealthItem());
            default:
                System.out.println("Item Factory - Should not reach here");
                return null;
        }
    }
}
