package com.example.minismart;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mref;
    private List<Location> locations=new ArrayList<>();

    public interface  DataStatus{
        void DataIsLoaded(List<Location> locations,List<String> keys );
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDatabaseHelper() {
        mDatabase=FirebaseDatabase.getInstance();
        mref=mDatabase.getReference("Location");


    }
    public void readlocation(final DataStatus dataStatus){
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locations.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:snapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Location location=keyNode.getValue(Location.class);
                    locations.add(location);

                }
                dataStatus.DataIsLoaded(locations,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
