package de.ews.server.patient;

import java.time.LocalDate;
import java.time.Period;

import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.station.Station;

public class Patient {

    private int             id;
    private String          name;
    private String          forename;
    private LocalDate       birthday;
    private String          insuranceID;
    private Measurement     lastMeasurement;
    private Measurement     currentMeasurement;
    private Hospitalization hospitalization;
    private boolean         isUpdated;
    private Station         station;
    private int             room;
    private boolean         hasUpdate = false;

    /**
     * @param forename
     * @param name
     * @param birthday
     *            in format YYYY-MM-DD
     */
    public Patient(String forename, String name, String birthday, String insuranceID) {
        this.forename = forename;
        this.name = name;
        this.birthday = LocalDate.parse(birthday);
        this.insuranceID = insuranceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgeInYears() {
        return Period.between(this.birthday, LocalDate.now()).getYears();

    }

    public String getForename() {
        return forename;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public String getName() {
        return this.name;
    }

    public Measurement getLastMeasurement() {
        return this.lastMeasurement;
    }

    /**
     * 
     * @param lastMeasurement
     */
    public void setLastMeasurement(Measurement lastMeasurement) {
        this.lastMeasurement = lastMeasurement;
        hasUpdate = true;
    }

    public Station getStation() {
        return this.station;
    }

    public Measurement getCurrentMeasurement() {
        return this.currentMeasurement;
    }

    public void setCurrentMeasurement(Measurement measurement) {
        this.currentMeasurement = measurement;
    }

    /**
     * 
     * @param station
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * @return the insuranceID
     */
    public String getInsuranceID() {
        return insuranceID;
    }

    /**
     * @return the hospitalization
     */
    public Hospitalization getHospitalization() {
        return hospitalization;
    }

    /**
     * @param hospitalization
     *            the hospitalization to set
     */
    public void setHospitalization(Hospitalization hospitalization) {
        this.hospitalization = hospitalization;
    }

    /**
     * Two patients are equal only if the insurance id is the same
     * 
     * @param other
     *            The Patient to check for equality
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient patient = (Patient) other;

        if (this.getInsuranceID() == patient.getInsuranceID()) {
            return true;
        }

        return false;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int roomId) {
        this.room = roomId;
    }

    /**
     * 
     * Checks if two patients havethe same id
     * 
     * @param other
     * @return
     */
    public boolean equalsID(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient patient = (Patient) other;

        if (this.getId() == patient.getId()) {
            return true;
        }

        return false;
    }

    public boolean hasUpdate() {
        return hasUpdate;
    }

    public void setUpdate(boolean update) {
        this.hasUpdate = update;
    }
}