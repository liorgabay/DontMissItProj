package com.example.liorozit.dontmissitproj;

public class StationLocationTable {
    private String station;
    private double latitude,longitude;

    public StationLocationTable(String stationname,double latitudepos,double longitudepos){
        station=stationname;
        latitude=latitudepos;
        longitude=longitudepos;
    }

    public String getStation() {
        return station;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
