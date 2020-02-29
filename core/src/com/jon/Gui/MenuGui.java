package com.jon.Gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jon.AlienInvaderGame;
import com.jon.Constants;
import com.jon.LevelConfig;
import com.jon.screens.GameScreen;

import static com.jon.Constants.BOSS_LEVEL_MODULO;
import static com.jon.Constants.HIGHEST_LEVEL;
import static com.jon.Constants.SOUND_ON;

public class MenuGui {
    public static void addMenu(AlienInvaderGame game, Skin skin, Stage stage) {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        skin.getFont("title").getData().setScale(3);
        TextButton startButton = new TextButton("Start", skin, "oval1");


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelConfig.setLevel(1);
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton stageSelect = addStageSelectDialog(game, skin, stage);

        //Add buttons to table
        mainTable.add(startButton).width(800).height(200);
        mainTable.row().pad(50);
        mainTable.add(stageSelect).width(800).height(200);

        //Add table to stage
        stage.addActor(mainTable);
    }

    public static void addSound(Skin skin, Stage stage) {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        boolean isSoundOn = prefs.getBoolean(SOUND_ON, true);
        TextButton soundButton = new TextButton(String.format("Sound: %s", isSoundOn ? "On  " : "Off"), skin, "oval3");
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isSoundOn = prefs.getBoolean(SOUND_ON, true);
                prefs.putBoolean(SOUND_ON, !isSoundOn);
                prefs.flush();
            }
        });
        soundButton.setX(145);
        soundButton.setWidth(800);
        soundButton.setY(Gdx.graphics.getHeight() / 2 - 450);
        soundButton.setHeight(200);
        stage.addActor(soundButton);
        stage.act();
        stage.draw();
    }

    private static TextButton addStageSelectDialog(AlienInvaderGame game, Skin skin, Stage stage) {
        TextButton stageSelect = new TextButton("Choose Stage", skin, "oval4");
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
                    if (i % BOSS_LEVEL_MODULO == 0) {
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
        return stageSelect;

    }
}
