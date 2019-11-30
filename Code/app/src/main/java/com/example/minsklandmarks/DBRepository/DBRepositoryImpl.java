package com.example.minsklandmarks.DBRepository;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.minsklandmarks.DBHelper.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;

public class DBRepositoryImpl implements DBRepository{
    private SQLiteDatabase db;
    private Cursor cursor;
    public DBRepositoryImpl(DatabaseHelper dbhelper) {

        //Подключение к базе данных
        db = dbhelper.getWritableDatabase();
    }
    public ArrayList<String> getNames(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.name from landmarksDB as l";
        cursor = db.rawQuery(query,null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                for (String cn : cursor.getColumnNames()) {
                    list.add(cursor.getString(cursor.getColumnIndex(cn)));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getImages(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.image from landmarksDB as l";
        cursor = db.rawQuery(query,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    for (String cn : cursor.getColumnNames()) {
                        list.add(cursor.getString(cursor.getColumnIndex(cn)));
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getInfo(int i){
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
    public ArrayList<String> getFavorites(){
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
    public ArrayList<String> getAllCoordinates(){
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
    public ArrayList<String> getAllCoordinates(int i){
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
