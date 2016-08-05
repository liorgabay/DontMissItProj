package com.example.liorozit.dontmissitproj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GPSSettingsActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    Spinner spinner1;
    Spinner spinner2;
    SharedPreferences sharedpref;
    MyDBHandler handlerdb;
    String userregion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpssettings2);


        handlerdb = new MyDBHandler(getApplicationContext(), null, null, 1);
        userregion= handlerdb.GetUserRegion();
        TextView TitleRegionTextView=(TextView)findViewById(R.id.TitleRegionTextView);
        TitleRegionTextView.setText("The region that i want to get off the bus is "+userregion);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        ///////////////////////////
        spinner1.setOnItemSelectedListener(this);
        //loadSpinnerData();
        List<String> lines = handlerdb.GetAllLinesRegion(userregion);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lines);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);
        ///////////////////////////
        spinner2.setOnItemSelectedListener(this);
        loadSpinner2Data(lines.get(0));
    }

    private void loadSpinner2Data(String line) {
        handlerdb = new MyDBHandler(getApplicationContext(), null, null, 1);
        List<String> stations = handlerdb.GetStationsLine(line, userregion);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stations);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner1)
        {
            String line = parent.getItemAtPosition(position).toString();
            loadSpinner2Data(line);

            sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString("Latitude","");
            editor.putString("Longitude","");
            editor.commit();


            // Showing selected spinner item
         //   Toast.makeText(parent.getContext(), "You selected line : " + line,
        //            Toast.LENGTH_LONG).show();
        }
        else if(spinner.getId() == R.id.spinner2)
        {
            String station = parent.getItemAtPosition(position).toString();


            List<String> LatAndLong=handlerdb.GetStationLocation(station);
            sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString("Latitude",LatAndLong.get(0));
            editor.putString("Longitude",LatAndLong.get(1));
            editor.commit();




         //   Toast.makeText(parent.getContext(), "You selected station : " + station,
                    //Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void SetAlarmOnClick(View v){
        sharedpref=getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
        if(sharedpref.getString("Latitude","").equals("") || sharedpref.getString("Longitude","").equals("")){
            Toast.makeText(getApplicationContext(), "Line and Station must be chosen", Toast.LENGTH_LONG).show();
        }
        else{
            sharedpref= getSharedPreferences("3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putBoolean("WakeMeAndCancelSwitch",false);
            editor.commit();

            sharedpref= getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedpref.edit();
            editor2.putBoolean("VibAndRingtoneSwitch",false);
            editor2.commit();

            sharedpref= getSharedPreferences("AlarmDistanceAndUserStatus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor3 = sharedpref.edit();
            editor3.putBoolean("IsArrivalDestination",false);
            editor3.commit();

            Intent newIntent = new Intent(this, GPSLocationActivity.class);
            this.startActivity(newIntent);
        }
        Log.d(this.getClass().getName(), "Latitude is: " + sharedpref.getString("Latitude","")+", Longitude is: " + sharedpref.getString("Longitude",""));

    }

}
