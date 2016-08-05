package com.example.liorozit.dontmissitproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME="DontMissItDB.db";

    //UserPersonalDetails Table
    private static final String TABLE_USER_PERSONAL_DETAILS="UserDetails";
    private static final String COLUMN_FIRSTNAME="firstname",COLUMN_LASTNAME="lastname",COLUMN_EMAIL="email",COLUMN_LOCATION_USER="location",COLUMN_MISSING_NUMBER="missingnumber";

    //BusLinesTable Table
    private static final String TABLE_BUS_LINES="BusLines";
    private static final String COLUMN_LINE="line",COLUMN_LOCATION_BUS="location";
    private static final String COLUMN_STATION_1="station1",COLUMN_STATION_2="station2",COLUMN_STATION_3="station3",COLUMN_STATION_4="station4",COLUMN_STATION_5="station5",COLUMN_STATION_6="station6",COLUMN_STATION_7="station7",COLUMN_STATION_8="station8",COLUMN_STATION_9="station9",COLUMN_STATION_10="station10";

    //StationLocationDetails Table
    private static final String TABLE_STATION_LOCATION_DETAILS="StationLocationDetails";
    private static final String COLUMN_STATION="station",COLUMN_LATITUDE ="latitude",COLUMN_LONGITUDE="longitude";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query1="CREATE TABLE "+TABLE_USER_PERSONAL_DETAILS+" ("+COLUMN_FIRSTNAME+" TEXT,"+COLUMN_LASTNAME+" TEXT,"
                +COLUMN_EMAIL+" TEXT,"+COLUMN_LOCATION_USER+" TEXT,"+COLUMN_MISSING_NUMBER+" INTEGER"+");";

        db.execSQL(query1);

        String query2="CREATE TABLE "+TABLE_BUS_LINES+" ("+COLUMN_LINE+" INTEGER,"+COLUMN_LOCATION_BUS+" TEXT,"
                +COLUMN_STATION_1+" TEXT,"+COLUMN_STATION_2+" TEXT,"+COLUMN_STATION_3+" TEXT,"+COLUMN_STATION_4+" TEXT,"+COLUMN_STATION_5+" TEXT,"+COLUMN_STATION_6+" TEXT,"+COLUMN_STATION_7+" TEXT,"+COLUMN_STATION_8+" TEXT,"+COLUMN_STATION_9+" TEXT,"+COLUMN_STATION_10+" TEXT"+");";

        db.execSQL(query2);


        String query3="CREATE TABLE "+TABLE_STATION_LOCATION_DETAILS+" ("+COLUMN_STATION+" TEXT,"+COLUMN_LATITUDE+" DOUBLE,"
                +COLUMN_LONGITUDE+" DOUBLE"+");";

        db.execSQL(query3);

        Log.d(this.getClass().getName(), "create tables!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER_PERSONAL_DETAILS + ";");
        db.execSQL("DROP TABLE " + TABLE_BUS_LINES + ";");
        db.execSQL("DROP TABLE " + TABLE_STATION_LOCATION_DETAILS + ";");
        onCreate(db);
        Log.d(this.getClass().getName(), "upgrade tables!!!");
    }
    ///////////////////
    public void AddUserDetailsRow(UserTable upd){
        ContentValues values=new ContentValues();
        values.put(COLUMN_FIRSTNAME,upd.getFirstname());
        values.put(COLUMN_LASTNAME,upd.getLastname());
        values.put(COLUMN_EMAIL, upd.getEmail());
        values.put(COLUMN_LOCATION_USER, upd.getLocation());
        values.put(COLUMN_MISSING_NUMBER, upd.getMissingnum());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_USER_PERSONAL_DETAILS, null, values);
        db.close();
        Log.d(this.getClass().getName(), "add user details!!!");
    }

    public void DeleteUserDetailsRow(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER_PERSONAL_DETAILS + ";");
        Log.d(this.getClass().getName(), "delete user details!!!");
    }



    public void AddBusLinesRow(BusLinesTable upd){
        ContentValues values=new ContentValues();
        values.put(COLUMN_LINE,upd.getLine());
        values.put(COLUMN_LOCATION_BUS,upd.getLocation());
        values.put(COLUMN_STATION_1,upd.getStation1());
        values.put(COLUMN_STATION_2,upd.getStation2());
        values.put(COLUMN_STATION_3,upd.getStation3());
        values.put(COLUMN_STATION_4,upd.getStation4());
        values.put(COLUMN_STATION_5,upd.getStation5());
        values.put(COLUMN_STATION_6,upd.getStation6());
        values.put(COLUMN_STATION_7,upd.getStation7());
        values.put(COLUMN_STATION_8,upd.getStation8());
        values.put(COLUMN_STATION_9,upd.getStation9());
        values.put(COLUMN_STATION_10,upd.getStation10());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_BUS_LINES, null, values);
        db.close();
        Log.d(this.getClass().getName(), "add bus line!!!");
    }

    public void DeleteBusLinesRowByLine(int line){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BUS_LINES + " WHERE " + COLUMN_LINE + "='" + line + "';");
        Log.d(this.getClass().getName(), "delete bus line!!!");
    }

    public void AddStationLocationDetailsRow(StationLocationTable ...upd){
        SQLiteDatabase db = getWritableDatabase();
        for(int i=0;i<upd.length;i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_STATION, upd[i].getStation());
            values.put(COLUMN_LATITUDE, upd[i].getLatitude());
            values.put(COLUMN_LONGITUDE, upd[i].getLongitude());

            db.insert(TABLE_STATION_LOCATION_DETAILS, null, values);
        }
        //db.close();
        Log.d(this.getClass().getName(), "add station location!!!");
    }

    public void DeleteStationLocationDetailsRowByStation(String station){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STATION_LOCATION_DETAILS + " WHERE " + COLUMN_LINE + "='" + station + "';");
        Log.d(this.getClass().getName(), "delete station location!!!");
    }
    /////////////////////
    public String UserDBToString(){
        String dbstring="";
        SQLiteDatabase db=getWritableDatabase();
        String query2="SELECT firstname FROM UserDetails;";

        Cursor c=db.rawQuery(query2, null);
        c.moveToFirst();
        dbstring+=c.getString(c.getColumnIndex(COLUMN_FIRSTNAME));
        dbstring+="\n";
        /**
         String query="SELECT * FROM "+TABLE_USER_PERSONAL_DETAILS+" WHERE 1;";
         Cursor c=db.rawQuery(query, null);
         c.moveToFirst();
         while(!c.isAfterLast()){
         if(c.getString(c.getColumnIndex(COLUMN_FIRSTNAME))!=null){
         dbstring+=c.getString(c.getColumnIndex(COLUMN_FIRSTNAME));
         dbstring+="\n";
         }
         }
         **/
        db.close();
        Log.d(this.getClass().getName(), "UserDBToString!!!");
        return dbstring;
    }

    public String BusLinesDBToString(){
        String dbstring="";
        SQLiteDatabase db=getWritableDatabase();
        String query2="SELECT "+COLUMN_LINE+" FROM "+TABLE_BUS_LINES+";";

        Cursor c=db.rawQuery(query2, null);
        c.moveToFirst();
        dbstring+=c.getString(c.getColumnIndex(COLUMN_LINE));
        dbstring+="\n";
        db.close();
        Log.d(this.getClass().getName(), "BusLinesDBToString!!!");
        return dbstring;
    }

    public void DeleteAllTablesContent(){
        SQLiteDatabase db=getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_USER_PERSONAL_DETAILS + ";");
        db.execSQL("DELETE FROM " + TABLE_BUS_LINES + ";");
        db.execSQL("DELETE FROM " + TABLE_STATION_LOCATION_DETAILS + ";");
        Log.d(this.getClass().getName(), "All Tables Content Was Deleted!!!");
    }

    public void UpdateEmailUser(String updateemail){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE UserDetails SET email = '"+updateemail+"';");
        db.close();
        Log.d(this.getClass().getName(), "Email User Was Update!!!");
    }

    public void UpdateLocationUser(String updatelocation){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE UserDetails SET location = '"+updatelocation+"';");
        db.close();
        Log.d(this.getClass().getName(), "Location User Was Update!!!");
    }

    public String GetUserRegion(){
        String dbstring="";
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_USER_PERSONAL_DETAILS+";";

        Cursor c=db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                dbstring+=c.getString(c.getColumnIndex(COLUMN_LOCATION_USER));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        Log.d(this.getClass().getName(), dbstring);
        return dbstring;
    }

    public List<String> GetAllLinesRegion(String userregion){
        List<String> lines = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + COLUMN_LINE + " FROM " + TABLE_BUS_LINES + " WHERE "+ COLUMN_LOCATION_BUS +" = '" + userregion +"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst() && cursor.getCount()>0) {
            do {
                lines.add(Integer.toString(cursor.getInt(cursor.getColumnIndex(COLUMN_LINE))));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        Log.d(this.getClass().getName(), "GetAllStationsRegion, the query is: "+selectQuery+", the lines List is: "+lines.toString());
        // returning lables
        return lines;
    }

    public List<String> GetStationsLine(String stationsline,String userregion){
        List<String> stations = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BUS_LINES + " WHERE "+ COLUMN_LINE +" = '" + stationsline +"' AND " + COLUMN_LOCATION_BUS + " = '" + userregion +"';";

        Log.d(this.getClass().getName(),selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String tempcolumn;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < 10; i++) {
                    int temp=i+1;
                    tempcolumn = "station"+Integer.toString(temp);
                    Log.d(this.getClass().getName(),tempcolumn);
                    stations.add(cursor.getString(cursor.getColumnIndex(tempcolumn)));
                }
            }while (cursor.moveToNext());


        }

        // closing connection
        cursor.close();
        db.close();

        Log.d(this.getClass().getName(), "GetStationsLine, the stations line list is: "+stations.toString());

        return stations;
    }

    public List<String> GetStationLocation(String station){
        List<String> LatAndLong=new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_STATION_LOCATION_DETAILS + " WHERE "+ COLUMN_STATION +" = '" + station + "';";
        Log.d(this.getClass().getName(), "GetStationLocation, the query is: "+selectQuery);


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst() && cursor.getCount()>0) {
            do {
                LatAndLong.add(Double.toString(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE))));
                LatAndLong.add(Double.toString(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE))));
            }while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        Log.d(this.getClass().getName(), "GetStationLocation, the query is: "+selectQuery+" latitude: "+LatAndLong.get(0)+" Longitude: "+LatAndLong.get(1));
        return LatAndLong;
    }

    public int GetMissingCountUser(){
        int missingcount=0;
        String selectQuery = "SELECT "+COLUMN_MISSING_NUMBER+" FROM " + TABLE_USER_PERSONAL_DETAILS + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst() && cursor.getCount()>0) {
            do {
                missingcount=cursor.getInt(cursor.getColumnIndex(COLUMN_MISSING_NUMBER));
            }while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return missingcount;
    }

    public void UpdateMissingCount(){
        int updateusermissingcount= GetMissingCountUser() +1;
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_USER_PERSONAL_DETAILS+" SET "+COLUMN_MISSING_NUMBER+" = '"+updateusermissingcount+"';");
        db.close();
        Log.d(this.getClass().getName(), "Missing Count User Was Update!!!");
    }

}
