package com.example.liorozit.dontmissitproj;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GPSLocationActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    // LogCat tag
    private static final String TAG = GPSLocationActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters



    SharedPreferences sharedpref;
    boolean UserStatus=true;
    public static boolean RUN_ONCE = true;
    MyDBHandler handlerdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpslocation);



            // First we need to check availability of play services
            if (checkPlayServices()) {

                // Building the GoogleApi client
                buildGoogleApiClient();

                createLocationRequest();
            }


            sharedpref = getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = sharedpref.edit();
            UserStatus = sharedpref.getBoolean("UserStatus", true);


            Log.d("UserStatus now is:", String.valueOf(UserStatus));
            if (UserStatus) {
                Button OkStopButton = (Button) findViewById(R.id.OkStopButton);
                OkStopButton.setVisibility(View.VISIBLE);
                TextView WakeUpTimeTextView = (TextView) findViewById(R.id.WakeUpTimeTextView);
                WakeUpTimeTextView.setVisibility(View.VISIBLE);

                Button DidItButton = (Button) findViewById(R.id.DidItButton);
                Button MissItButton = (Button) findViewById(R.id.MissItButton);
                DidItButton.setVisibility(View.INVISIBLE);
                MissItButton.setVisibility(View.INVISIBLE);
                TextView DestArrivedTextView = (TextView) findViewById(R.id.DestArrivedTextView);
                DestArrivedTextView.setVisibility(View.INVISIBLE);
            } else {
                Button OkStopButton = (Button) findViewById(R.id.OkStopButton);
                OkStopButton.setVisibility(View.INVISIBLE);
                TextView WakeUpTimeTextView = (TextView) findViewById(R.id.WakeUpTimeTextView);
                WakeUpTimeTextView.setVisibility(View.INVISIBLE);

                Button DidItButton = (Button) findViewById(R.id.DidItButton);
                Button MissItButton = (Button) findViewById(R.id.MissItButton);
                DidItButton.setVisibility(View.VISIBLE);
                MissItButton.setVisibility(View.VISIBLE);
                TextView DestArrivedTextView = (TextView) findViewById(R.id.DestArrivedTextView);
                DestArrivedTextView.setVisibility(View.VISIBLE);

            }


    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        // checkPlayServices();

        // Resuming the periodic location updates
        //  if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
        //     startLocationUpdates();
        //  }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }


        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
        sharedpref= getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putBoolean("VibAndRingtoneSwitch",false);
        editor.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // stopLocationUpdates();
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);



        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

        } else {

            Toast.makeText(getApplicationContext(),
                    "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_LONG)
                    .show();

        }
    }


    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

     //   Toast.makeText(getApplicationContext(), "UPDATE_INTERVAL is : " + UPDATE_INTERVAL +" Seconds",
    //            Toast.LENGTH_LONG).show();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, LocationReciever.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, pendingIntent);

        Toast.makeText(getApplicationContext(),
                "request location updates OnReciever Is Set!", Toast.LENGTH_LONG)
                .show();

    }

    /**
     * Stopping location updates
     */
    protected  void stopLocationUpdates() {
        try {


        RUN_ONCE=true;
        sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus",Context.MODE_WORLD_READABLE);
        Boolean curruserstatus=sharedpref.getBoolean("UserStatus",true);
        SharedPreferences.Editor editor=sharedpref.edit();
        editor.putFloat("AlarmDistance", 0);
        editor.putBoolean("UserStatus", true);
        editor.commit();

        sharedpref= getSharedPreferences("3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedpref.edit();
        editor2.putBoolean("WakeMeAndCancelSwitch", true);
        editor2.commit();

        sharedpref= getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor3 = sharedpref.edit();
        editor3.putBoolean("VibAndRingtoneSwitch", false);
        editor3.commit();

         sharedpref= getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
         SharedPreferences.Editor editor5 = sharedpref.edit();
         editor5.putBoolean("IsArrivalDestination",false);
         editor5.commit();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, LocationReciever.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, pendingIntent);
        Toast.makeText(getApplicationContext(),
                "request location updates OnReciever Is Canceled!", Toast.LENGTH_LONG)
                .show();



        if(curruserstatus) {
            sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus",Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor4=sharedpref.edit();
            editor4.putString("Latitude", "");
            editor4.putString("Longitude", "");
            editor4.commit();
            Intent newIntent = new Intent(this, MainActivity.class);
            this.startActivity(newIntent);
        }
     }
     catch (Exception e){
         Toast.makeText(getApplicationContext(), "There was an error Step 2: "+e.getMessage(), Toast.LENGTH_SHORT).show();
         e.printStackTrace();
     }
    }


    public void StopOnClick(View v){
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);

        stopLocationUpdates();
    }




    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        sharedpref = getSharedPreferences("3", Context.MODE_PRIVATE);
        if(sharedpref.getBoolean("WakeMeAndCancelSwitch",false)) {

            stopLocationUpdates();
        }
        else {
            displayLocation();
            runOnce();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();
    }



    private void runOnce() {
        if (RUN_ONCE) {
            RUN_ONCE = false;

            if (mLastLocation!=null) {

                sharedpref = getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
                Location dest = new Location("wow");
                dest.setLatitude(Double.parseDouble(sharedpref.getString("Latitude","")));
                dest.setLongitude(Double.parseDouble(sharedpref.getString("Longitude","")));
                float FirstDistance = mLastLocation.distanceTo(dest);
                Log.d("first distance",String.valueOf(FirstDistance));
///////////////////////
//1000 * 5 * 1
                //System.currentTimeMillis()
                //interval time of distance check
                int SecondsInterval;
                if (FirstDistance >= 0 && FirstDistance <= 1000) SecondsInterval = 3000;
                else if (FirstDistance > 1000 && FirstDistance <= 2000) SecondsInterval = 4000;
                else if (FirstDistance > 2000 && FirstDistance <= 3500) SecondsInterval = 5000;
                else SecondsInterval = 5000;
                UPDATE_INTERVAL=SecondsInterval;
                createLocationRequest();
                ///
                //check the distance to alarm user before dist. station
                handlerdb = new MyDBHandler(getApplicationContext(), null, null, 1);
                int MissingCount = handlerdb.GetMissingCountUser();//for example-import from db
                float AlarmDistance=0;
                if(MissingCount==0){
                    AlarmDistance= (FirstDistance/ 10);
                }
                else{
                    AlarmDistance= (FirstDistance/ 10) * MissingCount;
                }
                Toast.makeText(getApplicationContext(), "Missing Stations Number is : " + MissingCount,
                        Toast.LENGTH_LONG).show();


                Log.d("AlarmDistance activity", String.valueOf(AlarmDistance));
                sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor=sharedpref.edit();
                editor.putFloat("AlarmDistance",AlarmDistance);
                editor.putBoolean("UserStatus",true);
                editor.commit();

                ///////////////////////
                // alarm.SetAlarm(this, SecondsInterval);
                startLocationUpdates();

            } else {
                //  gps.showSettingsAlert();
                // RUN_ONCE=true;
                stopLocationUpdates();
            }

            Intent newIntent = new Intent(this, MainActivity.class);
            this.startActivity(newIntent);
        }
    }


public void DidItOnClick(View v){
    stopLocationUpdates();

    Intent newIntent = new Intent(this, MainActivity.class);
    this.startActivity(newIntent);
}


    public void MissItOnClick(View v){
        String CurrMissedLatitude,CurrMissedLongitude,DestLatitude,DestLongitude;
        StringBuilder URL=new StringBuilder();

        handlerdb = new MyDBHandler(getApplicationContext(), null, null, 3);
        handlerdb.UpdateMissingCount();

        //show alternative website
        sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
        CurrMissedLatitude=sharedpref.getString("MissedLatitude", "");
        CurrMissedLongitude=sharedpref.getString("MissedLongitude", "");
        DestLatitude=sharedpref.getString("Latitude","");
        DestLongitude=sharedpref.getString("Longitude","");
        URL.append("https://www.google.co.il/maps/dir/'");
        URL.append(CurrMissedLatitude+","+CurrMissedLongitude);
        URL.append("'/'");
        URL.append(DestLatitude+","+DestLongitude);
        URL.append("'/@");
        URL.append(CurrMissedLatitude+","+CurrMissedLongitude);
        URL.append(",14z/");
        URL.append("data=!4m2!4m1!3e3");


        stopLocationUpdates();
    //det lat & long deleted from this class in stoplocationsupdate
        //only one intent work
        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.putExtra("MissedURL", URL.toString());
        this.startActivity(newIntent);
    }

}