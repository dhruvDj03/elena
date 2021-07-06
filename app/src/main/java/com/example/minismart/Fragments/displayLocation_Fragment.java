package com.example.minismart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.minismart.FirebaseDatabaseHelper;
import com.example.minismart.Location;
import com.example.minismart.R;
import com.example.minismart.RecyclerView_Config;
import com.example.minismart.displaylocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class displayLocation_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    ImageView add_wayPoints;
    Fragment selectorFragment;
    ImageView image_delete;
    ImageView image_edit;
    DatabaseReference reff;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display_location_, container, false);
        mRecyclerView=view.findViewById(R.id.display_fragment);
        add_wayPoints=view.findViewById(R.id.add_wayPoints);
        image_delete=view.findViewById(R.id.image_delete);
        reff= FirebaseDatabase.getInstance().getReference().child("Location");
        image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff.child("Latitude").removeValue();
            }
        });
        add_wayPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectorFragment=new add_wayPoints_Fragment();


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectorFragment)
                        .commit();
            }
        });
        new FirebaseDatabaseHelper().readlocation(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Location> locations, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, getActivity(),locations,keys);
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

        return view;
    }

}