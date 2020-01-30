package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture shipTexture;
    public static TextureAtlas.AtlasRegion redShip;
    public static TextureAtlas.AtlasRegion blueShip;
    public static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        redShip = atlas.findRegion("Ship2");
        blueShip = atlas.findRegion("Ship3");
    }
}
