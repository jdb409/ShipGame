package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.AIControlledShip;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.PlayerControllerShip;


public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    Texture dropImage;
    Texture fullShipTexture;
    TextureRegion shipImage;
    Texture fullRedBullet;
    Texture fullGreenEnemyTexture;
    TextureRegion enemyImage;
    TextureRegion redBulletImage;
    private ShapeRenderer shapeRenderer;

    private PlayerControllerShip playerControllerShip;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        fullShipTexture = new Texture(Gdx.files.internal("maroon-ship.png"));
        shipImage = new TextureRegion(fullShipTexture, 44, 30, 40, 70);
        fullGreenEnemyTexture = new Texture(Gdx.files.internal("green-enemy.png"));
        enemyImage = new TextureRegion(fullGreenEnemyTexture, 44, 30, 40, 70);
        fullRedBullet = new Texture(Gdx.files.internal("red-bullet.png"));
        redBulletImage = new TextureRegion(fullRedBullet, 54, 60, 20, 24);
        initGameObjects();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        Array<AIControlledShip> enemyShips = world.getEnemyShips();
        Array<Bullet> heroBullets = world.getPlayerControlledShip().getBullets();
        Gdx.gl.glClearColor(0, 0, 0.1f, .9f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Drops Collected: " + world.getDropsGathered(), 0, Constants.WINDOW_HEIGHT);

        batch.draw(shipImage, playerControllerShip.getX(), playerControllerShip.getY(), playerControllerShip.getWidth(), playerControllerShip.getHeight());
        for (AIControlledShip enemyShip: enemyShips) {
            batch.draw(enemyImage, enemyShip.getX(), enemyShip.getY(), enemyShip.getWidth(), enemyShip.getHeight());
        }
        for (Bullet bullet : heroBullets) {
            batch.draw(redBulletImage, bullet.getX(), bullet.getY(), 20, bullet.getHeight());
        }

        batch.end();
    }

    public void dispose() {

    }

    private void initGameObjects() {
        playerControllerShip = world.getPlayerControlledShip();
    }
}
