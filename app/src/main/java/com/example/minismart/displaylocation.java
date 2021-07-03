package com.example.minismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class displaylocation extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaylocation);
        mRecyclerView= findViewById(R.id.locationrecycler);
        new FirebaseDatabaseHelper().readlocation(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Location> locations, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView,displaylocation.this,locations,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
    public void addlocation(View v){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void mapcheck(View v){
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
}