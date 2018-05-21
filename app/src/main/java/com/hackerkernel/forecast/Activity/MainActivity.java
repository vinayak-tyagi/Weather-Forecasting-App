package com.hackerkernel.forecast.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hackerkernel.forecast.Pojo.CurrentPojo;
import com.hackerkernel.forecast.R;
import com.hackerkernel.forecast.Utils.Util;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static double currentLatitude;
    private static double currentLongitude;
    private static String name;

    RequestQueue requestQueue;
    TextView location, humidity, pricp, time, summary, temp;
    ImageView icon;
    Button daily, monthly;
    ProgressDialog pd;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText searchtxt;
    ImageButton searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        isGPSEnable();
        isNetworkAvailable();
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)  // 10 sec
                .setFastestInterval(1 * 1000); // 1sec

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);

        location = findViewById(R.id.area);
        humidity = findViewById(R.id.humidity_value);
        pricp = findViewById(R.id.rain_value);
        time = findViewById(R.id.dayname);
        summary = findViewById(R.id.summary);
        temp = findViewById(R.id.temprature);
        icon = findViewById(R.id.imageView);
        daily = findViewById(R.id.daily_btn);
        monthly = findViewById(R.id.monthly_btn);
        searchtxt =findViewById(R.id.search_txt);
        searchbtn = findViewById(R.id.search_btn);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        daily.setEnabled(false);
        daily.setVisibility(View.GONE);
        monthly.setEnabled(false);
        monthly.setVisibility(View.GONE);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = searchtxt.getText().toString().trim();
                if (!location.isEmpty()){
                    Log.e("static name, location","\n"+ location);
                     onFetchingData(location);
                }else{
                     searchtxt.setError("Please Enter Valid Input");
                }
             }
        });

    }


    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        onFetchingData(name);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onFetchingData(String locationName) {
        isNetworkAvailable();
        pd.show();

        // NOTE THAT THE API KEY OVERHERE IS HIGHLY IMPORTANT AND CONFIDENTIAL
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://api.worldweatheronline.com/premium/v1/weather.ashx?key=c3527523f7794c2888950956180704&q="+locationName+"&format=json&num_of_days=7",
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Util.JsonParse(response,MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                UIuppdate();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
               Log.e("Vin: MainActivity", "This error from json calling");
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void UIuppdate() {
        location.setText(CurrentPojo.getLocation());
        temp.setText(CurrentPojo.getTemp() + "");
        humidity.setText(CurrentPojo.getHumidity() + "");
        pricp.setText(CurrentPojo.getPricip() + "");
        icon.setImageResource(CurrentPojo.getIcon_id());
        time.setText("Temperature at " + CurrentPojo.getTime() + " is");
        summary.setText(CurrentPojo.getSummary());
        onButtonUpdate();
    }

    private void onButtonUpdate() {

        daily.setEnabled(true);
        daily.setVisibility(View.VISIBLE);
        monthly.setEnabled(true);
        monthly.setVisibility(View.VISIBLE);

        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                i.putExtra("value", 1);
                startActivity(i);
            }
        });

        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                i.putExtra("value", 0);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Log.e("VIN:","location is null");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.e("VIN: ,location: ",currentLatitude+"\n"+currentLongitude);

            getlocationName(currentLatitude,currentLongitude);

            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
             /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                Log.e("VIN:","onConnectionFailure");
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
           // setAlertDialog("GPS Setting","GPS is not enabled. Do you want to go to settings menu?",1);
            setAlertDialog("GPS Setting","GPS is not enabled. Do you want to go to settings menu?",1);
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Log.e("VIN: GPS: ",currentLatitude+"\n"+currentLongitude);
        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    private boolean isGPSEnable(){
        LocationManager manager = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
        boolean enable = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enable){
            setAlertDialog("GPS Setting","GPS is not enabled. Do you want to go to settings menu?",1);
        }
       return true;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!= null && networkInfo.isConnectedOrConnecting()) {
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }else {
            setAlertDialog("Internet Setting","Internet is not enabled. Do you want to go to settings menu?",0);
            Toast.makeText(MainActivity.this, "Please connect to Internet", Toast.LENGTH_SHORT).show(); }
        return false;
    }

     private void setAlertDialog(String title, String msg, final int check){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (check == 0){  // FOR INTERNET

                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else if (check == 1){  // FOR GPS

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        builder.create().show();
    }

    private void getlocationName(double lat,double lon)  {
        isNetworkAvailable();
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&key=AIzaSyDWu-sNamRjuzoaXZXBBTAROGWZa8PFp1k",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                Log.e("VIN: location",response);
                try {
                    name = Util.JsonParselocation(response);
                } catch (JSONException e) {
                    Log.e("VIN: ","error in location json parse");
                    e.printStackTrace();
                }
                onFetchingData(name);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.e("Vin: MainActivity", "This error from json location calling");
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

//    private String getlocationName(double lat,double lon)  {
//        Geocoder geocoder = new Geocoder(MainActivity.this);
//        try {
//            List<Address> addressList = geocoder.getFromLocation(lat,lon,1);
//            if (addressList != null & addressList.size() > 0){
//                Address address = addressList.get(0);
//                String result = address.getLocality();
//                return  result;
//            }
//        } catch (IOException e) {
//            Log.e("VIN:","Unable to get Location Name");
//            e.printStackTrace();
//        }
//        return null;
//    }

}
