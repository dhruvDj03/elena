package com.example.minismart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minismart.FirebaseDatabaseHelper;
import com.example.minismart.Location;
import com.example.minismart.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class map_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map_, container, false);
        SupportMapFragment supportMapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                new FirebaseDatabaseHelper().readlocation(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Location> locations, List<String> keys) {
                        LatLng[] location = new LatLng[locations.size()];
                        for (int i = 0; i < locations.size(); i++) {
                            location[i] = new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(location[i]);
                            System.out.println((locations.get(i).getStatus()));
                            if ((locations.get(i).getStatus()).compareTo("not working")!=0) {//If Light is Faulty differentiating the markers
//                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
//                                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_user_location)));
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.blc_point));
                            }
                            googleMap.addMarker(markerOptions.title("Marker in location "+locations.get(i).getLatitude()+" "+locations.get(i).getLongitude()+" "+locations.get(i).getStatus()));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location[i]));
                            googleMap.getUiSettings().setZoomControlsEnabled(true);

                        }

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
        });
        return view;

    }
}