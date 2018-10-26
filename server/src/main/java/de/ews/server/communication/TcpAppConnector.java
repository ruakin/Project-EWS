package de.ews.server.communication;

import java.net.InetAddress;
import java.io.IOException;

/**
 * 
 * @author Jenne Hilberts
 *
 *         establishes a TCP-Connetcion with the app
 */
public class TcpAppConnector extends AbstractTcpConnector {

    /**
     * 
     * @param address
     *            address of client
     * @throws IOException
     *             if something goes wrong
     */
    public TcpAppConnector(InetAddress address) throws IOException {
        super(address, 8001);
    }

}