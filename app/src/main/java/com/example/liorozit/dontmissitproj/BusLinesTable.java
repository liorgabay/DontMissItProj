package com.example.liorozit.dontmissitproj;

public class BusLinesTable {
    private int line;
    private String location,station1,station2,station3,station4,station5,station6,station7,station8,station9,station10;

    public BusLinesTable(int linenum,String... s1){
        line=linenum;
        location=s1[0];
        station1=s1[1];
        station2=s1[2];
        station3=s1[3];
        station4=s1[4];
        station5=s1[5];
        station6=s1[6];
        station7=s1[7];
        station8=s1[8];
        station9=s1[9];
        station10=s1[10];
    }

    public String getStation10() {
        return station10;
    }

    public String getStation9() {
        return station9;
    }

    public String getStation8() {
        return station8;
    }

    public String getStation7() {
        return station7;
    }

    public String getStation6() {
        return station6;
    }

    public String getStation5() {
        return station5;
    }

    public String getStation4() {
        return station4;
    }

    public String getStation3() {
        return station3;
    }

    public String getStation2() {
        return station2;
    }

    public String getStation1() {
        return station1;
    }

    public String getLocation() {
        return location;
    }

    public int getLine() {
        return line;
    }

    public void setStation10(String station10) {
        this.station10 = station10;
    }

    public void setStation9(String station9) {
        this.station9 = station9;
    }

    public void setStation8(String station8) {
        this.station8 = station8;
    }

    public void setStation7(String station7) {
        this.station7 = station7;
    }

    public void setStation6(String station6) {
        this.station6 = station6;
    }

    public void setStation5(String station5) {
        this.station5 = station5;
    }

    public void setStation4(String station4) {
        this.station4 = station4;
    }

    public void setStation3(String station3) {
        this.station3 = station3;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLine(int line) {
        this.line = line;
    }
}