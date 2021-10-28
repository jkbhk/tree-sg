package com.example.treesg;

import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.core.utilities.Tree;
import com.google.gson.JsonElement;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.sources.VectorSource;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class MapFragment extends Fragment implements MapboxMap.OnMapClickListener{

    private static final String TAG = "123";
    private MapView mapView;
    private MapboxMap mapboxMap;
    static StringBuilder builder = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //read all species
        InputStream is = getResources().openRawResource(R.raw.species);

        Scanner scanner = new Scanner(is);

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }






        // Inflate the layout for this fragment
        Mapbox.getInstance(getContext().getApplicationContext(),"pk.eyJ1IjoiamFsYW5ldW5vcyIsImEiOiJja3Y4YWFid3U0Zzl2MzJwNmg5b3RhNmRyIn0.uQZju4_sVA9GdF9lBT4Rgg");
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                MapFragment.this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapboxMap.addOnMapClickListener(MapFragment.this);

                        VectorSource vectorSource = new VectorSource("vector-source", "mapbox://jalaneunos.70mhubzq");

                        style.addSource(vectorSource);

                        CircleLayer circleLayer = new CircleLayer("circle-layer-id", "vector-source");

                        circleLayer.setSourceLayer("trees-everything-1yrdpn");

                        style.addLayer(circleLayer);

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(1.3521, 103.8198)) // set the camera's center position
                                .zoom(10.5f)  // set the camera's zoom level
                                .tilt(20)  // set the camera's tilt
                                .build();
                        // Move the camera to that position
                        mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




                    }

                });
            }
        });
        return view;
    }


    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Bundle args = new Bundle();

        JSONObject root = null;
        try {
            root = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }



        // Convert LatLng coordinates to screen pixel and only query the rendered features.
        final PointF pixel = mapboxMap.getProjection().toScreenLocation(point);

        List<Feature> features = mapboxMap.queryRenderedFeatures(pixel);

        // Get the first feature within the list if one exist
        if (features.size() > 0) {
            Feature feature = features.get(0);

            // Ensure the feature has properties defined
            if (feature.properties() != null) {
                for (Map.Entry<String, JsonElement> entry : feature.properties().entrySet()) {
                    // Log all the properties
                    Log.d(TAG, String.format("%s = %s", entry.getKey(), entry.getValue()));
                    if (entry.getKey().equals("species_id")) {
                        String species_id = String.valueOf(entry.getValue());
                        species_id = species_id.substring(1, species_id.length() - 2);
                        int id = Integer.parseInt(species_id);
                        for (int i = id; i < 33626; i++) {
                            JSONObject o = null;
                            try {
                                o = root.getJSONObject(String.valueOf(i));
                                String name = o.getString("name");
                                String commonName = o.getString("common_name");
                                args.putString("species_name", name);
                                args.putString("common_name", commonName);
                                Log.d(TAG, o.getString("name"));
                                break;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    args.putString(entry.getKey(), String.valueOf(entry.getValue()));
                }
                TreeDialog dialog = new TreeDialog();
                dialog.setArguments(args);
                dialog.setTargetFragment(MapFragment.this, 1);
                dialog.show(getParentFragmentManager(), "TreeDialog");
                return true;
            }


        }
        return false;
    }
}

