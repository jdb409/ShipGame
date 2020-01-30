package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture shipTexture;
    public static TextureAtlas shipAtlas;

    public static TextureAtlas.AtlasRegion redShip;
    public static TextureAtlas.AtlasRegion blueShip;

    public static Texture shotTexture;
    public static TextureAtlas shotAtlas;

    public static TextureAtlas.AtlasRegion redShot;
    public static TextureAtlas.AtlasRegion blueShot;

    public static void load() {
        //in atlas files
        //decrease size, to increase render size
        shipAtlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        shotAtlas = new TextureAtlas(Gdx.files.internal("Shots.atlas"));
        shotTexture = new Texture(Gdx.files.internal("Shots.png"));
        redShip = shipAtlas.findRegion("Ship2");
        redShot = shotAtlas.findRegion("shot6");

        blueShip = shipAtlas.findRegion("Ship3");
        blueShot = shotAtlas.findRegion("shot2");
    }
}
