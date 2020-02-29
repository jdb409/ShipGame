package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;
import com.jon.GameObjects.items.Item;

import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.jon.Constants.HIGH_SCORE;
import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;


public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    private int scrollY;

    private ShapeDrawer shapeDrawer;

    private PlayerControllerShip playerControllerShip;
    private Array<AIControlledShip> enemyShips;
    private Array<Bullet> heroBullets;
    private Array<Item> items;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        shapeDrawer = new ShapeDrawer(batch, AssetLoader.getInstance().getWhitePixelRegion());
        initGameObjects();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.1f, .1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //prevent overflow if game runs for too long
        scrollY = scrollY - 1 % 1600;
        batch.disableBlending();
        batch.draw(LevelConfig.bg, 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);
        batch.enableBlending();
        switch (this.world.gameState) {
            case READY:
                handleReady();
                break;
            case PAUSED:
                handlePaused();
                break;
            case RUNNING:
                handleRunning();
                break;
            case COMPLETE:
                handleComplete();
                break;
            case GAME_OVER:
                handleGameOver();
                break;
        }
        batch.end();
    }

    public void dispose() {
        AssetLoader.getInstance().dispose();
    }


    private void handleReady() {
        String levelMsg = String.format("Level: %d, Wave %d", LevelConfig.level, LevelConfig.stage);
        font.draw(batch, levelMsg, WINDOW_WIDTH / 2 - 55, Constants.WINDOW_HEIGHT - 50);
        font.draw(batch, "Get Ready!!!", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2);
        long elapsedSinceSpawn = System.currentTimeMillis() - world.getSpawnEnemyWaitingTime();
        font.getData().setScale(2);
        if (elapsedSinceSpawn > 0 && elapsedSinceSpawn < 600) {
            font.draw(batch, "3", WINDOW_WIDTH / 2 - 10, Constants.WINDOW_HEIGHT / 2 + 40);
        } else if (elapsedSinceSpawn > 600 && elapsedSinceSpawn < 1200) {
            font.draw(batch, "2", WINDOW_WIDTH / 2 - 10, Constants.WINDOW_HEIGHT / 2 + 40);
        } else if (elapsedSinceSpawn > 1200 && elapsedSinceSpawn < 1800) {
            font.draw(batch, "1", WINDOW_WIDTH / 2 - 10, Constants.WINDOW_HEIGHT / 2 + 40);
        }
        font.getData().setScale(1);
    }

    private void handleComplete() {
        handleRunning();
        drawNewHighScore();
        font.getData().setScale(2);
        font.draw(batch, "You Win!!", WINDOW_WIDTH / 2 - 70, Constants.WINDOW_HEIGHT / 2 + 60);
        font.getData().setScale(1);
        font.draw(batch, "Touch Screen To Go Back", WINDOW_WIDTH / 2 - 90, Constants.WINDOW_HEIGHT / 2 - 20);
    }


    private void handleGameOver() {
        handleRunning();
        drawNewHighScore();
        font.draw(batch, "Game Over", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2);
        font.draw(batch, "Touch Screen", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2 - 20);
    }

    private void handleRunning() {
        font.draw(batch, "Score: " + LevelConfig.score, 0, Constants.WINDOW_HEIGHT - Constants.TOP_AD_BUFFER);
        font.draw(batch, String.format("Level: %d, Wave %d", LevelConfig.level, LevelConfig.stage), WINDOW_WIDTH - 150, Constants.WINDOW_HEIGHT - Constants.TOP_AD_BUFFER);
        drawLives();
        drawPlayerShip();
        drawPlayerBullets();
        drawEnemies();
        drawItems();
        drawBulletSpeedBar();
    }


    private void handlePaused() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        Integer highScore = prefs.getInteger(HIGH_SCORE);
        if (highScore != null) {
            font.draw(batch, "High Score: " + highScore, WINDOW_WIDTH / 2 - 50, Constants.WINDOW_HEIGHT / 2 + 60);
        }
        font.draw(batch, "Paused", WINDOW_WIDTH / 2 - 20, Constants.WINDOW_HEIGHT / 2 + 10);
        font.draw(batch, "Touch to resume", WINDOW_WIDTH / 2 - 50, Constants.WINDOW_HEIGHT / 2 - 10);
    }

    private void drawNewHighScore() {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        Integer highScore = prefs.getInteger(HIGH_SCORE);
        if (highScore == null || highScore <= LevelConfig.score) {
            font.getData().setScale(2);
            font.draw(batch, String.format("New High Score: %s !!!!\n", LevelConfig.score), WINDOW_WIDTH / 2 - 150, Constants.WINDOW_HEIGHT / 2 + 60);
            font.getData().setScale(1);
        }
    }

    private void drawLives() {
        batch.draw(playerControllerShip.getOriginalImage(), 5, 5, 20, 25);
        font.draw(batch, String.format("X %d",playerControllerShip.getHealth()),30,25);

    }

    private void drawPlayerShip() {
        batch.draw(playerControllerShip.getImage(), playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
    }

    private void drawPlayerBullets() {
        for (Bullet bullet : heroBullets) {
            batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }

    private void drawEnemies() {
        for (AIControlledShip enemyShip : enemyShips) {
            batch.draw(enemyShip.getImage(), enemyShip.getX(), enemyShip.getY(), enemyShip.getWidth(), enemyShip.getHeight());
            if (enemyShip.isShowHealthBar()) {
                drawEnemyHealthBar(enemyShip);
            }
            for (Bullet bullet : enemyShip.getBullets()) {
                batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
        }
    }

    private void drawEnemyHealthBar(AIControlledShip enemyShip) {
        Color bulletSpeedBar = Color.ROYAL;
        int width = (int) enemyShip.getWidth();

        shapeDrawer.setColor(bulletSpeedBar);
        shapeDrawer.rectangle(enemyShip.getX(),
                enemyShip.getY() + enemyShip.getHeight(),
                width,
                10);

        shapeDrawer.filledRectangle(enemyShip.getX(),
                enemyShip.getY() + enemyShip.getHeight(),
                ((enemyShip.getHealth() / (float) enemyShip.getOriginalHealth()) * width),
                10);
    }

    private void drawItems() {
        for (Item item : items) {
            batch.draw(item.getImage(), item.getX(), item.getY(), item.getWidth(), item.getHeight());
        }
    }

    private void drawBulletSpeedBar() {
        Color bulletSpeedBar = Color.GOLDENROD;
        int width = 150;
        font.setColor(bulletSpeedBar);
        font.draw(batch, "Bullet Speed:", width, 18);
        font.setColor(Color.WHITE);
        shapeDrawer.setColor(bulletSpeedBar);
        shapeDrawer.rectangle(WINDOW_WIDTH - 160,
                5,
                width,
                15);
        shapeDrawer.filledRectangle(WINDOW_WIDTH - 160,
                5,
                ((float) (300 - playerControllerShip.getBulletFrequency()) / 200) * width,
                15);
    }

    private void initGameObjects() {
        playerControllerShip = world.getPlayerControlledShip();
        enemyShips = world.getEnemyShips();
        heroBullets = world.getPlayerControlledShip().getBullets();
        items = world.getItems();
    }

}
