package com.example.muhwezidenisliam.safeneighborhoodalpha.location;

/**
 * Created by Muhwezi Denis Liam on 9/14/2017.
 */

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.muhwezidenisliam.safeneighborhoodalpha.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import im.delight.android.location.SimpleLocation;

public class MyLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float zoom = 18;

    private SimpleLocation simpleLocation;

    double current_longitude;

    double current_latitude;

    String key;

    Double value;

    private String[] colors = {"#7fff7272", "#7f31c7c5", "#7fff8a00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        simpleLocation = new SimpleLocation(this);

        // if we can't access the location yet
        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }



        mapFragment.getMapAsync(this);

        // alternative A
        /*location.calculateDistance(startLatitude, startLongitude, endLatitude, endLongitude);

        // alternative B
        location.calculateDistance(startPoint, endPoint);*/

        //location.setBlurRadius(10000);

       /*current_latitude = location.getLatitude();
       current_longitude = location.getLongitude();*/



    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        mMap.setMyLocationEnabled(true);

        mMap.setTrafficEnabled(true);


        current_latitude = simpleLocation.getLatitude();
        current_longitude = simpleLocation.getLongitude();
        Log.d("lat=>=long",simpleLocation.getLatitude()+" =>= "+simpleLocation.getLongitude());

        final HashMap<String, Double> smallest_distance = new HashMap<>();

        String locate = String.valueOf(current_latitude)+","+String.valueOf(current_longitude);
        String place = "hospital|police";

        HashMap<String,String> send_url =new HashMap<>();
        send_url.put("location",locate);
        send_url.put("place",place);

        PostResponseAsyncTask urlTask = new PostResponseAsyncTask(MyLocation.this, send_url, false,new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d("talk to me", s);

                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    final JSONArray jsonArray = jsonResponse.getJSONArray("results");

                    for (int index = 0; index < jsonArray.length(); index++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(index);

                        String title= jsonObject.getString("name");

                        String thumbnail= jsonObject.getString("icon");

                        String lng = jsonObject.getJSONObject("geometry")
                                .getJSONObject("location")
                                .getString("lng");

                        String lat = jsonObject.getJSONObject("geometry")
                                .getJSONObject("location")
                                .getString("lat");

                        Double distance = simpleLocation.calculateDistance(current_latitude,current_longitude,Double.valueOf(lat),Double.valueOf(lng));

                        smallest_distance.put(lat+","+lng,distance);

                        Log.d("distance","===>==="+distance+" Meters");

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.valueOf(lat),Double.valueOf(lng))).title(title);
                        mMap.addMarker(marker);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }


        });


        urlTask.execute("http://welshuganda.com/mobipay/neighborhood/places.php");

        Map<String,Double> sortedDistance = sortByValue(smallest_distance);

        for (Map.Entry<String, Double> entry : sortedDistance.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            break;
        }

//        final String split[] = key.split(",");

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(final Location location) {
                // TODO Auto-generated method stub


                GoogleDirection.withServerKey("AIzaSyCcG9m-qjHsRzdqOniwIADKaMRbbwo4L5U")
                        .from(new LatLng(location.getLatitude(),location.getLongitude()))
//                        .to(new LatLng(Double.valueOf(split[0]),Double.valueOf(split[1])))
                        .to(new LatLng(current_latitude,current_longitude))
                        //.transportMode(TransportMode.DRIVING)
                        .avoid(AvoidType.FERRIES)
                        .avoid(AvoidType.TOLLS)
                        //.transportMode(TransportMode.WALKING)
                        .execute(new DirectionCallback() {

                            @Override
                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                if(direction.isOK()) {

//                                    mMap.clear();


                                    //Alternate paths
                                   /* for (int i = 0; i < direction.getRouteList().size(); i++) {
                                        Route route = direction.getRouteList().get(i);
                                        String color = colors[i % colors.length];
                                        ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                                        mMap.addPolyline(DirectionConverter.createPolyline(Directions.this, directionPositionList, 5, Color.parseColor(color)));
                                    }*/

                                    //driving
                                    ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();

                                    mMap.addPolyline(DirectionConverter.createPolyline(MyLocation.this, directionPositionList, 5, Color.RED));

                                    Log.d("direction_success","Mapped direction");
                                } else {
                                    Log.d("direction_!success","Mapped not direction");
                                    // Do something
                                }
                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {
                                // Do something
                                Log.d("direction_failure","API key ?");
                            }
                        });

            }
        });

        // Showing / hiding your current location
        //  LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


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

        // Enable / Disable zooming controls
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        mMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        // Enable / Disable zooming functionality
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        mMap.setTrafficEnabled(true);




        // Add a marker in Sydney and move the camera
        LatLng uganda = new LatLng(current_latitude, current_longitude);

        CameraUpdate cam = CameraUpdateFactory.newLatLngZoom(uganda, zoom);
        mMap.moveCamera(cam);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        simpleLocation.beginUpdates();

        // ...
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        simpleLocation.endUpdates();

        // ...

        super.onPause();
    }

    private static Map<String,Double> sortByValue (Map<String,Double> unsorted)
    {
        List<Map.Entry<String,Double>> new_list = new LinkedList<>(unsorted.entrySet());

        Collections.sort(new_list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        Map<String,Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String,Double> entry: new_list)
        {
            sorted.put(entry.getKey(),entry.getValue());
        }

        return sorted;
    }

}


