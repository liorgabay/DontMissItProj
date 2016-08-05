package com.example.liorozit.dontmissitproj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText usernametext;
    EditText passwordtext;
    SharedPreferences sharedpref;
    String s1;
    String s2;
    String s3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        sharedpref= getSharedPreferences("1", Context.MODE_PRIVATE);
        s1=sharedpref.getString("username", "");
        s2=sharedpref.getString("password", "");
        usernametext=(EditText)findViewById(R.id.username);
        passwordtext=(EditText)findViewById(R.id.password);

        sharedpref= getSharedPreferences("2", Context.MODE_PRIVATE);
        s3=sharedpref.getString("sign", "");
        if(s3.equals("true")){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }




    }

    public void LogInOnClick(View v){


        if(!usernametext.getText().toString().equals(s1)) Toast.makeText(this, "Wrong username", Toast.LENGTH_LONG).show();
        else if(!passwordtext.getText().toString().equals(s2)) Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show();
        else{
            sharedpref=getSharedPreferences("2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString("sign","true");
            editor.commit();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }

    public void CreateAccountOnClick(View v){
        Intent i=new Intent(this,SignInActivity.class);
        startActivity(i);
    }

    public void testonclick(View v){
        Intent i = new Intent(getApplicationContext(), RingtonePlayingService.class);
        getApplicationContext().startService(i);
    }

    public void stoptest(View v){
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
    }
}
