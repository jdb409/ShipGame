package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.jon.AlienInvaderGame;
import com.jon.InputHandler;
import com.jon.Renderer;
import com.jon.World;

class GameScreen implements Screen {
    private AlienInvaderGame game;
    private World world;
    private Renderer renderer;


    public GameScreen(AlienInvaderGame game) {
        this.game = game;
        this.world = new World();
        this.renderer = new Renderer(game.batch, this.world, game.camera);
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void render(float delta) {
        world.update();
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
        world.dispose();
        renderer.dispose();
    }
}
