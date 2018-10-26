package de.ews.server.alarm;

import java.time.LocalDate;

import de.ews.server.patient.Patient;

public class RedAlarm extends Alarm {

    public RedAlarm(Patient patient, LocalDate localDate, String message) {
        super(patient, localDate, message);
        this.alarmLevel = alarmLevel.RED;
    }

}