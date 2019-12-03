package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minsklandmarks.R;

public class LandmarkInfoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton backButton;
    ImageButton addToFavorite;

    TextView handle1;
    ImageView pic;
    TextView handle2;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_info);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        addToFavorite = findViewById(R.id.favoritesButton);
        addToFavorite.setOnClickListener(this);

        handle1 = findViewById(R.id.landmarkInfoHandle);
        handle2 = findViewById(R.id.textViewHandle);
        pic = findViewById(R.id.imageViewPic);

        Bundle arguments = getIntent().getExtras();
        String name = arguments.getString("name");

        handle1.setText(name);
        handle2.setText(name);

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.backButton:
                this.finish();
                break;
            case R.id.favoritesButton:
                if (!flag){
                    addToFavorite.setImageResource(android.R.drawable.btn_star_big_on );
                    flag = true;
                }
                else {
                    addToFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                    flag = false;
                }
                break;
            default:
                break;
        }
    }
}
