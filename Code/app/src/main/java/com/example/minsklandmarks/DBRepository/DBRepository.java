package com.example.minsklandmarks.DBRepository;

import java.util.ArrayList;

public interface DBRepository {
    ArrayList<String> getNames();
    ArrayList<String> getImages();
    ArrayList<String> getInfo(int i);
    ArrayList<String> getFavorites();
    ArrayList<String> getAllCoordinates();
    ArrayList<String> getAllCoordinates(int i);
}
