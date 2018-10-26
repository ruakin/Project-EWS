package de.ews.server.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Measurement {
    private static final Logger LOGGER = LogManager.getLogger();

    private int     id;
    private Patient patient;

    private float  temperature;
    private int    systolic;
    private int    diastolic;
    private int    breathingRate;
    private String message;
    private Date   time;

    public Measurement(float temperature, int systolic, int diastolic, int breathingRate, String message, String time) {
        this.temperature = temperature;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.breathingRate = breathingRate;
        this.message = message;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            this.time = simpleDateFormat.parse(time);
        } catch (ParseException parseException) {
            LOGGER.error("Could not parse time to date", parseException);
        }
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getSystolic() {
        return systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public int getBreathingRate() {
        return breathingRate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (getMessage() != null) {
            stringBuilder.append("Nachricht: " + getMessage() + "\n");
        }

        stringBuilder.append(
                "" + patient.getName() + ", " + patient.getForename() + ", Alter " + patient.getAgeInYears() + "\n");
        stringBuilder.append("Temp: " + getTemperature() + "\n");
        stringBuilder.append("Systolisch: " + getSystolic() + "\n");
        stringBuilder.append("Diastolisch: " + getDiastolic() + "\n");
        stringBuilder.append("Atmung: " + getBreathingRate());

        return stringBuilder.toString();
    }
}
