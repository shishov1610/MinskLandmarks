package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minsklandmarks.R;
import com.example.minsklandmarks.databaseHelper.DatabaseConnect;
import com.example.minsklandmarks.databaseService.DatabaseServiceImpl;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);


    }

    @Override
    protected void onStart () {
        super.onStart();

        DatabaseConnect dbc = DatabaseConnect.getInstance();
        DatabaseServiceImpl repository = new DatabaseServiceImpl(dbc.getDb());
        final ArrayList<String> names = repository.getFavoritesNames();
        final ArrayList<String> images = repository.getFavoritesImages();


        CustomAdapter customAdapter = new CustomAdapter(names, images);
        ListView listView = findViewById(R.id.favoritesListView);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent landmarkInfoWindow = new Intent(FavoritesActivity.this, LandmarkInfoActivity.class);
                landmarkInfoWindow.putExtra("name", names.get(position));
                landmarkInfoWindow.putExtra("image", images.get(position));
                landmarkInfoWindow.putExtra("id", position);
                startActivity(landmarkInfoWindow);
            }
        });
    }


    public void onClick(View v){
        switch (v.getId()) {
            case R.id.backButton:
                this.finish();
                break;
            default:
                break;
        }
    }


    class CustomAdapter extends BaseAdapter {

        private ArrayList<String> names;
        private ArrayList<String> images;

        public CustomAdapter(ArrayList<String> names, ArrayList<String> images){
            this.names = names;
            this.images = images;
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
            ImageView imageView = convertView.findViewById(R.id.imageView);

            textView.setText(names.get(position));
            int imageId = imageView.getResources().getIdentifier(images.get(position).concat("s"),"drawable","com.example.minsklandmarks");
            imageView.setImageResource(imageId);
            return convertView;
        }
    }
}
