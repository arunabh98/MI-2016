package com.iitb.moodindigo.mi2016;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;
import com.iitb.moodindigo.mi2016.ServerConnection.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.iitb.moodindigo.mi2016.R.id.map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, Callback<GsonModels.DistanceMatrix> {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String GOOGLE_API_KEY = "AIzaSyBXDe7lj28A5q8uYwy9uMb3xGielAqsWTE";
    private static double lat = 0.0;
    private static double lon = 0.0;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private GoogleMap mMap;
    private UserLockBottomSheetBehavior mBottomSheetBehavior;
    private FloatingActionButton directionsAndLocationButton;
    private Location currentLocation;
    private Place selectedPlace;
    private TextView titleTextView;
    private TextView distanceTextView;
    private TextView timeTextView;

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
    public void onStart() {
        super.onStart();
        directionsAndLocationButton = (FloatingActionButton) getActivity().findViewById(R.id.directions);
        View bottomSheetView = getView().findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = (UserLockBottomSheetBehavior) UserLockBottomSheetBehavior.from(bottomSheetView);
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(UserLockBottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new UserLockBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == UserLockBottomSheetBehavior.STATE_HIDDEN) {
                    directionsAndLocationButton.setImageResource(R.drawable.ic_my_location_black_24dp);
                    directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorGray), PorterDuff.Mode.SRC_IN);
                    directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        final FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.frame_layout);
        final FloatingActionMenu fabMenu = (FloatingActionMenu) getActivity().findViewById(R.id.fab_menu);
        fabMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fabMenu.isOpened()) {
                    frameLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            fabMenu.toggle(true);
                            frameLayout.setOnTouchListener(null);
                            return true;
                        }
                    });
                }
                fabMenu.toggle(true);
            }
        });
        directionsAndLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() == UserLockBottomSheetBehavior.STATE_HIDDEN) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        return;
                    }
                    currentLocation = getLastKnownLocation();
                    if (currentLocation != null) {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15);
                        mMap.moveCamera(cameraUpdate);
                        mMap.animateCamera(cameraUpdate);
                        directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    if (selectedPlace != null) {
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude + "&mode=w");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        startActivity(mapIntent);
                    }
                }
            }
        });
    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

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

        List<Place> toiletList = new ArrayList<>();
        toiletList.add(new Place(new LatLng(19.12541, 72.909201), "Near Padmavati Devi Temple", "TOILET"));
        toiletList.add(new Place(new LatLng(19.125455, 72.916304), "Main Gate", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130289, 72.915922), "Kresit Building T1", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130688, 72.915843), "Kresit Building T2", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130476, 72.916369), "LCH T1", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130973, 72.91634), "LCH T2", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130968, 72.916986), "LCH T3", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130571, 72.917032), "LCH T4", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130214, 72.916565), "Physics Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130281, 72.917738), "Chemistry Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.130565, 72.917287), "LCH T5", "TOILET"));
        toiletList.add(new Place(new LatLng(19.131034, 72.917275), "LCH T6", "TOILET"));
        toiletList.add(new Place(new LatLng(19.131168, 72.916496), "MEMS Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.13122, 72.91781), "HSS Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.131673, 72.916578), "GG Building", "TOILET"));
        toiletList.add(new Place(new LatLng(19.13188, 72.916441), "Electrical Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.131903, 72.917321), "CTARA Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.132589, 72.91649), "Civil Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.13235, 72.917319), "VMCC", "TOILET"));
        toiletList.add(new Place(new LatLng(19.131741, 72.91582), "SOM", "TOILET"));
        toiletList.add(new Place(new LatLng(19.133325, 72.91644), "Mechanical Department", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135648, 72.913701), "SAC", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135568, 72.912728), "Swimming Pool", "TOILET"));
        toiletList.add(new Place(new LatLng(19.136331, 72.913784), "Hostel 1 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.136575, 72.912584), "Hostel 2 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.136365, 72.911449), "Hostel 3 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135798, 72.911416), "Gymkhana Indoor", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135754, 72.912397), "Gymkhana Badminton", "TOILET"));
        toiletList.add(new Place(new LatLng(19.136492, 72.910099), "Hostel 4 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135555, 72.910177), "Tansa A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135771, 72.909998), "Tansa B", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135187, 72.909738), "Hostel 5 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135584, 72.908264), "Hostel 9 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.134359, 72.907746), "Hostel 7 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135829, 72.907155), "Hostel 6 A", "TOILET"));
        toiletList.add(new Place(new LatLng(19.135463, 72.904687), "Hostel 12 B1", "TOILET"));
        toiletList.add(new Place(new LatLng(19.134532, 72.904711), "Hostel 13 A1", "TOILET"));
        toiletList.add(new Place(new LatLng(19.134768, 72.905867), "Hostel 14 B1", "TOILET"));

        for (Place place : toiletList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_logo)));
            marker.setTag(place);
        }

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(convocationHall, 16.5f));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(this);
        directionsAndLocationButton.performClick();

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
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onPause() {

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        super.onPause();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        selectedPlace = (Place) marker.getTag();
        directionsAndLocationButton.setImageResource(R.drawable.ic_directions_walk_black_24dp);
        directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2962FF")));

        titleTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_title);
        titleTextView.setText(selectedPlace.getTitle());
        currentLocation = getLastKnownLocation();
        RetrofitInterface retrofitInterface = MatrixGenerator.createService(RetrofitInterface.class);
        retrofitInterface.getMatrix(currentLocation.getLatitude() + "," + currentLocation.getLongitude(),
                selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude,
                "walking",
                GOOGLE_API_KEY).enqueue(MapFragment.this);
        mBottomSheetBehavior.setState(UserLockBottomSheetBehavior.STATE_COLLAPSED);
        return true;
    }

    @Override
    public void onResponse(Call<GsonModels.DistanceMatrix> call, Response<GsonModels.DistanceMatrix> response) {
        if (response.isSuccessful()) {
            final GsonModels.DistanceMatrix distanceMatrix = response.body();
            if (distanceMatrix.getStatus().equals("OK")) {
                GsonModels.Element element = distanceMatrix.getRows().get(0).getElements().get(0);
                GsonModels.Distance distance = element.getDistance();
                GsonModels.Duration duration = element.getDuration();
                String status = element.getStatus();

                if (status.equals("OK")) {
                    distanceTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_distance);
                    timeTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_time);
                    distanceTextView.setText(distance.getText());
                    timeTextView.setText(duration.getText());
                } else {
                    Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), distanceMatrix.getStatus(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Response Code:" + String.valueOf(response.code()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<GsonModels.DistanceMatrix> call, Throwable t) {
        Toast.makeText(getContext(), "Network error occurred", Toast.LENGTH_LONG).show();
        Log.d("TAG", "onFailure: " + t.toString());
    }

    @Override
    public void onCameraMove() {
        if (mBottomSheetBehavior.getState() == UserLockBottomSheetBehavior.STATE_HIDDEN) {
            directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorGray), PorterDuff.Mode.SRC_IN);
        }
    }
}
