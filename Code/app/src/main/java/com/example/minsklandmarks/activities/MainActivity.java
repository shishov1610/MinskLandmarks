package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.minsklandmarks.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton favoritesButton;
    ImageButton mapButton;
    ImageButton searchButton;
    ImageButton aboutAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(this);
        mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        aboutAppButton = findViewById(R.id.aboutAppButton);
        aboutAppButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favoritesButton:
                Intent favoritesWindow = new Intent(this, FavoritesActivity.class);
                startActivity(favoritesWindow);
                break;
            case R.id.mapButton:
                Intent mapWindow = new Intent(this, ViewMapActivity.class);
                startActivity(mapWindow);
                break;
            case R.id.searchButton:
                Intent searchWindow = new Intent(this, SearchActivity.class);
                startActivity(searchWindow);
                break;
            case R.id.aboutAppButton:
                Intent aboutAppWindow = new Intent(this, AboutAppActivity.class);
                startActivity(aboutAppWindow);
                break;
            default:
                break;
        }
    }

}
