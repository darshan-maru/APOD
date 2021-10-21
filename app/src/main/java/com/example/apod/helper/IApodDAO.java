package com.example.apod.helper;

import android.annotation.SuppressLint;

import com.example.apod.model.APOD;

import java.util.ArrayList;
import java.util.List;

public interface IApodDAO {
    public boolean save(APOD apod);

    public boolean update(APOD apod);

    public boolean delete(int id);

    public List<APOD> ListAll();

    @SuppressLint("Range")
    public APOD find(int id);
}
