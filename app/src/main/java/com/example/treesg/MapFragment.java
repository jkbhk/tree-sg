package com.example.treesg;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.core.utilities.Tree;
import com.google.gson.JsonElement;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
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
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;


public class MapFragment extends Fragment implements MapboxMap.OnMapClickListener, PermissionsListener {

    private static final String TAG = "123";
    private MapView mapView;
    private MapboxMap mapboxMap;
    static StringBuilder builder = new StringBuilder();
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;


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
        Mapbox.getInstance(getContext().getApplicationContext(), "pk.eyJ1IjoiamFsYW5ldW5vcyIsImEiOiJja3Y4YWFid3U0Zzl2MzJwNmg5b3RhNmRyIn0.uQZju4_sVA9GdF9lBT4Rgg");
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

                        enableLocationComponent(style);

                        CameraPosition userLocation = mapboxMap.getCameraPosition();


                        CameraPosition startPosition  = new CameraPosition.Builder()
                                .target(new LatLng(1.3485, 103.7156))
                                .zoom(6f)
                                .build();

                        mapboxMap.setCameraPosition(startPosition);


                        CameraPosition zoomedPosition  = new CameraPosition.Builder()
                                .target(new LatLng(1.3485, 103.7156))
                                .zoom(17f)
                                .build();

                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(zoomedPosition), 7000);

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
                int j = 0;
                for (Map.Entry<String, JsonElement> entry : feature.properties().entrySet()) {
                    j += 1;
                    // Log all the properties
                    Log.d(TAG, String.format("%s = %s", entry.getKey(), entry.getValue()));
                    Treedebugger.log(String.valueOf(entry.getKey()) + String.valueOf(entry.getValue()));

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
                if (j == 10) {
                    TreeDialog dialog = new TreeDialog();
                    dialog.setArguments(args);
                    dialog.setTargetFragment(MapFragment.this, 1);
                    dialog.show(getParentFragmentManager(), "TreeDialog");
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {




        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getContext())
                    .elevation(5)
                    .pulseEnabled(true)
                    .pulseAlpha(Color.BLUE)
                    .pulseMaxRadius(200)
                    .accuracyAlpha(.0f)
                    .build();



            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

        // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getContext(), loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .useDefaultLocationEngine(true)
                            .build());


        // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);




        // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);






        // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);








        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }
}

