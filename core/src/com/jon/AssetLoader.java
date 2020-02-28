package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    private static Texture shipTexture;
    private static Texture shotTexture;
    private static Texture items;
    private static Texture whitePixelTexture;

    private static TextureAtlas shipAtlas;
    private static TextureAtlas shotAtlas;
    private static TextureAtlas whiteBossAtlas;
    private static TextureAtlas skinAtlas;


    private static TextureAtlas blueShipExplosionAtlas;
    private static TextureAtlas yellowShipExplosionAtlas;
    private static TextureAtlas redShipExplosionAtlas;
    private static TextureAtlas menuIconsAtlas;
    private static TextureAtlas greenOrangeAtlas;

    public static Texture bg;
    public static Texture bg2;
    public static Texture bg3;

    public static TextureAtlas.AtlasRegion redShip;
    public static TextureAtlas.AtlasRegion blueShip;
    public static TextureAtlas.AtlasRegion fishShip;
    public static TextureAtlas.AtlasRegion whiteBossShip;
    public static TextureAtlas.AtlasRegion orangeBossShip;
    public static TextureAtlas.AtlasRegion greenBossShip;


    public static TextureAtlas.AtlasRegion orangeBossShot1;
    public static TextureAtlas.AtlasRegion greenBossShot1;
    public static TextureAtlas.AtlasRegion whiteBossShot1;
    public static TextureAtlas.AtlasRegion whiteBossShot2;
    public static TextureAtlas.AtlasRegion whiteBossShot3;
    public static TextureAtlas.AtlasRegion redShot;
    public static TextureAtlas.AtlasRegion blueShot;


    public static TextureAtlas.AtlasRegion startBtn;
    public static TextureAtlas.AtlasRegion settingsBtn;
    public static TextureAtlas.AtlasRegion soundBtn;
    public static TextureAtlas.AtlasRegion score;
    public static TextureAtlas.AtlasRegion faq;
    public static TextureAtlas.AtlasRegion pause;
    public static TextureAtlas.AtlasRegion exit;

    public static Animation<TextureRegion> blueShipExplosion;
    public static Animation<TextureRegion> yellowShipExplosion;
    public static Animation<TextureRegion> redShipExplosion;
    public static Animation<TextureRegion> whiteBossShipExplosion;
    public static Animation<TextureRegion> greenBossShipExplosion;
    public static Animation<TextureRegion> orangeBossShipExplosion;
    public static Animation<TextureRegion> blueShipHitAnim;

    public static TextureRegion increaseBulletSpeedItem;
    public static TextureRegion increaseWidthItem;
    public static TextureRegion increaseHealthItem;
    public static TextureRegion whitePixelRegion;
    public static Skin menuSkin;

    public static Sound gunShot;
    public static Sound explosion;
    public static Sound getItem;
    public static Sound playerHurt;


    public static void load() {
        //in atlas files
        //decrease size, to increase render size
        skinAtlas = new TextureAtlas(Gdx.files.internal("lgdxs-ui.atlas"));
        menuSkin = new Skin(Gdx.files.internal("lgdxs-ui.json"), skinAtlas);
        loadBackgrounds();
        loadShips();
        loadBullets();
        loadItems();
        loadAnimations();
        loadMenuItems();
        loadSounds();
        setUpShapeDrawer();
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
        whitePixelTexture.dispose();
        greenOrangeAtlas.dispose();
        gunShot.dispose();
        explosion.dispose();
        getItem.dispose();
    }

    private static void loadBackgrounds() {
        bg = new Texture(Gdx.files.internal("bg1.png"));
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bg2 = new Texture(Gdx.files.internal("bg2.png"));
        bg2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        bg3 = new Texture(Gdx.files.internal("bg3.png"));
        bg3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    private static void loadMenuItems() {
        menuIconsAtlas = new TextureAtlas(Gdx.files.internal("menu_icons.atlas"));
        startBtn = menuIconsAtlas.findRegion("Start_BTN");
        settingsBtn = menuIconsAtlas.findRegion("Settings_BTN");
        soundBtn = menuIconsAtlas.findRegion("Sound_BTN");
        score = menuIconsAtlas.findRegion("Score");
        faq = menuIconsAtlas.findRegion("FAQ_BTN");
        pause = menuIconsAtlas.findRegion("Pause_BTN");
        exit = menuIconsAtlas.findRegion("Exit_BTN");
    }

    private static void loadShips() {
        shipAtlas = new TextureAtlas(Gdx.files.internal("Ships.atlas"));
        shipTexture = new Texture(Gdx.files.internal("Ships.png"));
        whiteBossAtlas = new TextureAtlas(Gdx.files.internal("boss.atlas"));
        greenOrangeAtlas = new TextureAtlas(Gdx.files.internal("ship1and4.atlas"));

        blueShip = shipAtlas.findRegion("Ship3");
        redShip = shipAtlas.findRegion("Ship2");
        redShip.flip(false, true);

        fishShip = shipAtlas.findRegion("Ship5");
        float u = fishShip.getU();
        fishShip.setU(fishShip.getV());
        fishShip.setV(u);

        whiteBossShip = whiteBossAtlas.findRegion("Ship6");
        greenBossShip = greenOrangeAtlas.findRegion("Ship1");
        orangeBossShip = greenOrangeAtlas.findRegion("Ship4");
    }

    private static void loadBullets() {
        shotAtlas = new TextureAtlas(Gdx.files.internal("Shots.atlas"));
        shotTexture = new Texture(Gdx.files.internal("Shots.png"));
        redShot = shotAtlas.findRegion("shot6");
        redShot.flip(false, true);
        blueShot = shotAtlas.findRegion("shot2");
        whiteBossShot1 = whiteBossAtlas.findRegion("shot5_0");
        whiteBossShot2 = whiteBossAtlas.findRegion("shot5_1");
        whiteBossShot3 = whiteBossAtlas.findRegion("shot5_2");
        greenBossShot1 = greenOrangeAtlas.findRegion("shot1_exp1");
        orangeBossShot1 = greenOrangeAtlas.findRegion("shot4_exp3");
    }

    private static void loadItems() {
        items = new Texture(Gdx.files.internal("items.png"));
        increaseBulletSpeedItem = new TextureRegion(items, 10, 156, 60, 62);
        increaseWidthItem = new TextureRegion(items, 79, 156, 60, 62);
        increaseHealthItem = new TextureRegion(items, 79, 220, 60, 62);
    }

    private static void loadAnimations() {
        blueShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("BlueShipExplosion.atlas"));
        redShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("RedShipExplosion.atlas"));
        yellowShipExplosionAtlas = new TextureAtlas(Gdx.files.internal("YellowShipExplosion.atlas"));

        blueShipExplosion = new Animation<>(0.1f,
                blueShipExplosionAtlas.findRegions("Ship3_Explosion"), Animation.PlayMode.LOOP);

        redShipExplosion = new Animation<>(0.05f,
                redShipExplosionAtlas.findRegions("Ship2_Explosion"), Animation.PlayMode.NORMAL);

        yellowShipExplosion = new Animation<>(0.05f,
                yellowShipExplosionAtlas.findRegions("Ship5_Explosion"), Animation.PlayMode.NORMAL);

        whiteBossShipExplosion = new Animation<>(.10f,
                whiteBossAtlas.findRegions("Ship6_Explosion"), Animation.PlayMode.NORMAL);

        orangeBossShipExplosion = new Animation<>(.10f,
                greenOrangeAtlas.findRegions("Ship4_Explosion"), Animation.PlayMode.NORMAL);

        greenBossShipExplosion = new Animation<>(.10f,
                greenOrangeAtlas.findRegions("Ship1_Explosion"), Animation.PlayMode.NORMAL);

        TextureRegion[] blueShipHitFrames = new TextureRegion[3];
        for (int frame = 0; frame < 3; frame++) {
            blueShipHitFrames[frame] = blueShipExplosionAtlas.findRegion("Ship3_Explosion", frame);
        }
        blueShipHitAnim = new Animation<>(.05f, blueShipHitFrames);
    }

    private static void loadSounds(){
        gunShot = Gdx.audio.newSound(Gdx.files.internal("Gunshot.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("Explosion.wav"));
        getItem = Gdx.audio.newSound(Gdx.files.internal("GetItem.wav"));
        playerHurt = Gdx.audio.newSound(Gdx.files.internal("Hurt.wav"));
    }


    private static void setUpShapeDrawer() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        whitePixelTexture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        whitePixelRegion = new TextureRegion(whitePixelTexture, 0, 0, 1, 1);
    }

}
