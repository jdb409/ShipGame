package com.jon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jon.AlienInvaderGame;
import com.jon.AssetLoader;
import com.jon.Constants;
import com.jon.LevelConfig;

import static com.jon.Constants.HIGHEST_LEVEL;
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
        skin.getFont("title").getData().setScale(3);
        TextButton startButton = new TextButton("Start", skin, "oval1");
        TextButton stageSelect = new TextButton("Choose Stage", skin, "oval4");
        //Add listeners to buttons
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig.setLevel(1);
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        stageSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("", skin, "dialog") {
                    public void result(Object obj) {
                        LevelConfig.setLevel((int) obj);
                        game.setScreen(new GameScreen(game));
                    }
                };
                TextButton.TextButtonStyle stageStyle = skin.get("oval1", TextButton.TextButtonStyle.class);
                TextButton.TextButtonStyle exitStyle = skin.get("oval4", TextButton.TextButtonStyle.class);

                Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
                Integer highestLevel = Math.max(prefs.getInteger(HIGHEST_LEVEL, 0), 1);
                for (int i = 1; i <= highestLevel; i++) {
                    dialog.button(Integer.toString(i), i, stageStyle);
                    if (i % 6 == 0){
                        dialog.getButtonTable().row();
                    }
                }
                TextButton goBack = new TextButton("Go Back", exitStyle);
                goBack.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dialog.hide();
                    }
                });

                dialog.row().pad(100);
                dialog.add(goBack);
                dialog.show(stage);
            }
        });


        //Add buttons to table
        mainTable.add(startButton).width(800).height(200);
        mainTable.row().pad(50);
        mainTable.add(stageSelect).width(800).height(200);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        scrollY = scrollY - 1 % 1600;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(AssetLoader.bg, 0, 0, 0, scrollY, WINDOW_WIDTH, WINDOW_HEIGHT);
        game.batch.end();
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        boolean isSoundOn = prefs.getBoolean("SoundOn", true);
        TextButton soundButton = new TextButton(String.format("Sound: %s", isSoundOn ? "On  " : "Off"), skin, "oval3");
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isSoundOn = prefs.getBoolean("SoundOn", true);
                prefs.putBoolean("SoundOn",!isSoundOn);
                prefs.flush();
            }
        });
        soundButton.setX(145);
        soundButton.setWidth(800);
        soundButton.setY(Gdx.graphics.getHeight()/2 - 450);
        soundButton.setHeight(200);
        stage.addActor(soundButton);
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
