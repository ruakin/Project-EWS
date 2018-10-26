package de.ews.server.communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Jenne Hilberts
 * @date 10.06.2018
 *
 *       This class should be used to create/get instances from the Connector,
 *       Messenger and Interpret classes
 */
public class CommunicationFactory {

    private static final Logger LOGGER = LogManager.getLogger(CommunicationFactory.class);

    private static TcpAppConnector    appconnect;
    private static TcpViewerConnector viewerconnect;
    private static AppMessenger       appmess;
    private static ViewerMessenger    viewermess;
    private static AppInterpret       appinter;
    private static ViewerInterpret    viewerinter;

    /**
     * 
     * @return instance of AppConnector
     */
    public static TcpAppConnector getAppConnector() {
        if (appconnect == null) {
            try {
                appconnect = new TcpAppConnector(InetAddress.getLocalHost());
                System.out.println("Appconnector exists");
            } catch (UnknownHostException e) {
                LOGGER.error("getAppConnector():unknownhostexception");
                e.printStackTrace();
            } catch (IOException e) {
                LOGGER.error("getAppConnector():ioexception");
                e.printStackTrace();
            }
        }
        return appconnect;
    }

    /**
     * 
     * @return instance of AppMessenger
     */
    public static AppMessenger getAppMessenger() {
        if (appmess == null) {
            appmess = new AppMessenger(getAppConnector());
            System.out.println("Appmessenger exists");
        }
        return appmess;
    }

    /**
     * 
     * @return instance of AppInterpret
     */
    public static IInterpret getAppInterpret() {
        if (appinter == null) {
            appinter = new AppInterpret(getAppMessenger());
            System.out.println("Appinter exists");
        }
        return appinter;
    }

    /**
     * 
     * @return instance of ViewerConnector
     */
    public static TcpViewerConnector getViewerConnector() {
        if (viewerconnect == null) {
            try {
                viewerconnect = new TcpViewerConnector(InetAddress.getLocalHost());
                System.out.println("viewerconnector exists");
            } catch (UnknownHostException e) {
                LOGGER.error("getViewerConnector():unknownhostexception");
                e.printStackTrace();
            } catch (IOException e) {
                LOGGER.error("getViewerConnector():ioexception");
                e.printStackTrace();
            }
        }
        return viewerconnect;
    }

    /**
     * 
     * @return instance of ViewerMessenger
     */
    public static ViewerMessenger getViewerMessenger() {
        if (viewermess == null) {

            viewermess = new ViewerMessenger(getViewerConnector());
            System.out.println("viewermessenger exists");
        }
        return viewermess;
    }

    /**
     * 
     * @return instance of ViewerConnector
     */
    public static IInterpret getViewerInterpret() {
        if (viewerinter == null) {
            viewerinter = new ViewerInterpret(getViewerMessenger());
            LOGGER.info("ViewerInterpret started");
        }
        return viewerinter;
    }

}
