package de.ews.server.communication;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.dedicated.DedicatedServer;

public class DeleteViewerStatement extends AbstractStatement {

    private int                 patientid;
    private static final Logger LOGGER = LogManager.getLogger();

    public DeleteViewerStatement(int patientid) {
        this.patientid = patientid;
    }

    @Override
    public void perform() throws NullPointerException {
        LOGGER.info("Dismissung id: " + patientid);
        String response;
        response = DedicatedServer.dismissPatientByID(this.patientid) ? "DELETE_SUCCEDED;" : "DELETE_FAILED;";
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
