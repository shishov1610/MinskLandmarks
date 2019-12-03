package com.example.minsklandmarks.databaseService;

import java.util.ArrayList;

public interface DatabaseService {
    ArrayList<String> getNames();
    ArrayList<String> getImages();
    ArrayList<String> getInfo(int i);
    ArrayList<String> getFavorites();
    ArrayList<String> getAllCoordinates();
    ArrayList<String> getAllCoordinates(int i);
}
