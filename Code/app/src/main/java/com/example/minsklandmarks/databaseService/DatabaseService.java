package com.example.minsklandmarks.databaseService;

import java.util.ArrayList;

public interface DatabaseService {
    ArrayList<String> getNames();
    ArrayList<String> getImages();
    ArrayList<String> getInfo(int id);
    void setFavorite(String name, int i);
    int isFavorite(String name);
    ArrayList<String> getFavoritesNames();
    ArrayList<String> getFavoritesImages();
    ArrayList<String> getSearchNames(String str);
    ArrayList<String> getSearchImages(String str);
    int getId(String name);
    ArrayList<String> getAllCoordinates();
}
