package com.example.apod.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    public static int DB_VERSION = 1;
    public static String DB_NAME = "DB_APOD";
    public static String DB_TABLE_USERS = "Users";
    public static String DB_TABLE_APOD = "APOD";

    public SQLiteManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_USERS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName VARCHAR(20) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "email VARCHAR(80) UNIQUE NOT NULL," +
                "password VARCHAR(256) NOT NULL" +
                ")";

        try{
            sqLiteDatabase.execSQL(sql);
            Log.i("Database", "Table users created!");
        }catch (Exception e){
            Log.i("Database", "Table users not created!\n" + e.toString());
        }

        String createTableAPOD = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_APOD +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date VARCHAR(10) NOT NULL," +
                "title VARCHAR(255) NOT NULL, " +
                "explanation VARCHAR(255), " +
                "url VARCHAR(255) NOT NULL," +
                "media_type VARCHAR(20) NOT NULL" +
                ")";
        try{
            sqLiteDatabase.execSQL(createTableAPOD);
            Log.i("Database", "Table APOD created!");
        }catch (Exception e){
            Log.i("Database", "Table APOD not created!\n" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
