package de.ews.server.communication;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 
 * @author Jenne Hilberts
 *
 *         establishes a TCP-Connetcion with the viewer
 */
public class TcpViewerConnector extends AbstractTcpConnector {

    /**
     * 
     * @param address
     *            address of client
     * @throws IOException
     *             if something goes wrong
     */
    public TcpViewerConnector(InetAddress address) throws IOException {
        super(address, 8000);
    }
}