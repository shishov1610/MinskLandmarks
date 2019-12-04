package com.example.minsklandmarks.databaseHelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class DatabaseConnect {
    private static DatabaseConnect mInstance;
    private static Context mContext;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    private DatabaseConnect() {
        helper = new DatabaseHelper(mContext);
        try{
            helper.createDataBase();
        } catch (IOException ioex){
            throw new Error("Can't initialize DB");
        }
        try{
            helper.openDataBase();
        } catch (SQLException sqlex){
            throw sqlex;
        }
        db = helper.getWritableDatabase();
    }

    public static void initInstance() {
        if (mInstance == null) {
            mInstance = new DatabaseConnect();
        }
    }

    public static DatabaseConnect getInstance() {
        return mInstance;
    }

    public static void setContext(Context context){
        mContext = context;
    }

    public SQLiteDatabase getDb(){
        return db;
    }

}
