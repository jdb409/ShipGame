package com.jon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jon.screens.MainMenuScreen;


//Game, impl of ApplicationListener(handles app life cycle) that delegates
//to a screen.  Can have multiple screens
public class AlienInvaderGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        AssetLoader.load();
        this.setScreen(new MainMenuScreen(this, camera));
    }

    @Override
    public void render() {
        //without super.render() screen set in created will not be rendered
        super.render();
    }


}
