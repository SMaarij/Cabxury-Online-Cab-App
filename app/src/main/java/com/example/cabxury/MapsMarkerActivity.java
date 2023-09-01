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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private Polyline currentPolyline;
    String url = "https://cabxury.000webhostapp.com/user_ride.php";


    SupportMapFragment mapFragment;
    SearchView searchView;
    GoogleMap map;
    private LatLng currentLocation;
    private LatLng destinationLocation;
    public String destination;
    private double fare;
    public String address1;
    private double distance;
    public String Sdistance;
    public String Sfare;
    public String uemail;
    public float fdistance;
    public float ffare;
    public String fsdistance;
    public String fsfare;

    @SuppressLint("NewApi")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_drivers_maps);
        searchView=findViewById(R.id.search_view);
        uemail= getIntent().getStringExtra("uemail");

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList=null;

                if(location!=null || !location.equals("")){
                    Geocoder geocoder= new Geocoder((MapsMarkerActivity.this));
                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address=addressList.get(0);
                    LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latlng).title(location));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


    }


    public String getAddress(double Lat,double Lon){

        address1="";
        Geocoder geocoder1= new Geocoder(MapsMarkerActivity.this, Locale.getDefault());

        try{
            List<Address> addresses;
            addresses = geocoder1.getFromLocation(Lat,Lon,1);
            if(address1!=null){
                Address returnAddress=addresses.get(0);
                StringBuilder stringBuilderReturnAddress=new StringBuilder("");

                for (int i=0; i<=returnAddress.getMaxAddressLineIndex();i++){

                    stringBuilderReturnAddress.append(returnAddress.getAddressLine(i)).append("\n");
                }
                address1=stringBuilderReturnAddress.toString();
            }
            else{
                Toast.makeText(MapsMarkerActivity.this,"Address not found",Toast.LENGTH_SHORT).show();
            }
        }

        catch (Exception exe){
            Toast.makeText(MapsMarkerActivity.this, exe.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }

        return address1;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        List<Marker> markerList = new ArrayList<>();
        LatLng southwest = new LatLng(24.78, 66.74);
        LatLng northeast = new LatLng(25.18, 67.39);
        LatLngBounds karachiBounds = new LatLngBounds(southwest, northeast);
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            double lat = random.nextDouble() * (karachiBounds.northeast.latitude - karachiBounds.southwest.latitude) + karachiBounds.southwest.latitude;
            double lng = random.nextDouble() * (karachiBounds.northeast.longitude - karachiBounds.southwest.longitude) + karachiBounds.southwest.longitude;
            LatLng position = new LatLng(lat, lng);

            MarkerOptions markerOptions = new MarkerOptions().position(position).title("Driver here " + (i + 1));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon13);
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
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            double latitude=location.getLatitude();
                            double longitude=location.getLongitude();
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title(getAddress(latitude,longitude)));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10));
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    String location = searchView.getQuery().toString();
                                    destination=searchView.getQuery().toString();;
                                    List<Address> addressList=null;

                                    if(location!=null || !location.equals("")){
                                        Geocoder geocoder= new Geocoder((MapsMarkerActivity.this));
                                        try {
                                            addressList=geocoder.getFromLocationName(location,1);
                                        }
                                        catch (IOException e){
                                            e.printStackTrace();
                                        }
                                        Address address=addressList.get(0);

                                        LatLng latlng=new LatLng(address.getLatitude(),address.getLongitude());
                                        map.addMarker(new MarkerOptions().position(latlng).title(location));
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                                        LatLng destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude()); // Replace with the destination's latitude and longitude

                                        PolylineOptions polylineOptions = new PolylineOptions()
                                                .add(currentLocation, destinationLatLng)
                                                .width(5)
                                                .color(Color.RED);
                                        currentPolyline = map.addPolyline(polylineOptions);
                                        // Calculate the distance between the current location and destination location
                                        distance = SphericalUtil.computeDistanceBetween(currentLocation, destinationLatLng) / 1000.0;

                                        // Calculate the fare based on the distance
                                        if (distance <= 5) {
                                            fare = distance*80.0;
                                        } else if (distance <= 10 && distance>5) {
                                            fare = distance*70.0;
                                        } else if (distance <= 15 && distance>10) {
                                            fare = distance*60.0;
                                        } else {
                                            fare = distance*50.0;
                                        }
                                        Sdistance=Double.toString(distance);
                                        Sfare=Double.toString(fare);

                                        fdistance=(float)distance;
                                        ffare=(float)fare;

                                        fsdistance=String.valueOf(fdistance);
                                        fsfare=String.valueOf(ffare);

                                    }
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    return false;
                                }
                            });
                        }
                    }
                });


        Button button = findViewById(R.id.BookCab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest rq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MapsMarkerActivity.this, "Booking your cab", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(MapsMarkerActivity.this, BillingActivity.class);
                        myIntent.putExtra("uemail",uemail);
                        myIntent.putExtra("Sfare",fsfare);
                        myIntent.putExtra("Sdistance",fsdistance);//create new intent
                        startActivity(myIntent); //start the new activity
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsMarkerActivity.this, "No cab availabe", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        //return super.getParams();
                        Map para = new HashMap();

                        para.put("destination", destination);
                        para.put("currentLocation", address1);
                        para.put("fare", fsfare);
                        para.put("distance", fsdistance);
                        para.put("uemail", uemail);

                        return para;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MapsMarkerActivity.this);
                requestQueue.add(rq);
            }
        });



    }

}