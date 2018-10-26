package com.example.rukiye.myapplication;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Messure {

    private String hospid;
    private String name;
    private String surname;
    private String breathrate;
    private String temp;
    private String systolic;
    private String diastolic;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sdf.format(new Date());

    //station
    public String getHospid() {
        return hospid;
    }

    public void setHospId(String hospid) {
        this.hospid = hospid;
    }

    //name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //surname
   public String getSurname() {
        return surname;
    }

    public void setSurname(String surname ) {
        this.surname = surname;
    }

    //breathrate
    public String getbreathrate() {
        return breathrate;
    }

    public void setBreathrate(String breathrate) {
        this.breathrate = breathrate;
    }

    //temp
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    //systolic
    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    //diastolic
    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    //time
    public String getTime() {
        return time;
    }




    @Override
    public String toString() {
        return "Messure [name=" + name +
                ", surname=" + surname +
                ", breathrate=" + breathrate +
                ", temp=" + temp +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", time=" + time +
                "]";
    }


}