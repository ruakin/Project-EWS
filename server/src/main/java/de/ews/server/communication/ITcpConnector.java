package de.ews.server.communication;

import java.io.InputStream;
import java.io.OutputStream;

public interface ITcpConnector {

    public InputStream getInputStream();

    public OutputStream getOutputStream();

}