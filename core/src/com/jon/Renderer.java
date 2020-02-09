package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;


public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private PlayerControllerShip playerControllerShip;
    private int scrollY;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        initGameObjects();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.1f, .1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        scrollY--;
        batch.draw(AssetLoader.bg, 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);

        switch (this.world.gameState) {
            case READY:
                handleReady();
                break;
            case RUNNING:
                handleRunning();
                break;
            case GAME_OVER:
                handleGameOver();
                break;
        }
        batch.end();
    }

    private void handleRunning() {
        Array<AIControlledShip> enemyShips = world.getEnemyShips();
        Array<Bullet> heroBullets = world.getPlayerControlledShip().getBullets();
        font.draw(batch, "Ships Destroyed: " + world.getShipsDestroyed(), 0, Constants.WINDOW_HEIGHT);
        batch.draw(playerControllerShip.getImage(), playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
        for (int h = 0; h < playerControllerShip.getHealth(); h++) {
            batch.draw(playerControllerShip.getOriginalImage(), (h * 25), 0, 20, 25);
        }
        for (AIControlledShip enemyShip : enemyShips) {
            batch.draw(enemyShip.getImage(), enemyShip.getX(), enemyShip.getY(), enemyShip.getWidth(), enemyShip.getHeight());
            for (Bullet bullet : enemyShip.getBullets()) {
                batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
        }
        for (Bullet bullet : heroBullets) {
            batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }

    private void handleReady() {
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

    private void handleGameOver() {
        handleRunning();
        font.draw(batch, "Game Over", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2);
        font.draw(batch, "Touch Screen", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2 - 20);
    }

    public void dispose() {
        AssetLoader.dispose();
    }

    private void initGameObjects() {
        playerControllerShip = world.getPlayerControlledShip();
    }

}


// shapeRenderer.setProjectionMatrix(camera.combined);
// shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
// shapeRenderer.rect(playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
// shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
// shapeRenderer.end();
