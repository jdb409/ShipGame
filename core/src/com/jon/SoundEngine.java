package com.jon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jon.enums.Sound;

public class SoundEngine {

    private SoundEngine(){}

    public static void playSound(Sound sound, float volume) {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
        boolean isSoundOn = prefs.getBoolean("SoundOn", true);
        if (!isSoundOn) {
            return;
        }

        switch (sound) {
            case EXPLOSION:
                AssetLoader.getInstance().getExplosion().play(volume);
                break;
            case GET_ITEM:
                AssetLoader.getInstance().getGetItem().play(volume);
                break;
            case PLAYER_BULLET:
                AssetLoader.getInstance().getGunShot().play(volume);
                break;
            case PLAYER_HURT:
                AssetLoader.getInstance().getPlayerHurt().play(volume);
                break;
        }

    }
}
