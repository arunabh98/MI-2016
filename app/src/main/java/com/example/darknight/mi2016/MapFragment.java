package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        return mapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng convocationHall = new LatLng(19.131973, 72.914285);
        LatLng IITB = new LatLng(19.133180, 72.913458);
        LatLng LTPCSA = new LatLng(19.132348, 72.915785);

        mMap.addMarker(new MarkerOptions().position(convocationHall).title("Convocation Hall"));
        mMap.addMarker(new MarkerOptions().position(LTPCSA).title("PC Saxena Auditorium (LTPCSA)"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(IITB, 16.5f));
    }
}
