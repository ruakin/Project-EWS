package de.ews.server.exception;

public class NoAvailableBedsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -351101569015705944L;

    public NoAvailableBedsException() {
        super("No bed available");
    }

}
