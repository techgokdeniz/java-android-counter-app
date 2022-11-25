package com.example.counterapp;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs
{
    private static SharedPrefs instance;

    private SharedPreferences mSharedPref;
    private Context context;

    public static final String NAME = "NAME";

    private SharedPrefs(Context context)
    {
        this.context = context;
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefs(context);
        }

        return instance;
    }


    public String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }
}
