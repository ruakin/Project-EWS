package de.ews.server.communication;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author student
 *
 */
public class ViewerMessenger implements IMessenger, Closeable {

    private static final Logger LOGGER = LogManager.getLogger(ViewerMessenger.class);
    private OutputStream        out;
    private InputStream         in;
    private LinkedList<String>  list;
    private Scanner sc;

    public ViewerMessenger(ITcpConnector connector) {
        in = connector.getInputStream();
        out = connector.getOutputStream();
        list = new LinkedList<String>();
    }

    @Override
    public String getNextMessage() {
        sc = new Scanner(in);
        sc.useDelimiter(";");
        String debug;

        /*
         * Possible blocking error
         */
        // FIXME
        if (list.size() == 0) {
            if (sc.hasNext()) {
                list.add(debug = sc.next());
                LOGGER.trace("received: " + debug);
            }
        }

        if (!list.isEmpty()) {
            LOGGER.info(list.getFirst());
            debug = list.removeFirst();
            LOGGER.trace("from list: " + debug);
            return debug;
        }

        return null;
    }

    @Override
    public void sendMessage(String text) {
        try {
            out.write(text.getBytes());
        } catch (IOException e) {
            LOGGER.error("could not send Message");
            e.printStackTrace();
        }
    }

	@Override
	@Deprecated
	public void close() throws IOException {
		sc.close();
	}

}
