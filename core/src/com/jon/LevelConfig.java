package com.jon;


import com.badlogic.gdx.graphics.Texture;
import com.jon.enums.ScoreEvent;

import lombok.Getter;

public class LevelConfig {
    @Getter
    public static int level;
    public static int numEnemyPerRow;
    public static int numRows;
    @Getter
    public static int stage;
    public static int shootingSpeedMax;
    public static int shootingSpeedMin;
    public static int chanceToShoot;
    public static float horizontalSpeed;
    public static int stagesPerLevel;
    public static float diveSpeedMultiplier;
    public static Texture bg;
    public static int score;
    public static int divingFrequencyMax;
    public static int divingFrequencyMin;


    private final static int defaultNumEnemyPerRow = 5;
    private final static int defaultNumRows = 2;
    private final static float defaultDiveSpeedMultiplier = 0;
    private final static int defaultShootingSpeedMax = 3500;
    private final static int defaultShootingSpeedMin = 3000;
    private final static int defaultDivingFrequencyMax = 5000;
    private final static int defaultDivingFrequencyMin = 4000;
    private final static int defaultChanceToShoot = 2;
    private final static float defaultHorizontalSpeed = .5f;

    private static long waveStartTime;


    public static void restart() {
        init();
    }

    public static void setNextStage() {
        waveStartTime = System.currentTimeMillis();
        numRows++;
        if (stage++ == stagesPerLevel) {
            setLevel(++level);
        }
    }

    public static void setLevel(int newLevel) {
        waveStartTime = System.currentTimeMillis();
        bg = chooseBg();
        level = newLevel;
        stage = 1;
        numRows = 2;
        diveSpeedMultiplier = newLevel * .1f;
        shootingSpeedMin = Math.max(defaultShootingSpeedMin - (newLevel * 500), 500);
        shootingSpeedMax = Math.max(defaultShootingSpeedMax - (newLevel * 100), 1000);
        divingFrequencyMax = Math.max(defaultDivingFrequencyMax - (newLevel * 100), 2000);
        divingFrequencyMin = Math.max(defaultDivingFrequencyMin - (newLevel * 200), 2500);
    }

    private static void init() {
        //check if user selected a level
        waveStartTime = System.currentTimeMillis();
        System.out.println(waveStartTime);
        if (level == 0) {
            level = 1;
            diveSpeedMultiplier = defaultDiveSpeedMultiplier;
            shootingSpeedMax = defaultShootingSpeedMax;
            shootingSpeedMin = defaultShootingSpeedMin;
            divingFrequencyMax = defaultDivingFrequencyMax;
            divingFrequencyMin = defaultDivingFrequencyMin;
        }
        bg = chooseBg();
        stage = 1;
        stagesPerLevel = 3;
        numEnemyPerRow = defaultNumEnemyPerRow;
        numRows = defaultNumRows;
        //33% chance to shoot.  0,1,2
        chanceToShoot = defaultChanceToShoot;
        horizontalSpeed = defaultHorizontalSpeed;
        score = 0;
    }

    public static String printConfig() {
        return "LevelConfig{" +
                "level=" + level +
                ", numEnemyPerRow=" + numEnemyPerRow +
                ", numRows=" + numRows +
                ", stage=" + stage +
                ", shootingSpeedMax=" + shootingSpeedMax +
                ", shootingSpeedMin=" + shootingSpeedMin +
                ", chanceToShoot=" + chanceToShoot +
                ", horizontalSpeed=" + horizontalSpeed +
                ", stagesPerLevel=" + stagesPerLevel +
                ", diveSpeedMultiplier=" + diveSpeedMultiplier +
                '}';
    }

    public static void modifyScore(ScoreEvent scoreEvent) {
        switch (scoreEvent) {
            case SHIP_HIT:
                score -= 50;
                break;
            case DESTROY_ENEMY:
                score += 100;
                break;
            case RECEIVED_ITEM:
                score += 10;
                break;
            case WAVE_PASSED:
                score += Math.max(500 - getWaveDuration(), 0);
                break;
            case LEVEL_PASSED:
                score += 1000;
                break;
        }
    }

    private static int getWaveDuration() {
        return (int) (Math.floor((System.currentTimeMillis() - waveStartTime) / 100)) / 2;
    }


    private static Texture chooseBg() {
        int bgImg = (int) Math.floor(Math.random() * 3);
        switch (bgImg) {
            case 0:
                return AssetLoader.bg;
            case 1:
                return AssetLoader.bg2;
            case 2:
                return AssetLoader.bg3;
        }
        return null;
    }
}
