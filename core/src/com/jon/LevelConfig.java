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


    public static void restart() {
        init();
    }

    public static void setNextStage() {
        numRows++;
        if (stage++ == stagesPerLevel) {
            setNextLevel();
        }
    }

    public static void setNextLevel() {
        stage = 1;
        level++;
        diveSpeedMultiplier += .1;
        shootingSpeedMin -= 500;
        numRows = 2;
    }

    private static void init() {
        level = 1;
        numEnemyPerRow = 5;
        numRows = 2;
        stage = 1;
        diveSpeedMultiplier = 0;
        shootingSpeedMax = 3500;
        shootingSpeedMin = 3000;
        //33% chance to shoot.  0,1,2
        chanceToShoot = 2;
        horizontalSpeed = .5f;
        stagesPerLevel = 4;
    }
}
