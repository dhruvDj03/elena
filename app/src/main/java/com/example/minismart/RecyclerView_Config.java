package com.example.minismart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_Config {
    private Context mContext;
    private LocationAdapter mLocationAdapter;
    public void setConfig(RecyclerView recyclerView,Context context,List<Location> locations,List<String
            > keys){
        mContext=context;
        mLocationAdapter=new LocationAdapter(locations,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager((context)));
        recyclerView.setAdapter(mLocationAdapter);
    }
    class LocationItemView extends RecyclerView.ViewHolder{
        private TextView latitude;
        private  TextView longitude;
        private TextView status;
        private String key;
        public LocationItemView(ViewGroup parent){
         super(LayoutInflater.from(mContext).inflate(R.layout.location_list_item,parent,false));
        latitude= itemView.findViewById(R.id.latitude);
        longitude=itemView.findViewById(R.id.longitude);
        status=itemView.findViewById(R.id.status);
        }
        public void bind(Location location,String key){
            Log.d("tag",""+location.getLatitude()+" "+location.getLongitude()+" "+location.getStatus());
            latitude.setText("Latitude "+location.getLatitude()+" ");
            longitude.setText("Longitude "+location.getLongitude()+" ");
            status.setText("Name "+String.valueOf(location.getStatus())+" ");
            this.key=key;
        }
    }
    class LocationAdapter extends RecyclerView.Adapter<LocationItemView>{
    private List<Location> mLocationList;
    private List<String> mKeys;
    public LocationAdapter(List<Location> mLocationList,List<String> mKeys){
        this.mLocationList=mLocationList;
        this.mKeys=mKeys;
    }
        @NonNull
        @Override
        public LocationItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LocationItemView(parent);
        }
        @Override
        public void onBindViewHolder(@NonNull LocationItemView holder, int position) {
        holder.bind(mLocationList.get(position),mKeys.get(position));
    }
        @Override
        public int getItemCount() {
            return mLocationList.size();
        }
    }
}
