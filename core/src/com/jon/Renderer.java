package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.Bullet;
import com.jon.GameObjects.Ship;


public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    Texture dropImage;
    Texture fullShipTexture;
    TextureRegion shipImage;
    Texture fullRedBullet;
    TextureRegion redBulletImage;
    private ShapeRenderer shapeRenderer;

    private Ship ship;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        fullShipTexture = new Texture(Gdx.files.internal("maroon-ship.png"));
        shipImage = new TextureRegion(fullShipTexture, 44, 30, 40, 70);
        fullRedBullet = new Texture(Gdx.files.internal("red_bullet.png"));
        redBulletImage = new TextureRegion(fullRedBullet, 54, 60, 20, 24);
        initGameObjects();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        Array<Rectangle> raindrops = world.getRaindrops();
        Array<Bullet> heroBullets = world.getShip().getBullets();
        Gdx.gl.glClearColor(0, 0, 0.1f, .9f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Drops Collected: " + world.getDropsGathered(), 0, Constants.WINDOW_HEIGHT);

        batch.draw(shipImage, ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());
        for (Bullet bullet : heroBullets) {
            batch.draw(redBulletImage, bullet.getX(), bullet.getY(), 20, bullet.getHeight());
        }

        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y, 60, 60);
        }
        batch.end();
    }

    public void dispose() {

    }

    private void initGameObjects() {
        ship = world.getShip();
    }
}
