package com.example.apod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchPictureActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextDate;
    Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_picture);
        initialize();
    }

    private void initialize() {
        editTextDate = findViewById(R.id.editTextDate);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSubmit){
            String date = editTextDate.getText().toString();
            if(isValid(date)){
                Intent DescriptionActivity = new Intent(this, DescriptionActivity.class);
                Log.i("DATE :", date);
                DescriptionActivity.putExtra("date", date);
                startActivity(DescriptionActivity);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid date. Use format: YYYY-MM-DD and date has to be after 1995-06-16", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
    public static boolean isValid(String text) {
        if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            Date date = df.parse(text);
            Date after = df.parse("1995-06-19");
            if(date.after(after)){
                return true;
            }
            return false;
        } catch (ParseException ex) {
            return false;
        }
    }
}