package de.ews.server.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Jenne Hilberts
 *
 *         creates connections to clients Subclasses should just overwrite the
 *         constructor
 */
public abstract class AbstractTcpConnector implements ITcpConnector {

    private static final Logger LOGGER = LogManager.getLogger(AbstractTcpConnector.class);
    protected Socket            socket;
    protected ServerSocket      ssocket;

    /**
     * 
     * @param address
     *            address of the client
     * @param port
     *            port for listening
     * @throws IOException
     *             (if some shit goes wrong)
     */
    public AbstractTcpConnector(InetAddress address, int port) throws IOException {
        ssocket = new ServerSocket(port);
        socket = ssocket.accept();

        LOGGER.info("Created Socket IP: " + ssocket.getInetAddress().getCanonicalHostName() + ", Port: "
                + ssocket.getLocalPort() + "");
    }

    /**
     * @return gets an InputStream for reading
     */
    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            LOGGER.error("could not get an inputstream");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return gets an Outputstream for writing
     */
    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            LOGGER.error("could not get an outputstream");
            e.printStackTrace();
            return null;
        }
    }

}
