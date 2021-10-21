package com.example.apod.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apod.model.User;

import java.util.List;

public class UserDAO implements IUserDAO{

    private static SQLiteDatabase read;
    private SQLiteDatabase write;


    public UserDAO(Context context){
        SQLiteManager db = new SQLiteManager(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(User user) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("firstName", user.getFirstName());
            contentValues.put("lastName", user.getLastName());
            contentValues.put("email", user.getEmail());
            contentValues.put("password", user.getPassword());

            Long newId = write.insert(SQLiteManager.DB_TABLE_USERS, null, contentValues);
            user.setId(Integer.valueOf(Long.toString(newId)));
        }catch (Exception e){
            Log.i("UserDAO", "Could not save user\n" + e.toString());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            String where = "id = ?";
            String[] args = {String.valueOf(id)};

            int rowsAffected = write.delete(SQLiteManager.DB_TABLE_USERS, where, args);
            if (rowsAffected > 0){
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            Log.i("UserDAO", "delete.error\n" + e.toString());
            return false;
        }


    }

    @Override
    public List<User> ListAll() {
        return null;
    }

    @SuppressLint("Range")
    @Override
    public User find(String email) {
        try{

            String sql = "SELECT id, firstName, lastName, email, password" +
                    " FROM " + SQLiteManager.DB_TABLE_USERS +
                    " WHERE email = '" + email + "';";

            Cursor cursor = read.rawQuery(sql, null);
            if(cursor.moveToNext()){
                User user = new User();

                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
                user.setLastName(cursor.getString(cursor.getColumnIndex("lastName")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));

                return user;
            }

        }catch (Exception e){
            Log.i("Find error", e.toString());
        }
        return null;
    }
}
