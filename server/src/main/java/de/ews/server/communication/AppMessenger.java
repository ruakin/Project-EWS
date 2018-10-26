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
 * @author Jenne Hilberts
 * 
 *         reads an writes on the network
 */
public class AppMessenger implements IMessenger, Closeable {

    private static final Logger LOGGER = LogManager.getLogger(AppMessenger.class);
    private OutputStream        out;
    private InputStream         in;
    private LinkedList<String>  list;
    private String              latest;
    private Scanner             sc;

    /**
     *
     * @param connector
     *            connector to the client
     */
    public AppMessenger(ITcpConnector connector) {
        out = connector.getOutputStream();
        in = connector.getInputStream();
        list = new LinkedList<String>();
        latest = "";
    }

    /**
     * @return returns splitted message
     */
    @Override
    public String getNextMessage() {
        sc = new Scanner(in);
        sc.useDelimiter("}");
        while (list.size() == 0 && sc.hasNext()) {
            latest += sc.next() + "}";
            if (countChar(latest, '{') == countChar(latest, '}')) {
                list.add(latest);
                latest = "";
            }
        }
        if (list.size() != 0) {
            LOGGER.info(list.get(0));
            return list.removeFirst();
        }
        return null;
    }

    /**
     * @param message
     *            message2write
     */
    @Override
    public void sendMessage(String message) {
        try {
            out.write(message.getBytes());
        } catch (IOException e) {
            LOGGER.error("could not send Message");
            e.printStackTrace();
        }
    }

    /**
     * @param s
     *            String2search
     * @param c
     *            char2look
     * @return count of appearance
     * 
     *         helper-method for counting how often a specific character c in a
     *         String s appears
     */
    private int countChar(String s, char c) {
        int ret = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c)
                ret++;
        }
        return ret;
    }

    @Override
    @Deprecated
    public void close() throws IOException {
        if (sc != null) {
            sc.close();
        }
    }

}
