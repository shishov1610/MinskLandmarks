package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minsklandmarks.R;
import com.example.minsklandmarks.databaseHelper.DatabaseConnect;
import com.example.minsklandmarks.databaseHelper.DatabaseHelper;
import com.example.minsklandmarks.databaseService.DatabaseServiceImpl;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    Button searchBtn;
    String searchStr;
    EditText searchText;
    DatabaseConnect dbc;
    DatabaseServiceImpl repository;
    ArrayList<String> names;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        searchBtn = findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(this);
        searchText = findViewById(R.id.searchText);

        dbc = DatabaseConnect.getInstance();
        repository = new DatabaseServiceImpl(dbc.getDb());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                this.finish();
                break;
            case R.id.searchButton:
                searchStr = searchText.getText().toString();
                names = repository.getSearchNames(searchStr);
                images = repository.getSearchImages(searchStr);

                CustomAdapter customAdapter = new CustomAdapter(names, images);
                ListView listView = findViewById(R.id.searchListView);
                listView.setAdapter(customAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent landmarkInfoWindow = new Intent(SearchActivity.this, LandmarkInfoActivity.class);
                        landmarkInfoWindow.putExtra("name", names.get(position));
                        landmarkInfoWindow.putExtra("image", images.get(position));
                        landmarkInfoWindow.putExtra("id", position);
                        startActivity(landmarkInfoWindow);
                    }
                });
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
