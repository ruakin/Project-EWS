package de.ews.server.communication;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.alarm.Check;
import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.patient.Patient;
import de.ews.server.station.Station;

public class FetchAllViewerStatement extends AbstractStatement {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void perform() throws NullPointerException {
        String response = "FETCHALL,5,12345,max,mustermann,AbteilungA,A110,3,1997-09-02;";
        response = "FETCHALL";

        List<Station> stations = DedicatedServer.getStationList().getStations();

        for (Station station : stations) {
            List<Patient> patients = station.getPatients();
            for (Patient patient2 : patients) {
                response += "," + patient2.getId() + "," + patient2.getInsuranceID() + "," + patient2.getForename()
                        + "," + patient2.getName() + "," + patient2.getStation().getName().trim() + ","
                        + patient2.getRoom() + "," + (Check.getAlarmLevel(patient2) + 1) + ","
                        + patient2.getBirthday().toString();
            }
        }

        response = response.replace(" ", "");

        // while (stationIt.hasNext()) {
        // patientIt = stationIt.next().getPatients().iterator();
        // while (patientIt.hasNext()) {
        // patient = patientIt.next();
        // response += "," + patient.getId() + "," + patient.getInsuranceID() + "," +
        // patient.getForename() + ","
        // + patient.getName() + "," + patient.getStation().getName().trim() + "," +
        // patient.getRoom()
        // + "," + Check.getAlarmLevel(patient) + "," +
        // patient.getBirthday().toString();
        // }
        // }

        response += ";";
        try {
            CommunicationFactory.getViewerConnector().getOutputStream().write(response.getBytes());
            LOGGER.info("Send: " + response);
        } catch (IOException e) {
            LOGGER.error("Could not execute, response should be: " + response, e);
        }
    }

    @Override
    public boolean hasAnswer() {
        return true;
    }

}
