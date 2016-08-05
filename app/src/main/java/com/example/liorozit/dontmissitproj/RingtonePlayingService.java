package com.example.liorozit.dontmissitproj;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;


public class RingtonePlayingService extends Service {
    static Ringtone r;
    static Vibrator v;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

            //activating alarm sound
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getBaseContext(), notification);
        if(!r.isPlaying()) {
            //playing sound alarm
            r.play();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            if (prefs.getBoolean("one", true)) {
                long[] pattern = {0, 400, 2000};
                v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(pattern,0);

            }
        }

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy()
    {
        if(r.isPlaying()) {
            r.stop();
          //  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
             v.cancel();
        }
    }
}