package com.jon.GameObjects.items;

public enum ItemType {
    INCREASE_POWER(0),
    INCREASE_HEALTH(1),
    INCREASE_WIDTH(2);

    private int type;

    ItemType(int type) {
        this.type = type;
    }

    public static ItemType from(int type) {
        for (ItemType itemType : values()) {
            if (type == itemType.getType()) {
                return itemType;
            }
        }
        return null;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "ItemType{" +
                "type=" + type +
                '}';
    }
}
