package com.geektech.a41;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;


public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("setting",Context.MODE_PRIVATE);
    }


    public void saveState(){
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown(){
        return preferences.getBoolean("isShown",false);
    }

    public void saveImage(Uri image){
        preferences.edit().putString("key_image", String.valueOf(image)).apply();
    }

    public String getImage(){
        return preferences.getString("key_image","");
    }

    public void saveString(String name){
        preferences.edit().putString("save_text",name).apply();
    }

    public String getString(){
        return preferences.getString("save_text"," ");
    }
}
