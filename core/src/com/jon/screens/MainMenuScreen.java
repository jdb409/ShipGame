package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jon.AlienInvaderGame;
import com.jon.AssetLoader;
import com.jon.Gui.MenuGui;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

public class MainMenuScreen implements Screen {
    private AlienInvaderGame game;
    private OrthographicCamera camera;
    private int scrollY;
    private Skin skin;
    private Stage stage;

    public MainMenuScreen(AlienInvaderGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.skin = AssetLoader.getInstance().getMenuSkin();
        camera.update();

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        MenuGui.addMenu(game, skin, stage);
    }

    @Override
    public void render(float delta) {
        scrollY = scrollY - 1 % 1600;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(AssetLoader.getInstance().getBg(), 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);
        game.batch.end();
        MenuGui.addSound(skin, stage);
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
