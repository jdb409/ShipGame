package com.jon;


import lombok.Getter;

public class LevelConfig {
    @Getter
    public static int level;
    public static int numEnemyPerRow;
    public static int numRows;
    public static int stage;
    public static int shootingSpeedMax;
    public static int shootingSpeedMin;
    public static int chanceToShoot;
    public static float horizontalSpeed;
    public static int stagesPerLevel;
    public static float diveSpeedMultiplier;

    private final static int defaultNumEnemyPerRow = 5;
    private final static int defaultNumRows = 2;
    private final static float defaultDiveSpeedMultiplier = 0;
    private final static int defaultShootingSpeedMax = 3500;
    private final static int defaultShootingSpeedMin = 3000;
    private final static int defaultChanceToShoot = 2;
    private final static float defaultHorizontalSpeed = .5f;


    public static void restart() {
        init();
    }

    public static void setNextStage() {
        numRows++;
        if (stage++ == stagesPerLevel) {
            setLevel(++level);
        }
    }

    public static void setLevel(int level) {
        stage = 1;
        numRows = 2;
        diveSpeedMultiplier = level * .1f;
        shootingSpeedMin -= (level * 500);
        shootingSpeedMax -= (level * 500);
    }

    private static void init() {
        level = 1;
        stage = 1;
        stagesPerLevel = 2;
        numEnemyPerRow = defaultNumEnemyPerRow;
        numRows = defaultNumRows;
        diveSpeedMultiplier = defaultDiveSpeedMultiplier;
        shootingSpeedMax = defaultShootingSpeedMax;
        shootingSpeedMin = defaultShootingSpeedMin;
        //33% chance to shoot.  0,1,2
        chanceToShoot = defaultChanceToShoot;
        horizontalSpeed = defaultHorizontalSpeed;
    }

}
