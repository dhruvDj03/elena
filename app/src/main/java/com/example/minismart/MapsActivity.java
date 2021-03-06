package com.example.minismart;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.minismart.Fragments.displayLocation_Fragment;
import com.example.minismart.Fragments.map_Fragment;
import com.example.minismart.Fragments.menu_Fragment;
import com.example.minismart.Fragments.route_Fragment;
import com.example.minismart.Fragments.satellite_Fragment;
import com.example.minismart.Fragments.trip_Fragment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    TextView currentlocation;
    private GoogleMap map;
    private Firebase mref;
    private LinkedHashMap latitude;
    private  ArrayList<Integer> longitude;
    private ArrayList<Integer> status;
    private LocationListener locationListener;
     LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    TextView text_title;
LinearLayout start_latlong;
    BottomNavigationView bottomNavigationView;
  public static Fragment selectorFragment;
    int current_screen,left_screen,right_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        text_title=findViewById(R.id.text_title);
      //  map_layout=findViewById(R.id.map_layout);
        start_latlong=findViewById(R.id.start_latlon);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        selectorFragment=new map_Fragment();
                        current_screen=R.id.fragment_map;
                        text_title.setText("Map View");
                        start_latlong.setVisibility(View.VISIBLE);
                        break;
                    case R.id.setting:
                        selectorFragment=new displayLocation_Fragment();
                        current_screen=R.id.settings_frag;
                        text_title.setText("Settings");
                        start_latlong.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.move_left:
                       if(current_screen==R.id.fragment_map) {
                           selectorFragment = new menu_Fragment();
                           current_screen=R.id.settings_frag;
                           text_title.setText("Settings");
                           start_latlong.setVisibility(View.INVISIBLE);
                       }
                       else if(current_screen==R.id.satelliite_frag){
                           selectorFragment=new map_Fragment();
                           current_screen=R.id.fragment_map;
                           text_title.setText("Map View");
                           start_latlong.setVisibility(View.VISIBLE);
                       }
                       else if(current_screen==R.id.route_frag){
                           selectorFragment=new satellite_Fragment();
                           current_screen=R.id.satelliite_frag;
                           text_title.setText("Satellite View");
                           start_latlong.setVisibility(View.INVISIBLE);

                       }
                       else if(current_screen==R.id.trip_frag){
                           selectorFragment=new route_Fragment();
                           current_screen=R.id.route_frag;
                           text_title.setText("Route Chart");
                           start_latlong.setVisibility(View.INVISIBLE);
                       }
                        break;
                    case R.id.move_right:
                        if(current_screen==R.id.fragment_map) {
                            selectorFragment = new satellite_Fragment();
                            current_screen=R.id.satelliite_frag;
                            text_title.setText("Satellite View");
                            start_latlong.setVisibility(View.INVISIBLE);
                        }
                        else if(current_screen==R.id.satelliite_frag){
                            selectorFragment=new route_Fragment();
                            current_screen=R.id.route_frag;
                            text_title.setText("Route Chart");
                            start_latlong.setVisibility(View.INVISIBLE);
                        }
                      else   if(current_screen==R.id.route_frag){
                            selectorFragment=new trip_Fragment();
                            left_screen=current_screen;
                            current_screen=R.id.trip_frag;
                            text_title.setText("Trip View");
                            start_latlong.setVisibility(View.INVISIBLE);
                        }
                      else if(current_screen==R.id.settings_frag){
                            selectorFragment=new map_Fragment();
                            current_screen=R.id.fragment_map;
                            text_title.setText("Map View");
                            start_latlong.setVisibility(View.VISIBLE);
                        }
                      else if(current_screen==R.id.trip_frag){
                          current_screen=R.id.trip_frag;
                        }

         break;

                }
                if(selectorFragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectorFragment).commit();
                }
                return true;
            }
        });
//        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_displaylocation, null);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        currentlocation=findViewById(R.id.text2);
       // image_right=findViewById(R.id.image_right);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
   /*  image_right.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent=new Intent(MapsActivity.this,displaylocation.class);
             startActivity(intent);
         }
     });

    */
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                try{
                    currentlocation.setText("Current Location is \n Latitude "+Double.toString(location.getLatitude())+"\n Longitude "+Double.toString(location.getLongitude()));
                    Log.d("Latitude",Double.toString(location.getLatitude()));
                    Log.d("Longitude",Double.toString(location.getLongitude()));}

                catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }


        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }


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
                    map.addMarker(markerOptions.title("Marker in location "+locations.get(i).getLatitude()+" "+locations.get(i).getLongitude()+" "+locations.get(i).getStatus()));
                    map.moveCamera(CameraUpdateFactory.newLatLng(location[i]));
                    map.getUiSettings().setZoomControlsEnabled(true);

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
        map.setOnMarkerClickListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
