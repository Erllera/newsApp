package com.geektech.a41.ui;

import android.app.Application;

import androidx.room.Room;

import com.geektech.a41.room.AppDataBase;

public class App extends Application {
    public static AppDataBase appDataBase;


    public static AppDataBase getAppDataBase() {
        return appDataBase;
    }

    public static void setAppDataBase(AppDataBase appDataBase) {
        App.appDataBase = appDataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appDataBase = Room.databaseBuilder(this, AppDataBase.class, "myDataBase")
                .allowMainThreadQueries()
                .build();
    }
}
