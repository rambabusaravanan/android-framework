package com.rambabusaravanan.android.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andro Babu on Oct 15, 2015.
 */
public class BasePreference {

    private SharedPreferences preferences;
    private static BasePreference instance;

    // Constructor and Instance

    public BasePreference(Context context) {
        this.preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public BasePreference(Context context, String FILE_NAME) {
        this.preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static BasePreference getInstance(Context context) {
        if(instance==null) {
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

    // Getter and Setter

    public String get(String key) {
        return get(key, "");
    }
    public String get(String key, String defaultVal) {
        return getPref().getString(key, defaultVal);
    }

    public void put(String key, String val) {
        edit().putString(key, val).commit();
    }

    // Other Functions

    public void clearAll() {
        getPref().edit().clear().commit();
    }

}
