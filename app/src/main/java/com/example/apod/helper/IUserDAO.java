package com.example.apod.helper;

import android.annotation.SuppressLint;

import com.example.apod.model.User;

import java.util.List;

public interface IUserDAO {

    public boolean save(User user);

    public boolean update(User user);

    public boolean delete(int id);

    public List<User> ListAll();

    @SuppressLint("Range")
    public User find(String email);
}
