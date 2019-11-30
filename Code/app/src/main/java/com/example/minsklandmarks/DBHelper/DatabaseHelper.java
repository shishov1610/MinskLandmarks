package com.example.minsklandmarks.DBHelper;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.minsklandmarks/databases/";
    private static String DB_NAME = "landmarks.db";
    private SQLiteDatabase dataBase;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //ничего не делаем – файл базы данных уже есть
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //файл базы данных отсутствует
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {
        InputStream input = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }
    public void openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        dataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }
    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}