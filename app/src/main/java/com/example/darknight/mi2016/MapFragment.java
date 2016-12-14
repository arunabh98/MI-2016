package com.example.darknight.mi2016;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.darknight.mi2016.R.id.map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static double lat = 0.0;
    private static double lon = 0.0;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private GoogleMap mMap;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(map));
        mapFragment.getMapAsync(this);
        getActivity().setTitle("Map");
        return mapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        LatLng convocationHall = new LatLng(19.131973, 72.914285);
        LatLng gymkhanaGround = new LatLng(19.134446, 72.912217);
        LatLng lectureHall1 = new LatLng(19.130735, 72.916900);
        LatLng som = new LatLng(19.131651, 72.915758);
        LatLng ncc = new LatLng(19.133572, 72.913399);
        LatLng sac = new LatLng(19.135422, 72.913674);
        LatLng fck = new LatLng(19.130480, 72.915724);
        LatLng nsp = new LatLng(19.135574, 72.912930);
        LatLng kv = new LatLng(19.129113, 72.918408);
        LatLng LTPCSA = new LatLng(19.132348, 72.915785);
        LatLng openAirTheatre = new LatLng(19.135045, 72.913401);
        LatLng mbLawns = new LatLng(19.132635, 72.915716);
        LatLng ppl = new LatLng(19.130005, 72.916704);
        LatLng h10TPoint = new LatLng(19.129574, 72.915394);
        LatLng pcsaBackLawns = new LatLng(19.132030, 72.915920);
        LatLng sacParking = new LatLng(19.135771, 72.914428);
        LatLng sacBackyard = new LatLng(19.134779, 72.912952);
        LatLng osp = new LatLng(19.135572, 72.914017);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(convocationHall));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));

        mMap.addMarker(new MarkerOptions().position(convocationHall).title("Convocation Hall"));
        mMap.addMarker(new MarkerOptions().position(lectureHall1).title("Lecture Hall Complex (LCH)"));
        mMap.addMarker(new MarkerOptions().position(LTPCSA).title("PC Saxena Auditorium (LT PCSA)"));
        mMap.addMarker(new MarkerOptions().position(gymkhanaGround).title("Gymkhana Grounds"));
        mMap.addMarker(new MarkerOptions().position(ncc).title("NCC Grounds"));
        mMap.addMarker(new MarkerOptions().position(fck).title("FC Kohli Auditorium (FCK)"));
        mMap.addMarker(new MarkerOptions().position(nsp).title("New Swimming Pool"));
        mMap.addMarker(new MarkerOptions().position(kv).title("Kendriya Vidyalaya (KV)"));
        mMap.addMarker(new MarkerOptions().position(sac).title("Student Activity Center (SAC)"));
        mMap.addMarker(new MarkerOptions().position(som).title("Shailesh J. Mehta School of Management (SOM)"));
        mMap.addMarker(new MarkerOptions().position(openAirTheatre).title("Open Air Theatre (OAT)"));
        mMap.addMarker(new MarkerOptions().position(mbLawns).title("MB Lawns"));
        mMap.addMarker(new MarkerOptions().position(ppl).title("Physics Parking Lot"));
        mMap.addMarker(new MarkerOptions().position(h10TPoint).title("H10 T-Point"));
        mMap.addMarker(new MarkerOptions().position(pcsaBackLawns).title("PCSA Backlawns"));
        mMap.addMarker(new MarkerOptions().position(sacParking).title("SAC Parking Lot"));
        mMap.addMarker(new MarkerOptions().position(sacBackyard).title("SAC Backyard"));
        mMap.addMarker(new MarkerOptions().position(osp).title("Old Swimming Pool"));

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
