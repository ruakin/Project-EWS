package com.example.rukiye.myapplication;

public class Patient {

    private int hospid;
    private String name;
    private String surname;
    private String roomname;
    private String station;


    public Patient( int  hospid,String name, String surname,String roomname,String stationname){

        this.hospid = hospid;
        this.name = name;
        this.surname = surname;
        this.roomname = roomname;
        this.station = station;

    }

    public int getHospid() {
        return hospid;
    }

    public String getName() {
        return name;
    }

    public String getRoomname() {
        return roomname;
    }

    public String getSurname(){
        return surname;
    }

    public String getStationname() {
        return station;
    }

    @Override
    public boolean equals(Object other) {

        if (other == null)
            return false;
        if (!(other instanceof Patient))
            return false;
        Patient p = (Patient)other;
        return p.getHospid() == this.getHospid();
    }

}
