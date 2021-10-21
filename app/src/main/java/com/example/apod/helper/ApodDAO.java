package com.example.apod.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apod.model.APOD;

import java.util.ArrayList;
import java.util.List;

public class ApodDAO implements IApodDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public ApodDAO(Context context){
        SQLiteManager db = new SQLiteManager(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(APOD apod) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", apod.getDate());
            contentValues.put("title", apod.getTitle());
            contentValues.put("explanation", apod.getExplanation());
            contentValues.put("url", apod.getUrl());
            contentValues.put("media_type", apod.getMedia_type());

            Long newId = write.insert(SQLiteManager.DB_TABLE_APOD, null, contentValues);
            apod.setId(Integer.valueOf(Long.toString(newId)));
        }catch (Exception e){
            Log.i("ApodDAO", "Could not save APOD\n" + e.toString());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(APOD apod) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            String where = "id = ?";
            String[] args = {String.valueOf(id)};
            write.delete(SQLiteManager.DB_TABLE_APOD, where, args);
            return true;
        } catch(Exception e){
            Log.i("Delete Error", e.toString());
        }

        return false;
    }

    @SuppressLint("Range")
    @Override
    public List<APOD> ListAll() {
        List<APOD> APODs = new ArrayList<>();
        try{
            String sql = "SELECT * from " + SQLiteManager.DB_TABLE_APOD;
            Cursor cursor = read.rawQuery(sql, null);
            while(cursor.moveToNext()){

                APOD apod = new APOD();

                apod.setId(cursor.getInt(cursor.getColumnIndex("id")));
                apod.setDate(cursor.getString(cursor.getColumnIndex("date")));
                apod.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                apod.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                apod.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                apod.setMedia_type(cursor.getString(cursor.getColumnIndex("media_type")));
                Log.i("LISTALL" , apod.getTitle());
                APODs.add(apod);
            }

        }catch (Exception e) {
            Log.i("ApodDAO", "Cannot list all APODs\n" + e.toString());
        }
        return APODs;
    }

    @SuppressLint("Range")
    @Override
    public APOD find(int id) {
        try{

            String sql = "SELECT id, date, title, explanation, url, media_type" +
                    " FROM " + SQLiteManager.DB_TABLE_APOD +
                    " WHERE id = " + id + ";";

            Cursor cursor = read.rawQuery(sql, null);
            if(cursor.moveToNext()){
                APOD apod = new APOD();

                apod.setId(cursor.getInt(cursor.getColumnIndex("id")));
                apod.setDate(cursor.getString(cursor.getColumnIndex("date")));
                apod.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                apod.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                apod.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                apod.setMedia_type(cursor.getString(cursor.getColumnIndex("media_type")));
                return apod;
            }

        }catch (Exception e){
            Log.i("Find error", e.toString());
        }
        return null;
    }
}
