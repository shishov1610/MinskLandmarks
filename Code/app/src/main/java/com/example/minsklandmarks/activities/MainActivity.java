package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minsklandmarks.R;

import java.util.ArrayList;


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

        final ArrayList<String> names = new ArrayList<>();
        names.add("first");
        names.add("second");
        names.add("third dsjnajgnadkjgnaognajngofngznlgnagorn");
        CustomAdapter customAdapter = new CustomAdapter(names);
        ListView listView = findViewById(R.id.mainListView);
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent landmarkInfoWindow = new Intent(MainActivity.this, LandmarkInfoActivity.class);
                landmarkInfoWindow.putExtra("name", names.get(position));
                startActivity(landmarkInfoWindow);
            }
        });
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

    class CustomAdapter extends BaseAdapter {

        private ArrayList<String> names;

        public CustomAdapter(ArrayList<String> names){
            this.names = names;
        }

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);
            TextView textView = convertView.findViewById(R.id.textView);

            textView.setText(names.get(position));
            return convertView;
        }
    }

}
