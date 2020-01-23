package com.jon.screens;

import com.badlogic.gdx.Screen;
import com.jon.AlienInvaderGame;
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
