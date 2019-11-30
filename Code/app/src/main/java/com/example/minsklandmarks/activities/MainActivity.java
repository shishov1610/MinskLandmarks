package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minsklandmarks.DBHelper.DatabaseHelper;
import com.example.minsklandmarks.DBRepository.DBRepositoryImpl;
import com.example.minsklandmarks.R;

import java.io.IOException;
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



        DatabaseHelper helper = new DatabaseHelper(this);
        try{
            helper.createDataBase();
        } catch (IOException ioex){
            throw new Error("Can't initialize DB");
        }
        try{
            helper.openDataBase();
        } catch (SQLException sqlex){
            throw sqlex;
        }

        DBRepositoryImpl repository = new DBRepositoryImpl(helper);
        final ArrayList<String> names = repository.getNames();
        final ArrayList<String> images = repository.getImages();

        CustomAdapter customAdapter = new CustomAdapter(names, images);
        ListView listView = findViewById(R.id.mainListView);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent landmarkInfoWindow = new Intent(MainActivity.this, LandmarkInfoActivity.class);
                landmarkInfoWindow.putExtra("name", names.get(position));
                landmarkInfoWindow.putExtra("image", images.get(position));
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

    class CustomAdapter extends BaseAdapter{

        private ArrayList<String> names;
        private ArrayList<String> images;

        public CustomAdapter(ArrayList<String> names, ArrayList<String> images){
            this.images = images;
            this.names = names;
        }

        @Override
        public int getCount() {
            return images.size();
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
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);

            int imageId = imageView.getResources().getIdentifier(images.get(position).concat("s"),"drawable","com.example.minsklandmarks");
            imageView.setImageResource(imageId);
            textView.setText(names.get(position));
            return convertView;
        }
    }

}
