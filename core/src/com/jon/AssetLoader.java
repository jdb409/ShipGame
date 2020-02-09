package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

    public static Animation<TextureRegion> blueShipExplosion;
    public static TextureAtlas blueShipExplosionAtlas;

    public static Animation<TextureRegion> yellowShipExplosion;
    public static TextureAtlas yellowShipExplosionAtlas;

    public static Animation<TextureRegion> redShipExplosion;
    public static TextureAtlas redShipExplosionAtlas;

    public static Animation<TextureRegion> blueShipHitAnim;

    public static void load() {
        //in atlas files
        //decrease size, to increase render size
        shipAtlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        shotAtlas = new TextureAtlas(Gdx.files.internal("Shots.atlas"));
        shotTexture = new Texture(Gdx.files.internal("Shots.png"));
        blueShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("BlueShipExplosion.atlas"));
        redShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("RedShipExplosion.atlas"));
        yellowShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("YellowShipExplosion.atlas"));

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

        blueShipExplosion = new Animation<TextureRegion>(0.1f,
                blueShipExplosionAtlas.findRegions("Ship3_Explosion"), Animation.PlayMode.LOOP);

        redShipExplosion = new Animation<TextureRegion>(0.05f,
                redShipExplosionAtlas.findRegions("Ship2_Explosion"), Animation.PlayMode.NORMAL);

        yellowShipExplosion = new Animation<TextureRegion>(0.05f,
                yellowShipExplosionAtlas.findRegions("Ship5_Explosion"), Animation.PlayMode.NORMAL);

        TextureRegion[] blueShipHitFrames = new TextureRegion[3];
        for (int frame = 0; frame < 3; frame++) {
            blueShipHitFrames[frame] = blueShipExplosionAtlas.findRegion("Ship3_Explosion", frame);
        }
        blueShipHitAnim = new Animation<>(.02f, blueShipHitFrames);


    }

    public static void dispose() {
        shipAtlas.dispose();
        shipTexture.dispose();
        shotTexture.dispose();
        shotAtlas.dispose();
        bg.dispose();
        blueShipExplosionAtlas.dispose();
        yellowShipExplosionAtlas.dispose();
        redShipExplosionAtlas.dispose();

    }
}
