package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture shipTexture;
    public static TextureAtlas shipAtlas;

    public static TextureAtlas.AtlasRegion redShip;
    public static TextureAtlas.AtlasRegion blueShip;
    public static TextureAtlas.AtlasRegion fishShip;

    public static Texture shotTexture;
    public static TextureAtlas shotAtlas;

    public static TextureAtlas.AtlasRegion redShot;
    public static TextureAtlas.AtlasRegion blueShot;

    public static Texture bg;

    public static void load() {
        //in atlas files
        //decrease size, to increase render size
        shipAtlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        shotAtlas = new TextureAtlas(Gdx.files.internal("Shots.atlas"));
        shotTexture = new Texture(Gdx.files.internal("Shots.png"));

        redShip = shipAtlas.findRegion("Ship2");
        redShip.flip(false, true);
        redShot = shotAtlas.findRegion("shot6");
        redShot.flip(false, true);

        blueShip = shipAtlas.findRegion("Ship3");
        blueShot = shotAtlas.findRegion("shot2");

        fishShip = shipAtlas.findRegion("Ship5");
        float u = fishShip.getU();
        fishShip.setU(fishShip.getV());
        fishShip.setV(u);

        bg = new Texture(Gdx.files.internal("GalaxyUno.png"));
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public static void dispose(){
        shipAtlas.dispose();
        shipTexture.dispose();
        shotTexture.dispose();
        shotAtlas.dispose();
        bg.dispose();
    }
}
