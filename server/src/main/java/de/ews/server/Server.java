package de.ews.server;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.dedicated.DedicatedServer;

public class Server {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        LOGGER.info("Starting dedicated Server");

        @SuppressWarnings("unused")
        DedicatedServer dedicatedServer = new DedicatedServer();

        LOGGER.fatal("This should never be reached");
        System.exit(-1);

    }
}
