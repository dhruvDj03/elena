package com.example.minismart;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtlatitude;
    EditText txtlongitude;
    EditText txtname;
    Location location;
    Button addlocation;
    private Firebase mref;
    DatabaseReference reff;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtlatitude=findViewById(R.id.latitudetext);
        txtlongitude=findViewById(R.id.longitudetext);
        txtname=findViewById(R.id.editName);
        addlocation=findViewById(R.id.addbutton);
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
                Intent intent=new Intent(MainActivity.this,displaylocation.class);
                startActivity(intent);
            }
        });
    }

}