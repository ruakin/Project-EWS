package de.ews.server.hospitalization;

import java.time.LocalDate;

import de.ews.server.employee.Doctor;
import de.ews.server.patient.Patient;

public class Hospitalization {

    int       id;
    Patient   patient;
    Doctor    doctor;
    float     maxTemp;
    float     minTemp;
    int       maxSystolic;
    int       minDiastolic;
    int       maxBreathrate;
    int       minBreathrate;
    int       roomId;
    LocalDate start;

    /**
     * 
     * 
     * @param patient
     * @param doctor
     * @param maxTemp
     * @param minTemp
     * @param maxSystolic
     * @param minDiastolic
     */
    public Hospitalization(Patient patient, Doctor doctor, float maxTemp, float minTemp, int maxSystolic,
            int minDiastolic, int maxBreathrate, int minBreathrate, int roomId, LocalDate start) {
        this.patient = patient;
        this.doctor = doctor;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxSystolic = maxSystolic;
        this.minDiastolic = minDiastolic;
        this.maxBreathrate = maxBreathrate;
        this.minBreathrate = minBreathrate;
        this.roomId = roomId;
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public int getMaxSystolic() {
        return maxSystolic;
    }

    public int getMinDiastolic() {
        return minDiastolic;
    }

    public int getMaxBreathrate() {
        return maxBreathrate;
    }

    public int getMinBreathrate() {
        return minBreathrate;
    }

    public void setRoom(int roomId) {
        this.roomId = roomId;
    }

    public int getRoom() {
        return this.roomId;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setId(int id) {
        this.id = id;
    }

}
