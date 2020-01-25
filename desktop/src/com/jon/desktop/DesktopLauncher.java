package com.jon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jon.AlienInvaderGame;
import com.jon.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "AlienInvader";
        config.width = Constants.WINDOW_WIDTH;
        config.height = Constants.WINDOW_HEIGHT;
        new LwjglApplication(new AlienInvaderGame(), config);
    }
}
