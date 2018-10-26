package de.ews.server.exception;

public class DoubleEntryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7843267619818300534L;

    public DoubleEntryException(String string) {
        super("Entry already exists in database: " + string);
    }
}
