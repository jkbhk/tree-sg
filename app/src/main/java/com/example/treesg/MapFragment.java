package com.example.treesg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.treesg.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;


public class MapFragment extends Fragment {
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map,container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
                try {
                    GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.heritage_trees_geojson, getActivity());
                    layer.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Add a marker in Sydney and move the camera
                LatLng Singapore = new LatLng(1.3521, 103.8198);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Singapore));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.5f ) );
            }
        });
        return view;
    }
}