package de.ews.server.communication;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.patient.Patient;
import de.ews.server.station.Station;

public class SelectStationStatement extends AbstractStatement {

    private String              station;
    private static final Logger LOGGER = LogManager.getLogger();

    public SelectStationStatement(String s) {
        station = s;
    }

    @Override
    public void perform() throws NullPointerException {
        LOGGER.info("perform: station select");

        JsonArrayBuilder buildArray = Json.createArrayBuilder();
        JsonObjectBuilder buildObject;
        Iterator<Station> stationIt = DedicatedServer.getStationList().getStations().iterator();
        Iterator<Patient> patientIt = null;
        Station station = null;
        // Patient patient = null;

        List<Station> listOfStation = DedicatedServer.getStationList().getStations();
        for (Station station2 : listOfStation) {
            if (station2.getName().equals(this.station)) {
                List<Patient> patients = station2.getPatients();
                for (Patient patient : patients) {
                    buildObject = Json.createObjectBuilder();
                    buildObject.add("hospid", patient.getHospitalization().getId());
                    buildObject.add("name", patient.getForename());
                    buildObject.add("surname", patient.getName());
                    buildObject.add("roomname", patient.getRoom());
                    buildObject.add("stationname", patient.getStation().getName());
                    buildArray.add(buildObject);
                }
            }
        }
        JsonArray array = buildArray.build();

        // while (stationIt.hasNext()) {
        // station = stationIt.next();
        // if (station.getName().equals(this.station)) {
        // patientIt = station.getPatients().iterator();
        // break;
        // }
        // }
        // if (patientIt != null) {
        // buildArray = Json.createArrayBuilder();
        // while (patientIt.hasNext()) {
        // patient = patientIt.next();
        // buildObject = Json.createObjectBuilder();
        // buildObject.add("hospid", patient.getHospitalization().getId());
        // buildObject.add("name", patient.getForename());
        // buildObject.add("surname", patient.getName());
        // buildObject.add("roomname", patient.getRoom());
        // buildObject.add("stationname", patient.getStation().getName());
        // buildArray.add(buildObject);
        // }
        // JsonArray array = buildArray.build();

        try {
            CommunicationFactory.getAppConnector().getOutputStream().write(array.toString().getBytes());
            LOGGER.info("Send: " + array.toString());
        } catch (IOException e) {
            LOGGER.error("Could not execute, response should be: ", e);
        }
        // } else {
        // LOGGER.error("Station does not exist: " + this.station);
        // }
    }

    public boolean hasAnswer() {
        return true;
    }

}
