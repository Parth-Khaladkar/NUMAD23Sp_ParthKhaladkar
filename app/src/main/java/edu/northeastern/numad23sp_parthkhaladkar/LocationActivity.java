package edu.northeastern.numad23sp_parthkhaladkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationClient;
    TextView latdisp, longdisp, distdip;

    private double pastlat;
    private double pastlong;
    private double dist = 0.;
    private boolean startCalDistance = false;

    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latdisp = (TextView) findViewById(R.id.latitudeText);
        longdisp = (TextView) findViewById(R.id.longitudeText);
        distdip = (TextView) findViewById(R.id.distance);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        location_ping();
    }

    public void distance_set_zero(View view)
    {
        dist = 0.;
        refresh_distance();
    }

    private void refresh_distance() {
        distdip.setText("Distance Travelled is: " + String.valueOf(dist));
    }

    private void location_ping() {
        if (permission_checker())
        {
            if (is_location_enabled())
            {
                requestNewLocationData();
            }
            else
            {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent i1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i1);
            }
        }
        else
        {
            ask_permission();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();

            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();

            latdisp.setText("Latitude is : " + latitude);
            longdisp.setText("Longitude is : " + longitude);
            if (startCalDistance)
            {
                dist += math_helper(pastlat, latitude, pastlong, longitude);
                refresh_distance();
            }
            else
            {
                startCalDistance = true;
            }
            pastlat = latitude;
            pastlong = longitude;
        }
    };

    private double math_helper(double x1, double x2, double y1, double y2)
    {

        final int radius = 6371; // Radius of the earth

        double latDistance = Math.toRadians(x2 - x1);
        double lonDistance = Math.toRadians(y2 - y1);
        double temp = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double temp2 = 2 * Math.atan2(Math.sqrt(temp), Math.sqrt(1 - temp));
        double calc_distance = radius * temp2 * 1000;
        calc_distance = Math.pow(calc_distance, 2);
        return Math.sqrt(calc_distance);
    }

    private void ask_permission()
    {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }



    private boolean permission_checker()
    {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }



    private boolean is_location_enabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void onResume() {
        super.onResume();
        if (permission_checker()) {
            location_ping();
        }
    }




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dist = savedInstanceState.getDouble("distance", 0.);
        startCalDistance = savedInstanceState.getBoolean("startCalDistance", false);
        pastlat = savedInstanceState.getDouble("lastLatitude");
        pastlong = savedInstanceState.getDouble("lastLongitude");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("distance", dist);
        outState.putBoolean("startCalDistance", startCalDistance);
        outState.putDouble("lastLatitude", pastlat);
        outState.putDouble("lastLongitude", pastlong);
        super.onSaveInstanceState(outState);
    }
}