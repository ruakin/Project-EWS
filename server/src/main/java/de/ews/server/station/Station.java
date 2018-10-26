package de.ews.server.station;

import java.util.LinkedList;
import java.util.List;

import de.ews.server.employee.Doctor;
import de.ews.server.employee.Nurse;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.exception.PatientAlreadyExistsException;
import de.ews.server.patient.Patient;

public class Station {

    private int           id;
    private String        name;
    private List<Patient> patients = new LinkedList<>();
    private List<Room>    rooms    = new LinkedList<>();
    private Nurse         nurse;
    private Doctor        doctor;
    private StationList   stationList;

    /**
     * @param id
     * @param name
     * @param nurse
     */
    public Station(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    /**
     * 
     * @param newPatient
     *            Patient to add
     * @throws DoubleEntryException
     */
    public void addPatient(Patient newPatient) throws PatientAlreadyExistsException {
        patients.add(newPatient);
        // DedicatedServer.getDatabase().insertPatient(newPatient);
    }

    /**
     * 
     * @param oldPatient
     */
    public void removePatient(Patient oldPatient) {
        if (hasPatientByID(oldPatient.getId())) {
            patients.remove(oldPatient);
        }
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    /**
     * 
     * @param doctor
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return this.nurse;
    }

    /**
     * 
     * @param nurse
     */
    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public StationList getStationList() {
        return this.stationList;
    }

    /**
     * 
     * @param stationList
     */
    public void setStationList(StationList stationList) {
        this.stationList = stationList;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * @param patients
     *            the patients to set
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    /**
     * @return the rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * @param rooms
     *            the rooms to set
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @param rooms
     *            the rooms to set
     */
    public void addRooms(Room newRoom) {
        this.rooms.add(newRoom);
    }

    public boolean hasPatient(Patient patient) {
        for (Patient patientInList : patients) {
            if (patientInList.equals(patient)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a patient with a given id
     * 
     * @param id
     *            To search for
     * @return True if there is a patient with this id
     */
    public boolean hasPatientByID(int id) {
        for (Patient patientInList : patients) {
            if (patientInList.getId() == id) {
                return true;
            }
        }
        return false;
    }
}