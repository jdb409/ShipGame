package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.jon.AlienInvaderGame;
import com.jon.enums.GameState;
import com.jon.InputHandler;
import com.jon.Renderer;
import com.jon.World;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

public class GameScreen implements Screen {
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

        World.gameState = GameState.RUNNING;
        this.renderer = new Renderer(game.batch, this.world, game.camera);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);


    }

    @Override
    public void render(float delta) {

        if (World.gameState == GameState.PAUSED && Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenuScreen(game, game.camera));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            World.gameState = GameState.PAUSED;
        }

        if (World.gameState == GameState.COMPLETE && Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game, game.camera));
        }

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
        World.gameState = GameState.PAUSED;
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
