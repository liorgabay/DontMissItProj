package com.example.liorozit.dontmissitproj;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Preferences extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    MyDBHandler handlerdb;
    String tempchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        handlerdb = new MyDBHandler(getApplicationContext(), null, null, 1);
        addPreferencesFromResource(R.xml.pref);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if (key.equals("three"))
        {
            Preference exercisesPref = findPreference(key);
            ListPreference listPref = (ListPreference) exercisesPref;
            tempchange=listPref.getValue();
            handlerdb.UpdateLocationUser(tempchange);
        }
        if (key.equals("four"))
        {

            tempchange=sharedPreferences.getString(key, "");
            handlerdb.UpdateEmailUser(tempchange);
            // Set summary to be the user-description for the selected value
          //  Preference exercisesPref = findPreference(key);
            //exercisesPref.setSummary(sharedPreferences.getString(key, ""));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
