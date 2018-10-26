package de.ews.server.alarm;

import java.time.LocalDate;

import de.ews.server.Util.Mail;
import de.ews.server.patient.Patient;

public abstract class Alarm {

    Patient    patient;
    LocalDate  localDate;
    String     message;
    alarmLevel alarmLevel;

    public Alarm(Patient patient, LocalDate localDate, String message) {
        this.patient = patient;
        this.localDate = localDate;
        this.message = message;
    }

    /**
     * Use single target methods instead
     * 
     * @param patient
     */
    @Deprecated
    public void alarm(String to, String subject, String body) {
        viaMail(to, subject, body);
        viaViewer(to, subject, body);
        viaApp(to, subject, body);
    }

    /**
     * 
     * @param patient
     */
    public void viaMail(String to, String subject, String body) {
        new Mail(to, subject, body);
    }

    /**
     * 
     * @param patient
     */
    public void viaApp(String to, String subject, String body) {
        // TODO - implement Alarm.viaApp
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param patient
     */
    public void viaViewer(String to, String subject, String body) {
        // TODO - implement Alarm.viaApp
        throw new UnsupportedOperationException();
    }

    public void logToDatabase() {
        // TODO - implement Alarm.logToDatabase
        throw new UnsupportedOperationException();
    }

}