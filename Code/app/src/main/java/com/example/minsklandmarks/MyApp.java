package com.example.minsklandmarks;

import android.app.Application;

import com.example.minsklandmarks.databaseHelper.DatabaseConnect;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseConnect.setContext(getApplicationContext());
        DatabaseConnect.initInstance();
    }
}