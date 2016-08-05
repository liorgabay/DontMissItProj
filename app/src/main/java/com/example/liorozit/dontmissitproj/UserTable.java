package com.example.liorozit.dontmissitproj;

public class UserTable {
    private String firstname,lastname,email,location;
    private  int missingnum;



    public UserTable(String first,String last,String em,String area,int missnum){
        firstname=first;
        lastname=last;
        email=em;
        location=area;
        missingnum=missnum;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public int getMissingnum() {return missingnum;}

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMissingnum(int missingnum) {
        this.missingnum = missingnum;
    }
}