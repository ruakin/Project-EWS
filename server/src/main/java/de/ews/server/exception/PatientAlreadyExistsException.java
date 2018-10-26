package de.ews.server.exception;

public class PatientAlreadyExistsException extends DoubleEntryException {

    /**
     * 
     */
    private static final long serialVersionUID = 1767778053006899288L;

    public PatientAlreadyExistsException(String i) {
        super("Patient already exists: " + i);
    }

}
