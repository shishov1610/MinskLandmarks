package com.example.minsklandmarks;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.minsklandmarks.databaseHelper.DatabaseConnect;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        DatabaseConnect.setContext(getApplicationContext());
        DatabaseConnect.initInstance();
    }
}