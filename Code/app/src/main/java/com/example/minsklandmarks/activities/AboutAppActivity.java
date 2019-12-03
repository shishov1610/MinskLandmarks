package com.example.minsklandmarks.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.minsklandmarks.R;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener  {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
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
}
