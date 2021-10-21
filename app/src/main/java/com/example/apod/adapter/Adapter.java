package com.example.apod.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apod.DescriptionActivity;
import com.example.apod.R;
import com.example.apod.model.APOD;

import java.io.InputStream;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private ArrayList<APOD> APODlist;

    public Adapter(ArrayList<APOD> APODlist) { this.APODlist = APODlist; }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitleAdapter);
            date = itemView.findViewById(R.id.textViewDateAdapter);
            image = itemView.findViewById(R.id.imageViewAdapter);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_apods,
                parent,
                false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        APOD apod = APODlist.get(position);

        holder.title.setText(apod.getTitle());
        holder.date.setText(apod.getDate());
        new DownloadImageTask(holder.image)
                .execute(String.valueOf(apod.getUrl()));
    }

    @Override
    public int getItemCount() {
        return APODlist.size();
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
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
}
