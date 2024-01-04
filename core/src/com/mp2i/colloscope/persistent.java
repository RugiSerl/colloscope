package com.mp2i.colloscope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class persistent {
    public static Preferences preference;

    public static void init() {
        //load group number
        preference = Gdx.app.getPreferences("main");
    }


    public static boolean isFirstLaunch() {
        return persistent.preference.getBoolean("FirstLaunch", true);

    }

    public static void save() {
        persistent.preference.putBoolean("FirstLaunch", false);
        persistent.preference.flush();
    }

}
