package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jon.AlienInvaderGame;
import com.jon.AssetLoader;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

public class MainMenuScreen implements Screen {
    private AlienInvaderGame game;
    private OrthographicCamera camera;
    private int scrollY;

    public MainMenuScreen(AlienInvaderGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
    }

    @Override
    public void render(float delta) {
        int midX = WINDOW_WIDTH / 2 - 75;
        scrollY = scrollY - 1 % 1600;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(AssetLoader.bg, 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);
        game.font.draw(game.batch, "Welcome to the game", midX, WINDOW_HEIGHT/2 + 50);
        game.font.draw(game.batch, "Tap anywhere to begin", midX, WINDOW_HEIGHT/2);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
