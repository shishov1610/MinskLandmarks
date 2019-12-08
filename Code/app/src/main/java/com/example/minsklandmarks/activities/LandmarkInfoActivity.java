package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minsklandmarks.R;
import com.example.minsklandmarks.databaseHelper.DatabaseConnect;
import com.example.minsklandmarks.databaseService.DatabaseServiceImpl;

import java.util.ArrayList;

public class LandmarkInfoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton backButton;
    ImageButton addToFavorite;
    ImageButton route;

    TextView handle1;
    ImageView pic;
    TextView handle2;
    TextView aboutInfo;
    TextView address;
    TextView visitingHours;
    TextView cost;
    TextView telephone;
    TextView officialSite;

    Toast toast;

    DatabaseConnect dbc;
    DatabaseServiceImpl repository;
    int  id;
    String name;
    String image;
    String coordinates;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_info);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        addToFavorite = findViewById(R.id.favoritesButton);
        addToFavorite.setOnClickListener(this);
        route = findViewById(R.id.routeButton);
        route.setOnClickListener(this);

        handle1 = findViewById(R.id.landmarkInfoHandle);
        handle2 = findViewById(R.id.textViewHandle);
        pic = findViewById(R.id.imageViewPic);
        aboutInfo = findViewById(R.id.AboutTextView);
        address = findViewById(R.id.address);
        visitingHours = findViewById(R.id.visitingHours);
        cost = findViewById(R.id.cost);
        telephone = findViewById(R.id.telephone);
        officialSite = findViewById(R.id.officialSite);

        Bundle arguments = getIntent().getExtras();
        name = arguments.getString("name");
        image = arguments.getString("image");
        id = arguments.getInt("id");

        dbc = DatabaseConnect.getInstance();
        repository = new DatabaseServiceImpl(dbc.getDb());

        flag = repository.isFavorite(name);
        if (flag == 0) addToFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        else if (flag == 1) addToFavorite.setImageResource(android.R.drawable.btn_star_big_on );

        final ArrayList<String> info = repository.getInfo(id);

        handle1.setText(name);
        handle2.setText(name);
        int imageId = pic.getResources().getIdentifier(image,"drawable","com.example.minsklandmarks");
        pic.setImageResource(imageId);
        aboutInfo.setText(info.get(1));
        address.setText(info.get(2));
        visitingHours.setText(info.get(3));
        cost.setText(info.get(4));
        telephone.setText(info.get(5));
        officialSite.setText(info.get(6));
        coordinates = info.get(7);

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.backButton:
                this.finish();
                break;
            case R.id.favoritesButton:
                flag = repository.isFavorite(name);
                if (flag == 0){
                    addToFavorite.setImageResource(android.R.drawable.btn_star_big_on );
                    repository.setFavorite(name, 1);
                    toast = Toast.makeText(LandmarkInfoActivity.this, "Добавлено в избранное", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (flag == 1){
                    addToFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                    repository.setFavorite(name, 0);
                    toast = Toast.makeText(LandmarkInfoActivity.this, "Удалено из избранного", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.routeButton:
                Intent routeWindow = new Intent(this, ViewMapActivity.class);
                routeWindow.putExtra("key", 1);
                routeWindow.putExtra("coordinates", coordinates);
                startActivity(routeWindow);
                break;
            default:
                break;
        }
    }
}
