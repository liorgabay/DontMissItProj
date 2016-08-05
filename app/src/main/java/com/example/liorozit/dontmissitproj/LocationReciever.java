package com.example.liorozit.dontmissitproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

/**
 * Created by LioRozit on 17/06/2016.
 */
public class LocationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Do this when the system sends the intent
        //  Bundle b = intent.getExtras();
        //   Toast.makeText(context, "location changed on locationReciever!", Toast.LENGTH_SHORT).show();
        // Location loc = intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);

        //Toast.makeText(context, loc.toString(), Toast.LENGTH_SHORT).show();

        try {
            if (LocationResult.hasResult(intent)) {
                Boolean IsArrivalDestination;
                SharedPreferences prefs = context.getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_WORLD_READABLE);
                LocationResult locationResult = LocationResult.extractResult(intent);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    //set current lat & long
                    SharedPreferences.Editor editor3 = prefs.edit();
                    editor3.putString("MissedLatitude", Double.toString(location.getLatitude()));
                    editor3.putString("MissedLongitude", Double.toString(location.getLongitude()));
                    editor3.commit();
                }

                //Boolean switch for updates
                IsArrivalDestination=prefs.getBoolean("IsArrivalDestination",false);

                if (location != null && !IsArrivalDestination) {
                    // use the Location

                    Location dest = new Location("wow");
                    dest.setLatitude(Double.parseDouble(prefs.getString("Latitude","")));
                    dest.setLongitude(Double.parseDouble(prefs.getString("Longitude","")));
                    float CurrDistance = location.distanceTo(dest);


                    float AlarmDistance=prefs.getFloat("AlarmDistance", 0);

                    boolean VibAndRingtoneSwitch=prefs.getBoolean("VibAndRingtoneSwitch",false);

                    if(CurrDistance>=0 && CurrDistance<=50){
                        try {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("UserStatus", false);
                            editor.commit();
                            //  this.CancelAlarm(context);

                            Intent i = new Intent(context, RingtonePlayingService.class);
                            context.stopService(i);

                            SharedPreferences.Editor editor2 = prefs.edit();
                            editor2.putBoolean("VibAndRingtoneSwitch", false);
                            editor2.commit();


                            SharedPreferences.Editor editor4 = prefs.edit();
                            editor4.putBoolean("IsArrivalDestination", true);
                            editor4.commit();


                            ///////////////
                            Bundle bundle = intent.getExtras();
                            String message = bundle.getString("alarm_message");

                            Intent newIntent = new Intent(context, GPSLocationActivity.class);
                            newIntent.putExtra("alarm_message", message);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(newIntent);
                        }
                        catch(Exception e){
                            Toast.makeText(context, "There was an error Step 1: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    else if(AlarmDistance>=CurrDistance){
                        if(!VibAndRingtoneSwitch){
                            Intent i = new Intent(context, RingtonePlayingService.class);
                            context.startService(i);

                            SharedPreferences.Editor editor=prefs.edit();
                            editor.putBoolean("VibAndRingtoneSwitch", true);
                            editor.commit();


                            Bundle bundle = intent.getExtras();
                            String message = bundle.getString("alarm_message");

                            Intent newIntent = new Intent(context, GPSLocationActivity.class);
                            newIntent.putExtra("alarm_message", message);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(newIntent);
                        }
                    }
                    Toast.makeText(context, "Location latitude: " + location.getLatitude() + ", Location longtitude: " + location.getLongitude()+" latitude dest:"+dest.getLatitude()+" Longitude dest:"+dest.getLongitude()+" current distance: "+CurrDistance+" From LocationReciever", Toast.LENGTH_SHORT).show();
                }
                else if(location != null && IsArrivalDestination){
                    Toast.makeText(context, "You Have Arrived Already!,Current Location - latitude: " + location.getLatitude() + ", Location longtitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }
}