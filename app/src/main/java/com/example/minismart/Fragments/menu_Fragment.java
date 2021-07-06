package com.example.minismart.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.minismart.MapsActivity;
import com.example.minismart.R;


public class menu_Fragment extends Fragment {
 public CardView way_points,route,trip;
  Fragment selectorFragment;
FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu_, container, false);

        way_points=view.findViewById(R.id.way_points);
        frameLayout=view.findViewById(R.id.frame_layout);
        route=view.findViewById(R.id.route);
        trip=view.findViewById(R.id.trip);
        way_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectorFragment=new displayLocation_Fragment();


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectorFragment)
                        .commit();
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
                 way_points.setVisibility(View.INVISIBLE);
                 route.setVisibility(View.INVISIBLE);
                 trip.setVisibility(View.INVISIBLE);
                // frameLayout.setVisibility(View.INVISIBLE);

            }
        });
        return view;
    }
}