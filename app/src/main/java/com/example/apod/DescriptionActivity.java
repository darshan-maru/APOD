package com.example.apod;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apod.helper.ApodDAO;
import com.example.apod.helper.IAPICall;
import com.example.apod.model.APOD;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewImageDate, textViewImageTitle, textViewExplanation;
    ImageView imageViewDescription;
    Button buttonSave, buttonAnotherDate;
    String datePassed, date, title, explanation, url, media_type;

    APOD apodFromClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        initialize();
    }
    private void initialize() {
        textViewImageDate = findViewById(R.id.textViewImageDate);
        textViewImageTitle = findViewById(R.id.textViewImageTitle);
        textViewExplanation = findViewById(R.id.textViewExplanation);
        imageViewDescription = findViewById(R.id.imageViewDescription);
        buttonAnotherDate = findViewById(R.id.buttonAnotherDate);
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);
        buttonAnotherDate.setOnClickListener(this);
        Bundle data = getIntent().getExtras();
        datePassed = (String) data.getSerializable("date");
        apodFromClick = (APOD) data.getSerializable("APOD");

        if(apodFromClick != null){
            showApodInfo();
        } else {
            showInfo();
        }
    }

    private void showApodInfo() {
        media_type = String.valueOf(apodFromClick.getMedia_type());
        date = String.valueOf(apodFromClick.getDate());
        title = String.valueOf(apodFromClick.getTitle());
        explanation = String.valueOf(apodFromClick.getExplanation());
        if(media_type.equals("image")){
            url = String.valueOf(apodFromClick.getUrl());
        }
        textViewImageDate.setText(date);
        textViewImageTitle.setText(title);
        textViewExplanation.setText(explanation);
        new DownloadImageTask(imageViewDescription)
                .execute(url);
    }

    private void showInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IAPICall iApiCall = retrofit.create(IAPICall.class);

        String key = "f9DRrc9EdtxkyKQ9CasetCZJv8jdX20P0j9LXkZ4";
        Call<APOD> call = iApiCall.getData(key, datePassed);

        call.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                // If response != 200, means that the API cannot return the data.
                if (response.code() != 200) {
                    Log.i("Http response", String.valueOf(response.code()));
                    Log.i("URL", String.valueOf(call.request().url()));
                    return;
                }

                media_type = String.valueOf(response.body().getMedia_type());
                date = String.valueOf(response.body().getDate());
                title = String.valueOf(response.body().getTitle());
                explanation = String.valueOf(response.body().getExplanation());
                if(media_type.equals("image")){
                    url = String.valueOf(response.body().getUrl());
                }

                textViewImageDate.setText(date);
                textViewImageTitle.setText(title);
                textViewExplanation.setText(explanation);
                new DownloadImageTask(imageViewDescription)
                        .execute(url);

            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {
                Log.i("Http response", t.toString());
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSave: {
                buttonSaveClicked();
                break;
            }
            case R.id.buttonAnotherDate: {
                buttonAnotherDateClicked();
                break;
            }
            default:
                break;
        }
    }

    private void buttonAnotherDateClicked() {
        Intent SearchPictureActivity = new Intent(this, SearchPictureActivity.class);
        startActivity(SearchPictureActivity);
    }

    private void buttonSaveClicked() {
        ApodDAO apodDao = new ApodDAO(getApplicationContext());
        APOD apod = new APOD();
        apod.setDate(date);
        apod.setTitle(title);
        apod.setExplanation(explanation);
        apod.setUrl(url);
        apod.setMedia_type(media_type);
        if(apodDao.save(apod)){
            Toast.makeText(getApplicationContext(), "APOD saved successfully", Toast.LENGTH_LONG).show();
            Log.i("APOD SAVED URL = ", apod.getUrl());
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.i("SAVE", "Could not save APOD, an error occured");
        }
    }

}