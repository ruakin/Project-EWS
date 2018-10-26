package de.ews.server.alarm;

import java.time.LocalDate;
import java.util.List;

import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.patient.Measurement;
import de.ews.server.patient.Patient;
import de.ews.server.station.Station;
import de.ews.server.station.StationList;

public class Check {

    /**
     * Checks a list of patients for critical patients
     * 
     * @param list
     *            To be checked
     * 
     * @see Check.alarm()
     */
    public static void list(List<Patient> list) {
        for (Patient patient : list) {
            Check.patient(patient);

        }
    }

    /**
     * Checks a StationList for critical patients
     * 
     * @param list
     *            To be checked
     * 
     * @see Check.alarm()
     */
    public static void allStations(StationList list) {
        List<Station> stations = list.getStations();

        for (Station station : stations) {
            Check.station(station);
        }
    }

    /**
     * Checks a given Station for critical patients
     * 
     * @param station
     *            To be checked
     * 
     * @see Check.patient()
     */
    public static void station(Station station) {
        List<Patient> list = station.getPatients();

        for (Patient patient : list) {
            Check.patient(patient);

        }
    }

    /**
     * Checks vital signs an returns an alarmcode
     * 
     * @param patient
     *            to be checked
     * @return alarmcode
     */
    public static int getAlarmLevel(Patient patient) {
        int level = 0;

        Measurement current = patient.getCurrentMeasurement();

        if (current == null) {
            return 0;
        }

        Hospitalization hospitalization = patient.getHospitalization();

        if (hospitalization.getMinTemp() > current.getTemperature()
                || hospitalization.getMaxTemp() < current.getTemperature()) {
            level++;
        }

        if (hospitalization.getMinBreathrate() > current.getBreathingRate()
                || hospitalization.getMaxBreathrate() < current.getBreathingRate()) {
            level++;
        }

        if (hospitalization.getMaxSystolic() < current.getSystolic()) {
            level++;
        }

        if (hospitalization.getMinDiastolic() > current.getDiastolic()) {
            level++;
        }

        if (level > 2) {
            level = 2;
        }

        return level;
    }

    /**
     * Checks if the patients vital signs are outside the critical values and
     * triggers an alarm
     * 
     * @param patient
     *            To be checked
     */
    public static void patient(Patient patient) {
        alarmLevel alarmlevel = alarmLevel.GREEN;
        int level = getAlarmLevel(patient); // replaced old code, needed alarmlevel-code for FETCHALL

        switch (level) {
        case 0:
            alarmlevel = alarmLevel.GREEN;
            break;
        case 1:
            alarmlevel = alarmLevel.YELLOW;
            break;
        case 2:
            alarmlevel = alarmLevel.RED;
            break;

        default:
            alarmlevel = alarmLevel.RED;
            break;
        }

        if (alarmlevel != alarmLevel.GREEN) {
            alarm(patient, alarmlevel);
        }
    }

    /**
     * Triggers an email alarm
     * 
     * @param patient
     *            The patient with critical vital signs
     * @param level
     *            The level of the alarm, yellow or red
     */
    public static void alarm(Patient patient, alarmLevel level) {
        Alarm alarm = null;
        String subject = null;
        String body = null;
        String recipient = null;

        switch (level) {
        case YELLOW:
            alarm = new YellowAlarm(patient, LocalDate.now(), patient.getCurrentMeasurement().toString());
            subject = "[Warnung]";
            recipient = patient.getStation().getNurse().getEmail();
            break;

        case RED:
            alarm = new RedAlarm(patient, LocalDate.now(), patient.getCurrentMeasurement().toString());
            subject = "[Kritisch]";
            recipient = patient.getStation().getDoctor().getEmail();
            break;
        default:
            break;
        }

        body = patient.getStation().getName() + ", " + patient.getRoom();
        body += patient.getCurrentMeasurement().toString();

        alarm.viaMail(recipient, subject, body);
    }
}
