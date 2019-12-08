package com.example.minsklandmarks.databaseService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

public class DatabaseServiceImpl implements DatabaseService {
    private SQLiteDatabase db;
    private Cursor cursor;
    public DatabaseServiceImpl(SQLiteDatabase db) {
        this.db = db;
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
    public ArrayList<String> getInfo(int id){
        ArrayList<String> list = new ArrayList<>();
        String query = "select * from landmarksInfoDB where _id = ?";
        cursor = db.rawQuery(query,new String[]{ String.valueOf(id+1)});
        if (cursor.moveToFirst()) {
            for (int j = 0; j< 8; j++){
                list.add(cursor.getString(j));
            }
        }
        cursor.close();
        return list;
    }
    public void setFavorite(String name, int i){
        int id = getId(name);
        ContentValues cv = new ContentValues();
        cv.put("isFavorites", i);
        db.update("landmarksInfoDB", cv, "_id = ?", new String[]{ String.valueOf(id)});
    }
    public int isFavorite(String name){
        int flag = 0;
        int id = getId(name);
        String query = "select isFavorites from landmarksInfoDB where _id = ?";
        cursor = db.rawQuery(query,new String[]{ String.valueOf(id)});
        if (cursor.moveToFirst()) {
            flag = cursor.getInt(cursor.getColumnIndex("isFavorites"));
        }
        return flag;
    }
    public ArrayList<String> getFavoritesNames(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.name from landmarksDB  as l " +
                "INNER JOIN landmarksInfoDB as li " +
                "where li._id = l._id and li.isFavorites = ?";
        cursor = db.rawQuery(query,new String[]{ String.valueOf(1)});
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

    public ArrayList<String> getFavoritesImages(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.image from landmarksDB  as l " +
                "INNER JOIN landmarksInfoDB as li " +
                "where li._id = l._id AND li.isFavorites = ?";
        cursor = db.rawQuery(query,new String[]{ String.valueOf(1)});
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
    public ArrayList<String> getSearchNames(String str){
        String search = "%" + str + "%";
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.name from landmarksDB as l where l.name like ?";
        cursor = db.rawQuery(query,new String[] {search});
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
    public ArrayList<String> getSearchImages(String str){
        String search = "%" + str + "%";
        ArrayList<String> list = new ArrayList<>();
        String query = "select l.image from landmarksDB as l where l.name like ?";
        cursor = db.rawQuery(query,new String[] {search});
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
    public int getId(String name){
        int id = 0;
        String query = "select l._id from landmarksDB as l where l.name = ?";
        cursor = db.rawQuery(query,new String[] {name});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex("_id"));
            }
        }
        cursor.close();
        return id;
    }
    public ArrayList<String> getAllCoordinates(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select coordinates from landmarksInfoDB";
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

}
