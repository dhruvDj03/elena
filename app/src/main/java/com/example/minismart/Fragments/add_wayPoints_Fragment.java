package com.example.minismart.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minismart.Location;
import com.example.minismart.MainActivity;
import com.example.minismart.R;
import com.example.minismart.displaylocation;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class add_wayPoints_Fragment extends Fragment {
    EditText txtlatitude;
    EditText txtlongitude;
    EditText txtname;
    Location location;
    CardView addlocation;
    private Firebase mref;
    DatabaseReference reff;
    Fragment selectorFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add_way_points_, container, false);
        txtlatitude=view.findViewById(R.id.latitudetext);
        txtlongitude=view.findViewById(R.id.longitudetext);
        txtname=view.findViewById(R.id.editName);
        addlocation=view.findViewById(R.id.addbutton);
        location=new Location();
        reff= FirebaseDatabase.getInstance().getReference().child("Location");
        addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double latitude=Double.parseDouble(txtlatitude.getText().toString().trim());
                Double longitude=Double.parseDouble(txtlongitude.getText().toString().trim());
                String name=txtname.getText().toString().trim();
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                location.setStatus(name);
                reff.push().setValue(location);
               // Toast.makeText(getContext(), "Way Point Added", Toast.LENGTH_SHORT).show();
                selectorFragment=new displayLocation_Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectorFragment)
                        .commit();
            }
        });

        return view;

    }
}