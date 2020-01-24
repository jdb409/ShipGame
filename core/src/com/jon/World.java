package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jon.GameObjects.Ship;

import java.util.Iterator;

import lombok.Data;

import static com.jon.Constants.SHIP_HEIGHT;
import static com.jon.Constants.SHIP_WIDTH;

@Data
public class World {
    private Ship ship;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private int dropsGathered;

    public World() {
        float midX = Constants.WINDOW_WIDTH / 2 + SHIP_WIDTH / 2;
        ship = new Ship(midX, 20, SHIP_WIDTH, SHIP_HEIGHT);
        this.raindrops = new Array<>();
        spawnRainDrop();
    }

    public void update() {

        ship.update();


        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRainDrop();
        }

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(ship.getRectangle())) {
                dropsGathered++;
                iter.remove();
            }
        }

    }

    private void spawnRainDrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, Constants.WINDOW_WIDTH - 64);
        raindrop.y = Constants.WINDOW_HEIGHT;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public void dispose() {

    }
}
