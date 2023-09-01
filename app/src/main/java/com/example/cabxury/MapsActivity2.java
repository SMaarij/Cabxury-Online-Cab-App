package com.example.cabxury;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabxury.databinding.ActivityMaps2Binding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.maps.*;

import java.util.Random;


public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Polyline currentPolyline;
    String url = "https://cabxury.000webhostapp.com/driver_ride.php";


    SupportMapFragment mapFragment;
    SearchView searchView;
    private LatLng currentLocation;
    private LatLng destinationLocation;
    public String destination;
    private double fare;
    public String address1;
    private double distance;
    public String Sdistance;
    public String Sfare;
    public String demail;

    @SuppressLint("NewApi")
    private ActivityMaps2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps2);

        demail = getIntent().getStringExtra("demail");

        final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }


        Button button = findViewById(R.id.BookaCab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity2.this, "Accepting ride", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MapsActivity2.this, Customerconfirmation.class); //create new intent
                myIntent.putExtra("demail",demail);
                startActivity(myIntent); //start the new activity
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        List<Marker> markerList = new ArrayList<>();
        LatLng southwest = new LatLng(24.78, 66.74);
        LatLng northeast = new LatLng(25.18, 67.39);
        LatLngBounds karachiBounds = new LatLngBounds(southwest, northeast);
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            double lat = random.nextDouble() * (karachiBounds.northeast.latitude - karachiBounds.southwest.latitude) + karachiBounds.southwest.latitude;
            double lng = random.nextDouble() * (karachiBounds.northeast.longitude - karachiBounds.southwest.longitude) + karachiBounds.southwest.longitude;
            LatLng position = new LatLng(lat, lng);

            MarkerOptions markerOptions = new MarkerOptions().position(position).title("Customer here " + (i + 1));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon12);
            markerOptions.icon(bitmapDescriptor);
            Marker marker = map.addMarker(markerOptions);
            markerList.add(marker);



        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Use the location object to get the current latitude and longitude
                            double currentLatitude = location.getLatitude();
                            double currentLongitude = location.getLongitude();

                            // Create a LatLng object from the current latitude and longitude
                            currentLocation = new LatLng(currentLatitude, currentLongitude);
                            map.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));

                            // Move the camera to the current location
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
                        }
                    }
                });

    }
}