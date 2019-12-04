package com.example.minsklandmarks.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minsklandmarks.R;
import com.example.minsklandmarks.databaseHelper.DatabaseConnect;
import com.example.minsklandmarks.databaseService.DatabaseServiceImpl;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 123;
    ImageButton favoritesButton;
    ImageButton mapButton;
    ImageButton searchButton;
    ImageButton aboutAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasPermissions()){
            // our app has permissions.
            mainActivity();
        }
        else {
            //our app doesn't have permissions, So i m requesting permissions.
            requestPerms();
        }

    }

    private void mainActivity(){

        favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(this);
        mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        aboutAppButton = findViewById(R.id.aboutAppButton);
        aboutAppButton.setOnClickListener(this);

        DatabaseConnect dbc = DatabaseConnect.getInstance();

        DatabaseServiceImpl repository = new DatabaseServiceImpl(dbc.getDb());
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
                landmarkInfoWindow.putExtra("id", position);
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

    private boolean hasPermissions(){
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults){
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            mainActivity();
        }  else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }

    }



    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(MainActivity.this.findViewById(R.id.activity_view), "Permission isn't granted" , Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Location permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            mainActivity();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
