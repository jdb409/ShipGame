package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.jon.AlienInvaderGame;
import com.jon.GameState;
import com.jon.InputHandler;
import com.jon.Renderer;
import com.jon.World;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

class GameScreen implements Screen {
    private AlienInvaderGame game;
    private World world;
    private Renderer renderer;


    public GameScreen(AlienInvaderGame game) {
        this.game = game;
        this.world = new World(game);
        //scale actual screen by desired
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(new InputHandler(world,
                screenWidth / WINDOW_WIDTH,
                screenHeight / WINDOW_HEIGHT));

        this.world.gameState = GameState.RUNNING;
        this.renderer = new Renderer(game.batch, this.world, game.camera);


    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
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
        renderer.dispose();
    }
}
