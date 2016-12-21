package com.iitb.moodindigo.mi2016;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
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
import com.google.android.gms.maps.model.MapStyleOptions;
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
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, Callback<GsonModels.DistanceMatrix>, View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String GOOGLE_API_KEY = "AIzaSyCcD-MXdPrnYbDId8qSrij6EClfkJuJNE4";
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
    private FloatingActionMenu fabMenu;
    private com.github.clans.fab.FloatingActionButton fabEvents;
    private com.github.clans.fab.FloatingActionButton fabEateries;
    private com.github.clans.fab.FloatingActionButton fabAccomodation;
    private com.github.clans.fab.FloatingActionButton fabToilets;
    private List<Place> eventList = new ArrayList<>();
    private List<Place> eateriesList = new ArrayList<>();
    private List<Place> accomodationList = new ArrayList<>();
    private List<Place> toiletList = new ArrayList<>();
    private GsonModels.Event launchEvent;

    public MapFragment(GsonModels.Event launchEvent) {
        this.launchEvent = launchEvent;
    }

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
        if (getView() != null) {
            View bottomSheetView = getView().findViewById(R.id.bottom_sheet);
            mBottomSheetBehavior = (UserLockBottomSheetBehavior) UserLockBottomSheetBehavior.from(bottomSheetView);
        }
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
        fabMenu = (FloatingActionMenu) getActivity().findViewById(R.id.fab_menu);
        fabMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabMenu.isOpened()) {
                    displayAll();
                }
                fabMenu.toggle(true);
            }
        });
        createCustomAnimation();

        fabEvents = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_event);
        fabEateries = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_eateries);
        fabAccomodation = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_accomodation);
        fabToilets = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_toilets);

        fabEvents.setOnClickListener(this);
        fabEateries.setOnClickListener(this);
        fabAccomodation.setOnClickListener(this);
        fabToilets.setOnClickListener(this);

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
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17);
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
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style);
        mMap.setMapStyle(style);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (fabMenu.isOpened()) {
                    fabMenu.toggle(true);
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        if (directionsAndLocationButton != null) {
            directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        eventList.add(new Place(new LatLng(19.131973, 72.914285), "Convocation Hall"));
        eventList.add(new Place(new LatLng(19.130735, 72.916900), "Lecture Hall Complex (LCH)"));
        eventList.add(new Place(new LatLng(19.132348, 72.915785), "LT PCSA"));
        eventList.add(new Place(new LatLng(19.134446, 72.912217), "Gymkhana Grounds"));
        eventList.add(new Place(new LatLng(19.133572, 72.913399), "NCC Grounds"));
        eventList.add(new Place(new LatLng(19.130480, 72.915724), "FC Kohli Auditorium (FCK)"));
        eventList.add(new Place(new LatLng(19.135574, 72.912930), "New Swimming Pool"));
        eventList.add(new Place(new LatLng(19.129113, 72.918408), "Kendriya Vidyalaya (KV)"));
        eventList.add(new Place(new LatLng(19.135422, 72.913674), "Student Activity Center (SAC)"));
        eventList.add(new Place(new LatLng(19.131651, 72.915758), "SJM SOM"));
        eventList.add(new Place(new LatLng(19.135045, 72.913401), "Open Air Theatre (OAT)"));
        eventList.add(new Place(new LatLng(19.132635, 72.915716), "MB Lawns"));
        eventList.add(new Place(new LatLng(19.130005, 72.916704), "Physics Parking Lot"));
        eventList.add(new Place(new LatLng(19.129574, 72.915394), "H10 T-Point"));
        eventList.add(new Place(new LatLng(19.132030, 72.915920), "PCSA Backlawns"));
        eventList.add(new Place(new LatLng(19.135771, 72.914428), "SAC Parking Lot"));
        eventList.add(new Place(new LatLng(19.134779, 72.912952), "SAC Backyard"));
        eventList.add(new Place(new LatLng(19.135572, 72.914017), "Old Swimming Pool"));

        accomodationList.add(new Place(new LatLng(19.136110, 72.913904), "Hostel 1"));
        accomodationList.add(new Place(new LatLng(19.136357, 72.912555), "Hostel 2"));
        accomodationList.add(new Place(new LatLng(19.136231, 72.911482), "Hostel 3"));
        accomodationList.add(new Place(new LatLng(19.136142, 72.910456), "Hostel 4"));
        accomodationList.add(new Place(new LatLng(19.135235, 72.910237), "Hostel 5"));
        accomodationList.add(new Place(new LatLng(19.135361, 72.907071), "Hostel 6"));
        accomodationList.add(new Place(new LatLng(19.134910, 72.908062), "Hostel 7"));
        accomodationList.add(new Place(new LatLng(19.133758, 72.911147), "Hostel 8"));
        accomodationList.add(new Place(new LatLng(19.134966, 72.908400), "Hostel 9"));
        accomodationList.add(new Place(new LatLng(19.128322, 72.915731), "Hostel 10"));
        accomodationList.add(new Place(new LatLng(19.133495, 72.912053), "Hostel 11"));
        accomodationList.add(new Place(new LatLng(19.134811, 72.905217), "Hostel 12, 13, 14"));
        accomodationList.add(new Place(new LatLng(19.137539, 72.914096), "Hostel 15"));
        accomodationList.add(new Place(new LatLng(19.137731, 72.912796), "Hostel 16"));
        accomodationList.add(new Place(new LatLng(19.135883, 72.910248), "Tansa House"));
        accomodationList.add(new Place(new LatLng(19.135305, 72.913685), "Hospitality Desk"));

        eateriesList.add(new Place(new LatLng(19.134599, 72.910057), "The Campus Hub"));
        eateriesList.add(new Place(new LatLng(19.133698, 72.911471), "Brews & Bites"));
        eateriesList.add(new Place(new LatLng(19.129769, 72.915152), "Gulmohar"));
        eateriesList.add(new Place(new LatLng(19.134865, 72.913824), "SAC Food Court"));
        eateriesList.add(new Place(new LatLng(19.129861, 72.915798), "Day Food Court"));
        eateriesList.add(new Place(new LatLng(19.134377, 72.910707), "Concert Lounge"));
        eateriesList.add(new Place(new LatLng(19.135148, 72.905608), "Amul Parlour"));
        eateriesList.add(new Place(new LatLng(19.134948, 72.905138), "H12 Night Canteen"));
        eateriesList.add(new Place(new LatLng(19.131994, 72.915479), "Coffee Shack"));

        toiletList.add(new Place(new LatLng(19.12541, 72.909201), "Near Padmavati Devi Temple"));
        toiletList.add(new Place(new LatLng(19.125455, 72.916304), "Main Gate"));
        toiletList.add(new Place(new LatLng(19.130289, 72.915922), "Kresit Building T1"));
        toiletList.add(new Place(new LatLng(19.130688, 72.915843), "Kresit Building T2"));
        toiletList.add(new Place(new LatLng(19.130476, 72.916369), "LCH T1"));
        toiletList.add(new Place(new LatLng(19.130973, 72.91634), "LCH T2"));
        toiletList.add(new Place(new LatLng(19.130968, 72.916986), "LCH T3"));
        toiletList.add(new Place(new LatLng(19.130571, 72.917032), "LCH T4"));
        toiletList.add(new Place(new LatLng(19.130214, 72.916565), "Physics Department"));
        toiletList.add(new Place(new LatLng(19.130281, 72.917738), "Chemistry Department"));
        toiletList.add(new Place(new LatLng(19.130565, 72.917287), "LCH T5"));
        toiletList.add(new Place(new LatLng(19.131034, 72.917275), "LCH T6"));
        toiletList.add(new Place(new LatLng(19.131168, 72.916496), "MEMS Department"));
        toiletList.add(new Place(new LatLng(19.13122, 72.91781), "HSS Department"));
        toiletList.add(new Place(new LatLng(19.131673, 72.916578), "GG Building"));
        toiletList.add(new Place(new LatLng(19.13188, 72.916441), "Electrical Department"));
        toiletList.add(new Place(new LatLng(19.131903, 72.917321), "CTARA Department"));
        toiletList.add(new Place(new LatLng(19.132589, 72.91649), "Civil Department"));
        toiletList.add(new Place(new LatLng(19.13235, 72.917319), "VMCC"));
        toiletList.add(new Place(new LatLng(19.131741, 72.91582), "SOM"));
        toiletList.add(new Place(new LatLng(19.133325, 72.91644), "Mechanical Department"));
        toiletList.add(new Place(new LatLng(19.135648, 72.913701), "SAC"));
        toiletList.add(new Place(new LatLng(19.135568, 72.912728), "Swimming Pool"));
        toiletList.add(new Place(new LatLng(19.136331, 72.913784), "Hostel 1 A"));
        toiletList.add(new Place(new LatLng(19.136575, 72.912584), "Hostel 2 A"));
        toiletList.add(new Place(new LatLng(19.136365, 72.911449), "Hostel 3 A"));
        toiletList.add(new Place(new LatLng(19.135798, 72.911416), "Gymkhana Indoor"));
        toiletList.add(new Place(new LatLng(19.135754, 72.912397), "Gymkhana Badminton"));
        toiletList.add(new Place(new LatLng(19.136492, 72.910099), "Hostel 4 A"));
        toiletList.add(new Place(new LatLng(19.135555, 72.910177), "Tansa A"));
        toiletList.add(new Place(new LatLng(19.135771, 72.909998), "Tansa B"));
        toiletList.add(new Place(new LatLng(19.135187, 72.909738), "Hostel 5 A"));
        toiletList.add(new Place(new LatLng(19.135584, 72.908264), "Hostel 9 A"));
        toiletList.add(new Place(new LatLng(19.134359, 72.907746), "Hostel 7 A"));
        toiletList.add(new Place(new LatLng(19.135829, 72.907155), "Hostel 6 A"));
        toiletList.add(new Place(new LatLng(19.135463, 72.904687), "Hostel 12 B1"));
        toiletList.add(new Place(new LatLng(19.134532, 72.904711), "Hostel 13 A1"));
        toiletList.add(new Place(new LatLng(19.134768, 72.905867), "Hostel 14 B1"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.131973, 72.914285), 17));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(this);
        if (directionsAndLocationButton != null) {
            directionsAndLocationButton.performClick();
        }

        displayAll();

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

        if (launchEvent != null) {
            launchEventOnMap(launchEvent);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        if (launchEvent == null) {
            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }

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
        distanceTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_distance);
        timeTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_time);
        if (distanceTextView != null) {
            distanceTextView.setText(null);
        }
        if (timeTextView != null) {
            timeTextView.setText(null);
        }
        directionsAndLocationButton.setImageResource(R.drawable.ic_directions_walk_black_24dp);
        directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2962FF")));

        titleTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_title);
        titleTextView.setText(selectedPlace.getTitle());
        currentLocation = getLastKnownLocation();
        RetrofitInterface retrofitInterface = MatrixGenerator.createService(RetrofitInterface.class);
        if (currentLocation != null) {
            retrofitInterface.getMatrix(currentLocation.getLatitude() + "," + currentLocation.getLongitude(),
                    selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude,
                    "walking").enqueue(MapFragment.this);
        }
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
                    if (distanceTextView != null) {
                        distanceTextView.setText(distance.getText());
                    }
                    if (timeTextView != null) {
                        timeTextView.setText(duration.getText());
                    }
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

    @Override
    public void onClick(View v) {
        mMap.clear();
        List<Place> placeList = new ArrayList<>();
        int iconDrawable = 0;
        switch (v.getId()) {
            case R.id.fab_event:
                placeList = eventList;
                iconDrawable = R.drawable.ic_local_play_white_24dp;
                break;
            case R.id.fab_eateries:
                placeList = eateriesList;
                iconDrawable = R.drawable.ic_local_dining_white_24dp;
                break;
            case R.id.fab_accomodation:
                placeList = accomodationList;
                iconDrawable = R.drawable.ic_hotel_white_24dp;
                break;
            case R.id.fab_toilets:
                placeList = toiletList;
                iconDrawable = R.drawable.ic_toilet_white_24dp;
                break;
        }
        for (Place place : placeList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(iconDrawable)).anchor(0.5f, 0.5f));
            marker.setTag(place);
        }
        fabMenu.close(true);
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                        ? R.drawable.ic_search_white_24dp : R.drawable.ic_close_white_24dp);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabMenu.setIconToggleAnimatorSet(set);
    }

    public void displayAll() {
        mMap.clear();
        final float half = (float) 0.5;
        for (Place place : eventList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_play_white_24dp)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : eateriesList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_dining_white_24dp)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : accomodationList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hotel_white_24dp)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : toiletList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_toilet_white_24dp)).anchor(half, half));
            marker.setTag(place);
        }
    }

    public void launchEventOnMap(GsonModels.Event event) {
        Place place = null;
        switch (event.getMap_loc()) {
            case "PCSA":
                place = new Place(new LatLng(19.132348, 72.915785), "LT PCSA");
                break;
            case "lch":
                place = new Place(new LatLng(19.130735, 72.916900), "Lecture Hall Complex (LCH)");
                break;
            case "Convo":
                place = new Place(new LatLng(19.131973, 72.914285), "Convocation Hall");
                break;
            case "SAC parking lot":
                place = new Place(new LatLng(19.135771, 72.914428), "SAC Parking Lot");
                break;
            case "fck":
                place = new Place(new LatLng(19.130480, 72.915724), "FC Kohli Auditorium (FCK)");
                break;
            case "NCC":
                place = new Place(new LatLng(19.133572, 72.913399), "NCC Grounds");
                break;
            case "OSP":
                place = new Place(new LatLng(19.135572, 72.914017), "Old Swimming Pool");
                break;
            case "KV":
                place = new Place(new LatLng(19.129113, 72.918408), "Kendriya Vidyalaya (KV)");
                break;
            case "SOM":
                place = new Place(new LatLng(19.131651, 72.915758), "SJM SOM");
                break;
            case "h 10 t point":
                place = new Place(new LatLng(19.129574, 72.915394), "H10 T-Point");
                break;
            case "PCSA back lawns":
                place = new Place(new LatLng(19.132030, 72.915920), "PCSA Backlawns");
                break;
            case "MB lawns":
                place = new Place(new LatLng(19.132635, 72.915716), "MB Lawns");
                break;
            case "OAT":
                place = new Place(new LatLng(19.135045, 72.913401), "Open Air Theatre (OAT)");
                break;
            case "liby road":
                place = new Place(new LatLng(19.134299, 72.915376), "Library Road");
                break;
            case "ppl":
                place = new Place(new LatLng(19.130005, 72.916704), "Physics Parking Lot");
                break;
            case "Gymkhana":
                place = new Place(new LatLng(19.134446, 72.912217), "Gymkhana Grounds");
                break;
        }
        if (place != null) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
            marker.setTag(place);
            selectedPlace = (Place) marker.getTag();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 17));
            directionsAndLocationButton.setImageResource(R.drawable.ic_directions_walk_black_24dp);
            directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2962FF")));

            titleTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_title);
            titleTextView.setText(selectedPlace.getTitle());
            currentLocation = getLastKnownLocation();
            mBottomSheetBehavior.setState(UserLockBottomSheetBehavior.STATE_COLLAPSED);
            RetrofitInterface retrofitInterface = MatrixGenerator.createService(RetrofitInterface.class);
            if (currentLocation != null) {
                retrofitInterface.getMatrix(currentLocation.getLatitude() + "," + currentLocation.getLongitude(),
                        selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude,
                        "walking").enqueue(MapFragment.this);
            }
        }
    }

    public GsonModels.Event getLaunchEvent() {
        return launchEvent;
    }
}
