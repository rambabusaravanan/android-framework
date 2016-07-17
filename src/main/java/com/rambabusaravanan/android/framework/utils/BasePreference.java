package com.rambabusaravanan.android.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andro Babu on Oct 15, 2015.
 */
public class BasePreference {

    private static BasePreference instance;
    private SharedPreferences preferences;

    // Constructor and Instance

    public BasePreference(Context context) {
        this.preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public BasePreference(Context context, String fileName) {
        this.preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static BasePreference getInstance(Context context) {
        if (instance == null) {
            instance = new BasePreference(context);
        }
        return instance;
    }

    // Preference and Editor

    public SharedPreferences getPref() {
        return preferences;
    }

    public SharedPreferences.Editor edit() {
        return getPref().edit();
    }

    // Getter and Setter - String

    public String get(String key) {
        return get(key, "");
    }

    public String get(String key, String defaultVal) {
        return getPref().getString(key, defaultVal);
    }

    public void put(String key, String val) {
        edit().putString(key, val).commit();
    }

    // Getter and Setter - Int

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultVal) {
        return getPref().getInt(key, defaultVal);
    }

    public void putInt(String key, int val) {
        edit().putInt(key, val).commit();
    }

    // Getter and Setter - Boolean

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultVal) {
        return getPref().getBoolean(key, defaultVal);
    }

    public void putBoolean(String key, boolean val) {
        edit().putBoolean(key, val).commit();
    }


    // Other Functions

    public void clearAll() {
        getPref().edit().clear().commit();
    }

    public void remove(String key) {
        edit().remove(key).commit();
    }


}
