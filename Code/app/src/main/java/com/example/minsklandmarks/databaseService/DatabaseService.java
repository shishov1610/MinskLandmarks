package com.example.minsklandmarks.databaseService;

import java.util.ArrayList;

public interface DatabaseService {
    ArrayList<String> getNames();
    ArrayList<String> getImages();
    ArrayList<String> getInfo(int id);
    void setFavorite(int id, int i);
    int isFavorite(int id);
    ArrayList<String> getFavoritesNames();
    ArrayList<String> getFavoritesImages();
    ArrayList<String> getAllCoordinates();
    ArrayList<String> getAllCoordinates(int i);
}
