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
    public static TextureAtlas.AtlasRegion blueShipHit1;
    public static TextureAtlas.AtlasRegion blueShipHit2;
    public static TextureAtlas.AtlasRegion blueShipHit3;

    public static Animation<TextureRegion> blueShipHitAnim;

    public static void load() {
        //in atlas files
        //decrease size, to increase render size
        shipAtlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        shotAtlas = new TextureAtlas(Gdx.files.internal("Shots.atlas"));
        shotTexture = new Texture(Gdx.files.internal("Shots.png"));
        blueShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("BlueShipExplosion.atlas"));

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

        TextureRegion[] blueShipHitFrames = new TextureRegion[3];
        blueShipHit1 = blueShipExplosionAtlas.findRegion("Ship3_Explosion", 0);
        blueShipHit2 = blueShipExplosionAtlas.findRegion("Ship3_Explosion", 1);
        blueShipHit3 = blueShipExplosionAtlas.findRegion("Ship3_Explosion", 2);
        blueShipHitFrames[0] = blueShipHit1;
        blueShipHitFrames[1] = blueShipHit2;
        blueShipHitFrames[2] = blueShipHit3;
        blueShipHitAnim = new Animation<>(.02f, blueShipHitFrames);


    }

    public static void dispose() {
        shipAtlas.dispose();
        shipTexture.dispose();
        shotTexture.dispose();
        shotAtlas.dispose();
        bg.dispose();
    }
}
