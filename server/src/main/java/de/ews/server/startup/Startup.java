package de.ews.server.startup;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.Database;
import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.hospitalization.HospitalizationList;
import de.ews.server.patient.Patient;
import de.ews.server.station.Room;
import de.ews.server.station.Station;
import de.ews.server.station.StationList;

/**
 * 
 */
public class Startup {
    private static final Logger LOGGER      = LogManager.getLogger();
    private static Database     database;
    private static StationList  stationList = new StationList();
    private static Patient      patient;

    public static void startup(DedicatedServer dedicatedServer) throws DoubleEntryException {
        LOGGER.info("Reading database ...");

        database = DedicatedServer.getDatabase();

        // Gets and stores all current employees
        dedicatedServer.setEmployeeList(database.getEmployeeList());

        // Gets all stations with the corresponding enployees
        stationList = database.getStationList();

        HospitalizationList hospitalizationList = database.getCurrentHospitalization();
        List<Hospitalization> hospitalizations = hospitalizationList.getList();
        for (Hospitalization hospitalization : hospitalizations) {

            Station station = database.getStationByRoom(hospitalization.getRoom());

            switch (station.getName()) {
            case "Abteilung A":
                hospitalization.getPatient().setStation(stationList.getStation(1));
                patient = hospitalization.getPatient();
                database.setMeasurementByPatient(patient);
                stationList.getStation(1).addPatient(patient);

                break;
            case "Abteilung B":
                hospitalization.getPatient().setStation(stationList.getStation(2));
                patient = hospitalization.getPatient();
                database.setMeasurementByPatient(patient);
                stationList.getStation(2).addPatient(patient);
                break;
            case "Abteilung C":
                hospitalization.getPatient().setStation(stationList.getStation(3));
                patient = hospitalization.getPatient();
                database.setMeasurementByPatient(patient);
                stationList.getStation(3).addPatient(patient);
                break;
            case "Abteilung D":
                hospitalization.getPatient().setStation(stationList.getStation(4));
                patient = hospitalization.getPatient();
                database.setMeasurementByPatient(patient);
                stationList.getStation(4).addPatient(patient);
                break;
            case "Abteilung E":
                hospitalization.getPatient().setStation(stationList.getStation(5));
                patient = hospitalization.getPatient();
                database.setMeasurementByPatient(patient);
                stationList.getStation(5).addPatient(patient);
                break;

            default:
                break;
            }

        }

        LinkedList<Room> rooms = database.getRooms();

        for (Room room : rooms) {
            stationList.getStation(room.getStation()).addRooms(room);
        }

        DedicatedServer.setStationList(stationList);

        DedicatedServer.setHospitalizationList(hospitalizationList);

        LOGGER.info("Reading database finished");
    }
}