package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    TextureRegion playerImage;
    TextureRegion blueEnemy;
    TextureRegion redBulletImage;
    TextureRegion blueBulletImage;
    private ShapeRenderer shapeRenderer;

    private PlayerControllerShip playerControllerShip;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        initGameObjects();
        initAssets();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {


        Array<AIControlledShip> enemyShips = world.getEnemyShips();
        Array<Bullet> heroBullets = world.getPlayerControlledShip().getBullets();
        Gdx.gl.glClearColor(0, 0, 0.1f, .1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Ships Destroyed: " + world.getShipsDestroyed(), 0, Constants.WINDOW_HEIGHT);
        batch.draw(playerImage, playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());

        if (GameState.GAME_OVER.equals(world.getGameState())) {
            font.draw(batch, "Game Over", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2);
            font.draw(batch, "Touch Screen", WINDOW_WIDTH / 2 - 40, Constants.WINDOW_HEIGHT / 2 - 20);
        }
        for (AIControlledShip enemyShip : enemyShips) {
            batch.draw(blueEnemy, enemyShip.getX(), enemyShip.getY(), enemyShip.getWidth(), enemyShip.getHeight());
            for (Bullet bullet : enemyShip.getBullets()) {
                batch.draw(blueBulletImage, bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
        }
        for (Bullet bullet : heroBullets) {
            batch.draw(redBulletImage, bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
        batch.end();
    }

    public void dispose() {

    }

    private void initGameObjects() {
        playerControllerShip = world.getPlayerControlledShip();
    }

    private void initAssets() {
        playerImage = AssetLoader.redShip;
        blueEnemy = AssetLoader.blueShip;
        redBulletImage = AssetLoader.redShot;
        blueBulletImage = AssetLoader.blueShot;
    }
}


/**
 * shapeRender.setProjectionMatrix(camera.combined);
 * shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
 * shapeRenderer.rect(playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
 * shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
 * shapeRenderer.end();
 */