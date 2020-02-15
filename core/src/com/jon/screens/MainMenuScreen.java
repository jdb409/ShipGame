package com.jon.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jon.AlienInvaderGame;
import com.jon.AssetLoader;

import static com.jon.Constants.WINDOW_HEIGHT;
import static com.jon.Constants.WINDOW_WIDTH;

public class MainMenuScreen implements Screen {
    private AlienInvaderGame game;
    private OrthographicCamera camera;
    private int scrollY;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;

    public MainMenuScreen(AlienInvaderGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.skin = AssetLoader.menuSkin;
        camera.update();

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);


        TextureRegionDrawable settingDrawable = new TextureRegionDrawable(AssetLoader.settingsBtn);
        ImageButton settingsButton = new ImageButton(settingDrawable);
        skin.getFont("title").getData().setScale(3);
        TextButton startButton = new TextButton("Start", skin, "oval1");
        TextButton stageSelect = new TextButton("Choose Stage", skin, "oval4");
        //Add listeners to buttons
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });


        //Add buttons to table
        mainTable.add(startButton).width(800).height(200);
        mainTable.row().pad(50);
        mainTable.add(stageSelect).width(800).height(200);
        mainTable.row().pad(50);
        mainTable.add(settingsButton);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
//        int midX = WINDOW_WIDTH / 2 - 75;
        scrollY = scrollY - 1 % 1600;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(AssetLoader.bg, 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);
        game.batch.end();
        stage.act();
        stage.draw();
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
