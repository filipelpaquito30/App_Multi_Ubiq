package com.example.amu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.widget.Button;

import static java.lang.String.valueOf;

public class MapsActivity2 extends AppCompatActivity
        implements OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private EditText mSearchText;

    private LocationManager locationManager;

    private float[] dist = new float[1];

    private boolean isBet;

    private LocationListener locListener;

    public static LatLng destLocation;

    public ArrayMap<String,Float> list = new ArrayMap<String, Float>();

    TextView tv;
    TextView tv2;
    TextView tv3;

    public static double currLat = 0;
    public static double currLong = 0;
    private int k;

    Button backButton;

    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearchText = (EditText) findViewById(R.id.input_search);

        tv = (TextView) findViewById(R.id.coord);
        tv2 = (TextView) findViewById(R.id.isIn);
        tv3 = (TextView) findViewById(R.id.speed);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(1);
            }
        });

    }

    public void BackgroundLocationService() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Criteria crt = new Criteria();
        crt.setAccuracy(Criteria.ACCURACY_FINE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    public boolean isDistanceBetween() {

        Location.distanceBetween(currLat,currLong,destLocation.latitude,destLocation.longitude,dist);

        if(dist[0] > 1000){
            return false;
        }
        return true;
    }

    /*
    private void change(View view) {
        Intent intent = new Intent(this, PostMap.class);
        startActivity(intent);
    }*/


    private void init()
    {
        Log.d("MapActivity", "initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == event.ACTION_DOWN
                        || event.getAction() == event.KEYCODE_ENTER) {
                    geoLocate();
                }

                return false;
            }
        });
    }



    private void goToLocationZoom(double lat, double lng, float zoom)
    {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }


    @Override
    public void onMapReady(final GoogleMap map)
    {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        // Setting a click event handler for the map
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Destino: " + latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                map.clear();

                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                map.addMarker(markerOptions);

                destLocation = latLng;

            }
        });

        init();

        BackgroundLocationService();

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    public void geoLocate() {
        Log.d("MapActivity", "geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity2.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e("MapActivity", "geolocate: IOException: " + e.getMessage() );
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            goToLocationZoom(lat, lng, 14);

            Log.d("MapActivity", "geoLocate: found a location: " + address.toString());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        currLat = latitude;
        currLong = longitude;
        tv.setText(currLat + "," + currLong);
        tv3.setText(valueOf(location.getSpeed()));

        if (destLocation != null) {
            isBet = isDistanceBetween();
            if (isBet == true) {
                tv2.setText("Já tás perto, toca a estacionar!");
            }else{
                tv2.setText("Ainda estás longe, anda rápido!");
            }
        }
        if (k < 15) {
            k++;
        } else {
            list.put(Calendar.getInstance().getTime().toString(),location.getSpeed());
            k=0;
        }
        //Circle circle = mMap.addCircle(new CircleOptions().center(new LatLng(currLat, currLong)).radius(1000).strokeColor(Color.BLUE));
        //circle.setVisible(true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void openExperience(int ExpId){
        Intent intent = new Intent();
        switch (ExpId) {
            case 1:
                intent = new Intent(this, Exp2_2.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}

