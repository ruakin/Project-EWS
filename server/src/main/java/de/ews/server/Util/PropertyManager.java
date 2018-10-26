package de.ews.server.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 *
 */
public class PropertyManager {

    private static final Logger LOGGER = LogManager.getLogger(PropertyManager.class.getClass());

    /** Properties object */
    private final Properties properties = new Properties();

    /** Properties file */
    private final File propertiesFile;

    /**
     * Default constructor
     * 
     * @param serverPropertiesFile
     * @return
     */
    public PropertyManager(File propertiesFile) {
        this.propertiesFile = propertiesFile;

        if (propertiesFile != null) {
            FileInputStream fileInputStream = null;

            try {
                fileInputStream = new FileInputStream(propertiesFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");

                (this.properties).load(inputStreamReader);

            } catch (Exception exception) {
                LOGGER.warn(propertiesFile + ".properties not found, generating ...");
                generatePropertiesFile();
            }

        } else {
            LOGGER.warn("Generating properties");
            generatePropertiesFile();
        }
    }

    private void generatePropertiesFile() {
        try {
            FileOutputStream output = new FileOutputStream(propertiesFile);
            output.close();
        } catch (IOException io) {
            LOGGER.error("Could not generate properties file: " + propertiesFile);
            io.getCause();
            io.printStackTrace();
        }

        LOGGER.info(propertiesFile + ".properties generated");
    }

    /**
     * Stores the properties into the properties file
     */
    public void saveProperties() {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(this.propertiesFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            this.properties.store(outputStreamWriter, null);

        } catch (Exception exception) {
            LOGGER.error("Unable to store properties in file");
        }
    }

    /**
     * If existing, updates the property else create it
     * 
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    public boolean hasProperty(String key) {
        return this.properties.containsKey(key);
    }

    /**
     * Returns an
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(this.getStringProperty(key, "" + defaultValue));
        } catch (Exception exception) {
            this.properties.setProperty(key, "" + defaultValue);
            this.saveProperties();
            return defaultValue;
        }
    }

    /**
     * Returns a boolean. If it doas not exists it is set to the specified value and
     * be returned
     * 
     * @param key
     *            to search for
     * @param defaultValue
     *            if the key does not exists
     * @return the stored boolean or defaultValue
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(this.getStringProperty(key, "" + defaultValue));
        } catch (Exception exception) {
            this.properties.setProperty(key, "" + defaultValue);
            this.saveProperties();
            return defaultValue;
        }
    }

    public String getStringProperty(String key, String defaultValue) {
        if (!this.properties.containsKey(key)) {
            this.properties.setProperty(key, defaultValue);
            this.saveProperties();
        }
        return this.properties.getProperty(key, defaultValue);
    }
}
