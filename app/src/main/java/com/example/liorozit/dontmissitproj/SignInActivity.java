package com.example.liorozit.dontmissitproj;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    EditText usernametext;
    EditText passwordtext;
    SharedPreferences sharedpref;
    EditText firstnametext;
    EditText lastnametext;
    EditText emailtext;
    MyDBHandler handlerdb;
    String regiondrive;
    boolean stayflag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernametext=(EditText)findViewById(R.id.username);
        passwordtext=(EditText)findViewById(R.id.password);
        firstnametext=(EditText)findViewById(R.id.firstname);
        lastnametext=(EditText)findViewById(R.id.lastname);
        emailtext=(EditText)findViewById(R.id.email);
        stayflag=false;


    }

    public void CreateProfileOnClick(View V) {
        if (firstnametext.getText().toString().matches("") || lastnametext.getText().toString().matches("") || emailtext.getText().toString().matches("") || ((RadioGroup) findViewById(R.id.regiondrivebuttons)).getCheckedRadioButtonId() == -1 || usernametext.getText().toString().matches("") || passwordtext.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "At least one detail is empty", Toast.LENGTH_SHORT).show();
        } else {
            final Dialog dialog = new Dialog(SignInActivity.this);
            dialog.setTitle("Attention!");
            dialog.setContentView(R.layout.popup_sign_in);
            dialog.show();

            Button yesbutton = (Button) dialog.findViewById(R.id.YesButton);
            final Button nobutton = (Button) dialog.findViewById(R.id.NoButtom);

            yesbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    sharedpref = getSharedPreferences("1", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("username", usernametext.getText().toString());
                    editor.putString("password", passwordtext.getText().toString());
                    editor.commit();

                    sharedpref = getSharedPreferences("2", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedpref.edit();
                    editor2.putString("sign", "true");
                    editor2.commit();

                    sharedpref = getSharedPreferences("3", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedpref.edit();
                    editor3.putBoolean("WakeMeAndCancelSwitch",true);
                    editor3.commit();
                    //save all the sql data...

                    //first, clear all 3 tables
                    try {


                        DeleteAndCreateTables();

                      //  PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref, false);
                        //String initregion;
                       // if(regiondrive.equals("hasharon")){
                        //    initregion="Hasharon";
                      //  }
                      //  else{
                      //      initregion="Hadarom";
                       // }

                        SharedPreferences settings =
                                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor4 = settings.edit();

                       // editor3.putString("listOptions",initregion);
                        editor4.putString("listValues",regiondrive);
                        editor4.putString("four",emailtext.getText().toString());
                        editor4.commit();

                    } catch (Exception e) {
                        stayflag = true;
                        Log.d(this.getClass().getName(), e.getMessage());
                    }

                    if (!stayflag) {
                        Intent i = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(i);
                    }

                    dialog.cancel();

                }

            });


            nobutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

        }
    }

    public void onRadioButtonClicked(View v){
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.hasharonButton:
                if (checked)
                    regiondrive=((RadioButton) v).getText().toString();
                    break;
            case R.id.hadaromButton:
                if (checked)
                    regiondrive=((RadioButton) v).getText().toString();
                    break;
        }
    }

    public void DeleteAndCreateTables(){
        handlerdb = new MyDBHandler(getApplicationContext(), null, null, 1);
        handlerdb.DeleteAllTablesContent();

        UserTable ut = new UserTable(firstnametext.getText().toString(), lastnametext.getText().toString(), emailtext.getText().toString(), regiondrive, 1);
        BusLinesTable bl1=new BusLinesTable(29,"hasharon","תחנה מרכזית,כפר סבא","ויצמן / רוטשילד,כפר סבא","ויצמן / ירושלים,כפר סבא","קניון ערים- וייצמן / אנה פרנק,כפר סבא","ויצמן/ברנר,כפר סבא","קרית חינוך,כפר סבא","טשרניחובסקי / ויצמן,כפר סבא","מסוף רעננה,רעננה","אחוזה / כביש 4,כפר סבא","אחוזה / רבוצקי,רעננה");
        BusLinesTable bl2=new BusLinesTable(65,"hasharon","תחנת רכבת,כפר סבא","אסירי ציון / ויצמן,הוד השרון","החומש / חנקין,הוד השרון","חנקין,הוד השרון","חנקין / נצח ישראל,הוד השרון","מגדל המים- חנקין 68,הוד השרון","ששת הימים,הוד השרון","ששת הימים / הר מירון,הוד השרון","החרמון / אילת,הוד השרון","החרמון / הגולן,הוד השרון");
        BusLinesTable bl3=new BusLinesTable(360,"hadarom","תחנה מרכזית,אשדוד","שדרות מנחם בגין / שדרות משה סנה,אשדוד","צומת עד הלום/ כביש 4,באר טוביה","צומת ברכיה,אשקלון","מרכז אורן,באר שבע","בית חולים סורוקה,באר שבע","שדרות יצחק רגר / ביאליק,באר שבע","יד לבנים- שדרות יצחק רגר,באר שבע","שדרות יצחק רגר,באר שבע","תחנה מרכזית,באר שבע");
        BusLinesTable bl4=new BusLinesTable(353,"hadarom","תחנה מרכזית חדשה,תל אביב","מקווה ישראל / צומת חולון,מקוה ישראל","דרך השבעה/המכתש למזרח,חולון","דרך השבעה / קפלן,חולון","תחנה מרכזית,ראשון לציון","צומת עין הקורא,ראשון לציון","כביש 42/בית חנן,בית חנן","עיינות/ כביש 42,גן רווה","כפר הנגיד- כביש 42,גן רווה","שדרות העצמאות / אהרון חג'ג',יבנה");
        BusLinesTable bl5=new BusLinesTable(1,"hasharon","קניון שרונים,הוד השרון","מסעף נווה נאמן,הוד השרון","צומת רמת הדר,הוד השרון","צומת ירקונה,הוד השרון","צומת גני עם,הוד השרון","הוד השרון מרכז/דרך רמתיים,הוד השרון","מגדיאל / יהושע בן גמלא,הוד השרון","מגדיאל / פדויים,הוד השרון","כיכר מגדיאל- זקיף,הוד השרון","הצריף הראשון- סוקולוב / הידידות,הוד השרון");



        StationLocationTable sl1=new StationLocationTable("תחנה מרכזית,כפר סבא",32.173863, 34.914819);StationLocationTable sl2=new StationLocationTable("ויצמן / רוטשילד,כפר סבא",32.175088, 34.909375);
        StationLocationTable sl3=new StationLocationTable("ויצמן / ירושלים,כפר סבא",32.175620, 34.906589);StationLocationTable sl4=new StationLocationTable("קניון ערים- וייצמן / אנה פרנק,כפר סבא",32.176347, 34.903877);
        StationLocationTable sl5=new StationLocationTable("ויצמן/ברנר,כפר סבא",32.177206, 34.900293);StationLocationTable sl6=new StationLocationTable("קרית חינוך,כפר סבא",32.177959, 34.897174);
        StationLocationTable sl7=new StationLocationTable("טשרניחובסקי / ויצמן,כפר סבא",32.177882, 34.894075);StationLocationTable sl8=new StationLocationTable("מסוף רעננה,רעננה",32.175540, 34.890263);
        StationLocationTable sl9=new StationLocationTable("אחוזה / כביש 4,כפר סבא",32.177867, 34.886757);StationLocationTable sl10=new StationLocationTable("אחוזה / רבוצקי,רעננה",32.178524, 34.883894);

        StationLocationTable sl12=new StationLocationTable("תחנת רכבת,כפר סבא",32.167641, 34.917442);StationLocationTable sl22=new StationLocationTable("אסירי ציון / ויצמן,הוד השרון",32.165924, 34.909627);
        StationLocationTable sl32=new StationLocationTable("החומש / חנקין,הוד השרון",32.159377, 34.906473);StationLocationTable sl42=new StationLocationTable("חנקין,הוד השרון",32.158120, 34.910238);
        StationLocationTable sl52=new StationLocationTable("חנקין / נצח ישראל,הוד השרון",32.158516, 34.909123);StationLocationTable sl62=new StationLocationTable("מגדל המים- חנקין 68,הוד השרון",32.157196, 34.912933);
        StationLocationTable sl72=new StationLocationTable("ששת הימים,הוד השרון",32.157410, 34.912263);StationLocationTable sl82=new StationLocationTable("ששת הימים / הר מירון,הוד השרון",32.157676, 34.917777);
        StationLocationTable sl92=new StationLocationTable("החרמון / אילת,הוד השרוןן",32.157678, 34.921868);StationLocationTable sl102=new StationLocationTable("החרמון / הגולן,הוד השרון",32.159111, 34.922504);
///////////////////////
      //  StationLocationTable hasharon1=new StationLocationTable();StationLocationTable hasharon2=new StationLocationTable();
      //  StationLocationTable hasharon3=new StationLocationTable();StationLocationTable hasharon4=new StationLocationTable();
      //  StationLocationTable hasharon5=new StationLocationTable();StationLocationTable hasharon6=new StationLocationTable();
      //  StationLocationTable hasharon7=new StationLocationTable();StationLocationTable hasharon8=new StationLocationTable();
       // StationLocationTable hasharon9=new StationLocationTable();StationLocationTable hasharon10=new StationLocationTable();





//////////////////////////
        StationLocationTable hadarom11=new StationLocationTable("תחנה מרכזית חדשה,תל אביב",32.055975, 34.780282);StationLocationTable hadarom12=new StationLocationTable("מקווה ישראל / צומת חולון,מקוה ישראל",32.037199, 34.777021);
        StationLocationTable hadarom13=new StationLocationTable("דרך השבעה/המכתש למזרח,חולון",32.027458, 34.798369);StationLocationTable hadarom14=new StationLocationTable("דרך השבעה / קפלן,חולון",32.017674, 34.808901);
        StationLocationTable hadarom15=new StationLocationTable("תחנה מרכזית,ראשון לציון",32.056228, 34.780864);StationLocationTable hadarom16=new StationLocationTable("צומת עין הקורא,ראשון לציון",31.956502, 34.786228);
        StationLocationTable hadarom17=new StationLocationTable("כביש 42/בית חנן,בית חנן",31.935698, 34.778282);StationLocationTable hadarom18=new StationLocationTable("עיינות/ כביש 42,גן רווה",31.914785, 34.771023 );
        StationLocationTable hadarom19=new StationLocationTable("כפר הנגיד- כביש 42,גן רווה",31.889694, 34.756266 );StationLocationTable hadarom110=new StationLocationTable("שדרות העצמאות / אהרון חג'ג',יבנה",31.875753, 34.749396 );





        ////////////////
        StationLocationTable hadarom1=new StationLocationTable("תחנה מרכזית,אשדוד",31.790081, 34.639407);StationLocationTable hadarom2=new StationLocationTable("שדרות מנחם בגין / שדרות משה סנה,אשדוד",31.784786, 34.650033);
        StationLocationTable hadarom3=new StationLocationTable("צומת עד הלום/ כביש 4,באר טוביה",31.765900, 34.667360);StationLocationTable hadarom4=new StationLocationTable("צומת ברכיה,אשקלון",31.665286, 34.606619);
        StationLocationTable hadarom5=new StationLocationTable("מרכז אורן,באר שבע",31.270670, 34.797955);StationLocationTable hadarom6=new StationLocationTable("בית חולים סורוקה,באר שבע",31.260937, 34.801054);
        StationLocationTable hadarom7=new StationLocationTable("שדרות יצחק רגר / ביאליק,באר שבע",31.260046, 34.801402);StationLocationTable hadarom8=new StationLocationTable("יד לבנים- שדרות יצחק רגר,באר שבע",31.250171, 34.798114);
        StationLocationTable hadarom9=new StationLocationTable("שדרות יצחק רגר,באר שבע",31.258872, 34.798013);StationLocationTable hadarom10=new StationLocationTable("תחנה מרכזית,באר שבע",31.242864, 34.797961);


        handlerdb.AddUserDetailsRow(ut);
        handlerdb.AddBusLinesRow(bl1);handlerdb.AddBusLinesRow(bl2);handlerdb.AddBusLinesRow(bl3);
        handlerdb.AddBusLinesRow(bl4);//;handlerdb.AddBusLinesRow(bl5);
        handlerdb.AddStationLocationDetailsRow(sl1, sl2, sl3, sl4, sl5, sl6, sl7, sl8, sl9, sl10);
        handlerdb.AddStationLocationDetailsRow(sl12,sl22,sl32,sl42,sl52,sl62,sl72,sl82,sl92,sl102);
        handlerdb.AddStationLocationDetailsRow(hadarom1,hadarom2,hadarom3,hadarom4,hadarom5,hadarom6,hadarom7,hadarom8,hadarom9,hadarom10);
        handlerdb.AddStationLocationDetailsRow(hadarom11,hadarom12,hadarom13,hadarom14,hadarom15,hadarom16,hadarom17,hadarom18,hadarom19,hadarom110);
      //  handlerdb.AddStationLocationDetailsRow(sl12,sl22,sl32,sl42,sl52,sl62,sl72,sl82,sl92,sl102);

    }
}
