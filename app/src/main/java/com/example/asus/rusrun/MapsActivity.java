package com.example.asus.rusrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latRunADouble = 13.859132;
    private double lngRunADouble = 100.481989;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latUserADouble, lngUserADouble;
    private boolean distanceABoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Setup location Service
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }//Main  Method

    //นี่คือ เมทอด ที่หาระยะ ระหว่างจุด
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344 *1000;//meter Unit


        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        latUserADouble = latRunADouble;
        lngUserADouble = lngRunADouble;

        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            latUserADouble = networkLocation.getLatitude();
            lngUserADouble = networkLocation.getLongitude();

        }
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {

            latUserADouble = gpsLocation.getLatitude();
            lngUserADouble = gpsLocation.getLongitude();
        }





    }// onResume

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);



    }

    public Location myFindLocation (String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000,10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);


        } else {
            Log.d("RusV2", "Cannot Find Location");
        }

        return location;
    }

    //Create Class
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latUserADouble = location.getLatitude();
            lngRunADouble = location.getLongitude();


        } //onLocationChange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(latRunADouble,lngRunADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        //Loop
        myLoop();


    }//onMapReady

    private class CreatMarker extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private GoogleMap googleMap;
        private String urlJSON = "http://swiftcodingthai.com/rus/get_user_nattikan.php";
        private int[] avataInts = new int[]{R.drawable.bird48, R.drawable.doremon48,
                R.drawable.kon48, R.drawable.nobita48, R.drawable.rat48};


        public CreatMarker(Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }// Constructor

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();



            } catch (Exception e) {
                return null;
            }//try


        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Rus4", "JSON ==>>>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    double douLat = Double.parseDouble(jsonObject.getString("Lat"));
                    double douLng = Double.parseDouble(jsonObject.getString("Lng"));
                    String strName = jsonObject.getString("Name");
                    int intIndex = Integer.parseInt(jsonObject.getString("Avata"));


                    LatLng latLng = new LatLng(douLat, douLng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(strName)

                    .icon(BitmapDescriptorFactory.fromResource(avataInts[intIndex])));

                }//for

            } catch (Exception e) {
                e.printStackTrace();
            }
        }//onPost



        //CreatMarker Class


    }
    private void myLoop() {

        //To Do
        Log.d("RusV3", "latUser ==>" + latUserADouble);
        Log.d("RusV3", "lngUser ==>" + lngUserADouble);

        //Edit Lat,Lng on Server
        editLatlngOnServer();

        //Create Marker
        mMap.clear();
        CreatMarker creatMarker = new CreatMarker(this, mMap);
        creatMarker.execute();

        //CheckDistance
        double latCheckPoint = 13.859132;
        double lngCheckPoint = 100.481989;
        double userDistance = distance(latCheckPoint, lngCheckPoint,
                latUserADouble, lngUserADouble);
        Log.d("RusV5", "Distance ==>" + userDistance);

        if (userDistance < 10 ) {

            if (distanceABoolean) {

                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"ถึงฐานแล้ว","คุณเข้าใกล้ต่ำกว่า 10 เมตรแล้ว");
                distanceABoolean = false;
            }

        }// if1



        //Delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               myLoop();
            }
        },3000);
    } //myLoop

    private void editLatlngOnServer() {

        String urlPHP = "http://swiftcodingthai.com/rus/edit_location_nattikan.php";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", getIntent().getStringExtra("loginID"))
                .add("Lat", Double.toString(latUserADouble))
                .add("Lng", Double.toString(lngUserADouble))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    }// editLatLng

}// Main Class
