package com.example.apod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.apod.adapter.Adapter;
import com.example.apod.helper.ApodDAO;
import com.example.apod.helper.UserDAO;
import com.example.apod.model.APOD;
import com.example.apod.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButtonAdd;
    private Button buttonLogout;
    private ApodDAO apodDAO;
    private ArrayList<APOD> APODlist = new ArrayList<APOD>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initialize();

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        this.loadAPODs();

    }

    private void initialize() {

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        buttonLogout = findViewById(R.id.ButtonLogout);

        floatingActionButtonAdd.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        apodDAO = new ApodDAO(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        APODlist = (ArrayList<APOD>) apodDAO.ListAll();
        Adapter adapter = new Adapter(APODlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent DescriptionActivity = new Intent(getApplicationContext(), DescriptionActivity.class);
                        APOD apod = apodDAO.find(APODlist.get(position).getId());
                        DescriptionActivity.putExtra("APOD", (Serializable) apod);
                        startActivity(DescriptionActivity);
                    }
                    UserDAO userDAO = new UserDAO(getApplicationContext());
                    @Override
                    public void onLongItemClick(View view, int position) {
                        APOD selectedApod = apodDAO.find(APODlist.get(position).getId());

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
                        dialog.setTitle("Delete APOD?");
                        dialog.setMessage("Do you really want to delete the APOD: " + selectedApod.getTitle() + "?");
                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                apodDAO.delete(selectedApod.getId());
                                Log.i("List Activity", "Clicked on YES");
                                adapter.notifyItemRemoved(position);
                                APODlist.remove(position);
                                APODlist = (ArrayList<APOD>) apodDAO.ListAll();
                                Adapter adapter1 = new Adapter(APODlist);
                                recyclerView.setAdapter(adapter1);


                            }
                        });

                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //do nothing
                                Log.i("List Activity", "Clicked on NO");
                            }
                        });
                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    public void loadAPODs(){

        APODlist = (ArrayList<APOD>) apodDAO.ListAll();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.floatingActionButtonAdd){
            //APODlist.addAll((ArrayList<APOD>) apodDAO.ListAll());
            //adapter.notifyItemRangeInserted(0, APODlist.size());
            Intent SearchPictureActivity = new Intent(this, SearchPictureActivity.class);
            startActivity(SearchPictureActivity);


            Adapter adapter = new Adapter(APODlist);
            recyclerView.setAdapter(adapter);
        } else if(view.getId() == R.id.ButtonLogout){
            Intent MainActivity = new Intent(this, MainActivity.class);
            startActivity(MainActivity);
        }


    }


}