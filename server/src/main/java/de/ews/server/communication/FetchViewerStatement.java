package de.ews.server.communication;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchViewerStatement extends AbstractStatement {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void perform() throws NullPointerException {
        String response = "FETCH";
        FetchItem item = null;
        while (!Util.fetchList.isEmpty())
        {
        	item = Util.fetchList.poll();
        	response += "," + item.getPatientId() + "," + item.getAlarm();
        	
        }
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
