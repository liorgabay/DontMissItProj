package com.example.liorozit.dontmissitproj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView hellotext;
    SharedPreferences sharedpref;
    String username;
    Button WakeAndCancelBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            String URL = extras.getString("MissedURL");
            //extras.putString("MissedURL",null);
            getIntent().removeExtra("MissedURL");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            this.startActivity(browserIntent);
        }



        sharedpref=getSharedPreferences("1", Context.MODE_PRIVATE);
        hellotext=(TextView)findViewById(R.id.hellotext);
        username=sharedpref.getString("username", "");
        hellotext.setText("Hello " + username);

        WakeAndCancelBtn=(Button)findViewById(R.id.WakeMeButton);

        sharedpref= getSharedPreferences("3", Context.MODE_PRIVATE);
        if(sharedpref.getBoolean("WakeMeAndCancelSwitch",true)){
            WakeAndCancelBtn.setText("Wake Me Up!");
        }
        else{
            WakeAndCancelBtn.setText("Cancel Alarm");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        // Main Activity Close WebView
        WebView wv = (WebView) findViewById(R.id.TariffwebView);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        wv.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Main Activity Close WebView
    }

    public void LogOffOnClick(View v){
        sharedpref=getSharedPreferences("2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpref.edit();
        editor.putString("sign", "false");
        editor.commit();
        Intent i=new Intent(this,LogInActivity.class);
        startActivity(i);

    }

    public void PrefSettingsOnClick(View v){
        Intent i=new Intent(MainActivity.this,Preferences.class);
        startActivity(i);
    }


    public void ShowTariffOnClick(View v){
        WebView wv = (WebView) findViewById(R.id.TariffwebView);
        wv.setVisibility(View.VISIBLE);
        wv.loadUrl("http://trans-reform.org.il/");
        WebSettings settings= wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

    }

    public void WakeMeOnClick(View v){


        if(WakeAndCancelBtn.getText().toString().equals("Wake Me Up!")){
            Intent newIntent = new Intent(this, GPSSettingsActivity.class);
            this.startActivity(newIntent);
        }
        else{
            sharedpref= getSharedPreferences("3", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putBoolean("WakeMeAndCancelSwitch",true);
            editor.commit();
            Intent newIntent = new Intent(this, GPSLocationActivity.class);
            this.startActivity(newIntent);
        }



    }


}
