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

import static com.jon.Constants.WINDOW_WIDTH;


public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private PlayerControllerShip playerControllerShip;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        initGameObjects();
    }

    public void render() {
        switch (this.world.gameState) {
            case READY:
//                handleReady();
                break;
            case RUNNING:
                handleRunning();
                break;
            case GAME_OVER:
                handleGameOver();
                break;
        }
    }

    private void handleRunning() {
        Array<AIControlledShip> enemyShips = world.getEnemyShips();
        Array<Bullet> heroBullets = world.getPlayerControlledShip().getBullets();
        Gdx.gl.glClearColor(0, 0, 0.1f, .1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Ships Destroyed: " + world.getShipsDestroyed(), 0, Constants.WINDOW_HEIGHT);
//
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        batch.draw(PlayerControllerShip.getImage(), playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());

        for (AIControlledShip enemyShip : enemyShips) {
            batch.draw(enemyShip.getImage(), enemyShip.getX(), enemyShip.getY(), enemyShip.getWidth(), enemyShip.getHeight());
            for (Bullet bullet : enemyShip.getBullets()) {
                batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
        }
        for (Bullet bullet : heroBullets) {
            batch.draw(bullet.getImage(), bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
        batch.end();
    }

    private void handleGameOver() {
        Gdx.gl.glClearColor(0, 0, 0.1f, .1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Game Over", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2);
        font.draw(batch, "Touch Screen", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2 - 20);
        batch.end();
    }

    public void dispose() {

    }

    private void initGameObjects() {
        playerControllerShip = world.getPlayerControlledShip();
    }

}


/**
 * shapeRender.setProjectionMatrix(camera.combined);
 * shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
 * shapeRenderer.rect(playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
 * shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
 * shapeRenderer.end();
 */