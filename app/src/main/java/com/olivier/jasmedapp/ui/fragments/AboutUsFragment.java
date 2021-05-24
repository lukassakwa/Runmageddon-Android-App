package com.olivier.jasmedapp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.ui.mapview.CustomMapView;
import org.jetbrains.annotations.NotNull;

public class AboutUsFragment extends Fragment implements OnMapReadyCallback {

    private CustomMapView mapView;
    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_us_fragment ,container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //important
        mapView.getMapAsync(this);

        mapView.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //TODO:: set permission !!!!!!!!!!!
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.mGoogleMap = googleMap;

        // For showing a move to my location button
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map
        LatLng wroclaw = new LatLng(51.1078, 17.0385);
        mGoogleMap.addMarker(new MarkerOptions().position(wroclaw).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(wroclaw).zoom(12).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 15));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
