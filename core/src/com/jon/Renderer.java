package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jon.GameObjects.Ship;

import static com.jon.Constants.SHIP_HEIGHT;
import static com.jon.Constants.SHIP_WIDTH;

public class Renderer {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private BitmapFont font;
    Texture dropImage;
    Texture fullShipTexture;
    TextureRegion shipImage;
    private ShapeRenderer shapeRenderer;

    private Ship ship;

    public Renderer(SpriteBatch batch, World world, OrthographicCamera camera) {
        this.batch = batch;
        this.world = world;
        this.camera = camera;
        this.font = new BitmapFont();
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        fullShipTexture = new Texture(Gdx.files.internal("maroon-ship.png"));
        shipImage = new TextureRegion(fullShipTexture, 44, 30, (int) SHIP_WIDTH, (int) SHIP_HEIGHT);
        initGameObjects();
        shapeRenderer = new ShapeRenderer();
    }

    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.

        Array<Rectangle> raindrops = world.getRaindrops();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());
        shapeRenderer.end();

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        font.draw(batch, "Drops Collected: " + world.getDropsGathered(), 0, Constants.WINDOW_HEIGHT);
        batch.draw(shipImage, ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());

        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();
    }

    public void dispose() {

    }

    private void initGameObjects() {
        ship = world.getShip();
    }
}
