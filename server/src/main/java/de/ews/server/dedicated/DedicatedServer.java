package de.ews.server.dedicated;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.Database;
import de.ews.server.Util.PropertyManager;
import de.ews.server.alarm.Check;
import de.ews.server.communication.CommunicationFactory;
import de.ews.server.communication.IInterpret;
import de.ews.server.employee.Doctor;
import de.ews.server.employee.EmployeeList;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.exception.NoAvailableBedsException;
import de.ews.server.exception.PatientAlreadyExistsException;
import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.hospitalization.HospitalizationList;
import de.ews.server.patient.Measurement;
import de.ews.server.patient.Patient;
import de.ews.server.startup.Startup;
import de.ews.server.station.Station;
import de.ews.server.station.StationList;

public class DedicatedServer {
    private static final Logger    LOGGER = LogManager.getLogger();
    private static PropertyManager propertyManager;

    private static Database            database;
    private static StationList         stationList;
    private static EmployeeList        employeeList;
    private static HospitalizationList hospitalizationList;

    public DedicatedServer() throws ClassNotFoundException, SQLException {
        init();

        // testtesttest();

        Thread appThread = new Thread(new Runnable() { // App-Thread
            public void run() {
                IInterpret interpret = CommunicationFactory.getAppInterpret();
                while (true) {
                    try {
                        interpret.getStatement().asynchronousPerform();
                    } catch (NullPointerException e) {
                        // LOGGER.info("App disconnected");
                    }
                }
            }
        });
        appThread.start();

        Thread viewerThread = new Thread(new Runnable() { // Viewer-Thread
            public void run() {
                IInterpret interpret = CommunicationFactory.getViewerInterpret();
                while (true) {
                    try {
                        interpret.getStatement().asynchronousPerform();
                    } catch (NullPointerException e) {
                        // LOGGER.info("Viewer disconnected");
                    }
                }
            }
        });

        viewerThread.start();

        Thread endless = new Thread(new Runnable() { // Viewer-Thread
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            synchronized (endless) {
                endless.wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // while (true) {
        // }

    }

    /**
     * Initializes the dedicated server
     * 
     * @throws SQLException
     *             Will be thrown if there was an sql error
     * @throws ClassNotFoundException
     */
    private void init() throws SQLException, ClassNotFoundException {

        LOGGER.info("Starting server");
        DedicatedServer.propertyManager = new PropertyManager(new File("config.properties"));

        String filename = DedicatedServer.propertyManager.getStringProperty("database-config-file", "server.sqlite3");

        try {
            this.setDatabase(new Database(filename));
        } catch (SQLException sqlException) {
            LOGGER.error("An error occured during database connection establishment", sqlException);
            System.exit(-1);
        } catch (ClassNotFoundException classNotFoundException) {
            LOGGER.error("Class not found error", classNotFoundException);
            System.exit(-1);
        }

        try {
            Startup.startup(this);
        } catch (DoubleEntryException doubleEntryException) {
            LOGGER.fatal("Could not initialize memory", doubleEntryException);
        }

        // List<Hospitalization> lists = hospitalizationList.getList();
        // for (Hospitalization hospitalization : lists) {
        // Check.patient(hospitalization.getPatient());
        // }

        LOGGER.info("Initializing finished");
    }

    /**
     * @return the employeeList
     */
    public static EmployeeList getEmployeeList() {
        return employeeList;
    }

    /**
     * @param employeeList
     *            the employeeList to set
     */
    public void setEmployeeList(EmployeeList employeeList) {
        DedicatedServer.employeeList = employeeList;
    }

    /**
     * @return the stationList
     */
    public static StationList getStationList() {
        return stationList;
    }

    /**
     * @param stationList
     *            the stationList to set
     */
    public static void setStationList(StationList stationList) {
        DedicatedServer.stationList = stationList;
    }

    public static PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public static void setPropertyManager(PropertyManager propertyManager) {
        DedicatedServer.propertyManager = propertyManager;
    }

    /**
     * @return the database
     */
    public static Database getDatabase() {
        return database;
    }

    /**
     * @param database
     *            the database to set
     */
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * Adds a new patient into the hospital. Fills stations in numerical order
     * 
     * @param newPatient
     *            Patient to be added
     * @throws PatientAlreadyExistsException
     *             If there is already a patient with this insurance id
     * @throws DoubleEntryException
     *             If there is already this patient saved in the database
     * @throws NoAvailableBedsException
     *             If there are no more rooms available
     */
    public static int addPatient(Patient newPatient)
            throws PatientAlreadyExistsException, DoubleEntryException, NoAvailableBedsException {
        List<Station> stations = DedicatedServer.getStationList().getStations();

        for (Station station : stations) {
            List<Patient> list = station.getPatients();

            for (Patient patient : list) {
                if (patient.equals(newPatient)) {
                    throw new PatientAlreadyExistsException(newPatient.getInsuranceID());
                }
            }
        }

        for (Station station : stations) {
            if (station.getPatients().size() < 20) {
                station.addPatient(newPatient);
                break;
            } else {
                if (station.getId() == stations.size()) {
                    throw new NoAvailableBedsException();
                }
            }
        }

        String insuranceID = String.valueOf(newPatient.getInsuranceID());
        Patient patient = database.getPatient(insuranceID);

        return patient.getId();

    }

    /**
     * Dismisses a patient and sets an end date
     * 
     * @param patient
     *            To be dismissed
     */
    public static void dismissPatient(Patient patient) {
        List<Station> stations = DedicatedServer.getStationList().getStations();

        for (Station station : stations) {
            if (station.hasPatient(patient)) {
                DedicatedServer.getDatabase().dismissPatient(patient, LocalDate.now());
                station.removePatient(patient);
            }
        }

    }

    /**
     * Dismisses a patient and sets an end date
     * 
     * @param patient
     *            To be dismissed
     * 
     * @return True if patient could be deleted
     */
    public static boolean dismissPatientByID(int id) {
        LOGGER.info("Dismissing patient:" + id);
        List<Station> stations = DedicatedServer.getStationList().getStations();

        for (Station station : stations) {
            if (station.hasPatientByID(id)) {

                Patient patient = getPatient(id);
                DedicatedServer.getDatabase().dismissPatient(patient, LocalDate.now());
                station.removePatient(patient);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a patient by its id
     * 
     * @param hospID
     * @return The patient
     */
    public static Patient getPatient(int id) {
        List<Station> stations = DedicatedServer.getStationList().getStations();

        for (Station station : stations) {
            List<Patient> patients = station.getPatients();

            for (Patient patient : patients) {
                if (patient.getId() == id) {
                    return patient;
                }
            }
        }

        return null;
    }

    /**
     * Returns a patient by its hospID
     * 
     * @param hospID
     * @return The patient
     */
    public static Patient getPatientByHospIdid(int id) {
        {
            List<Station> stations = DedicatedServer.getStationList().getStations();

            for (Station station : stations) {
                List<Patient> patients = station.getPatients();

                for (Patient patient : patients) {
                    if (patient.getHospitalization().getId() == id) {
                        return patient;
                    }
                }
            }

            return null;
        }
    }

    public static Patient addHospilatization(Hospitalization newHospitalization)
            throws PatientAlreadyExistsException, NoAvailableBedsException {

        Patient patient = newHospitalization.getPatient();
        Doctor doctor = employeeList.getRandomDoctor();
        float maxTemp = newHospitalization.getMaxTemp();
        float minTemp = newHospitalization.getMinTemp();
        int maxSystolic = newHospitalization.getMaxSystolic();
        int minDiastolic = newHospitalization.getMinDiastolic();
        int maxBreathrate = newHospitalization.getMaxBreathrate();
        int minBreathrate = newHospitalization.getMinBreathrate();

        int roomId = database.getAvailableRoom();
        patient.setRoom(roomId);

        if (roomId > 100 || roomId < 1) {
            throw new NoAvailableBedsException();
        }

        Station station = database.getStationByRoom(roomId);
        patient.setStation(station);
        DedicatedServer.getStationList().getStation(station.getId()).addPatient(patient);

        Hospitalization hospitalization = new Hospitalization(patient, doctor, maxTemp, minTemp, maxSystolic,
                minDiastolic, maxBreathrate, minBreathrate, roomId, null);

        database.addHospitalization(hospitalization);
        patient.setHospitalization(hospitalization);

        return hospitalization.getPatient();
    }

    public static void addMeasurement(Patient patient, Measurement measurement) {
        patient.setCurrentMeasurement(measurement);

        database.addMeasurement(patient, measurement);

        patient.setLastMeasurement(patient.getCurrentMeasurement());

        Check.patient(measurement.getPatient());
    }

    /**
     * Get all updates patients
     * 
     * @return All patients with updates
     */
    public static List<Patient> getUpdatedPatients() {
        List<Station> list = stationList.getStations();
        LinkedList<Patient> patients = new LinkedList<>();

        for (Station station : list) {
            List<Patient> patientList = station.getPatients();

            for (Patient patient : patientList) {
                if (patient.hasUpdate()) {
                    patients.add(patient);
                    patient.setUpdate(false);
                }
            }
        }

        return patients;
    }

    /* REMOVE SECTION LATER */
	/* DUMMY DATA */
    public void testAddPatientTooMany()
            throws NoAvailableBedsException, PatientAlreadyExistsException, DoubleEntryException {
        List<Patient> list = new LinkedList<>();

        for (int i = 0; i < 105; i++) {
            String forename = getForename();
            String name = getName();
            String birthday = getBirthday();

            try {
                list.add(new Patient(forename, name, birthday, String.valueOf(i)));
                System.out.println("Erstellt :" + i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        int i = 0;
        for (Patient patient : list) {
            DedicatedServer.addPatient(patient);
            System.out.println("Eingefügt: " + i++);
        }

    }

    public static String getForename() {
        String[] forenames = { "Allison", "Arthur", "Ana", "Alex", "Arlene", "Alberto", "Barry", "Bertha", "Bill",
                "Bonnie", "Bret", "Beryl", "Chantal", "Cristobal", "Claudette", "Charley", "Cindy", "Chris", "Dean",
                "Dolly", "Danny", "Danielle", "Dennis", "Debby", "Erin", "Edouard", "Erika", "Earl", "Emily", "Ernesto",
                "Felix", "Fay", "Fabian", "Frances", "Franklin", "Florence", "Gabielle", "Gustav", "Grace", "Gaston",
                "Gert", "Gordon", "Humberto", "Hanna", "Henri", "Hermine", "Harvey", "Helene", "Iris", "Isidore",
                "Isabel", "Ivan", "Irene", "Isaac", "Jerry", "Josephine", "Juan", "Jeanne", "Jose", "Joyce", "Karen",
                "Kyle", "Kate", "Karl", "Katrina", "Kirk", "Lorenzo", "Lili", "Larry", "Lisa", "Lee", "Leslie",
                "Michelle", "Marco", "Mindy", "Maria", "Michael", "Noel", "Nana", "Nicholas", "Nicole", "Nate",
                "Nadine", "Olga", "Omar", "Odette", "Otto", "Ophelia", "Oscar", "Pablo", "Paloma", "Peter", "Paula",
                "Philippe", "Patty", "Rebekah", "Rene", "Rose", "Richard", "Rita", "Rafael", "Sebastien", "Sally",
                "Sam", "Shary", "Stan", "Sandy", "Tanya", "Teddy", "Teresa", "Tomas", "Tammy", "Tony", "Van", "Vicky",
                "Victor", "Virginie", "Vince", "Valerie", "Wendy", "Wilfred", "Wanda", "Walter", "Wilma", "William",
                "Kumiko", "Aki", "Miharu", "Chiaki", "Michiyo", "Itoe", "Nanaho", "Reina", "Emi", "Yumi", "Ayumi",
                "Kaori", "Sayuri", "Rie", "Miyuki", "Hitomi", "Naoko", "Miwa", "Etsuko", "Akane", "Kazuko", "Miyako",
                "Youko", "Sachiko", "Mieko", "Toshie", "Junko" };

        return forenames[new Random().nextInt(forenames.length)];
    }

    public static String getName() {
        String[] forenames = { "Müller", "Meyer", "Eilers", "Jansesn", "Schröder", "Ahlers", "Burns", "Behrens",
                "Harms", "Albers", "Kramer", "Schmidt", "Oltmanns", "Make", "Kruse", "Becker", "Cordes", "Janßens",
                "Schwarting", "Schütte", "Frerichs", "Deters", "Meinsers", "Menke", "Lange", "Olberding", "Bartelme",
                "Durban", "Holmes", "Herrel", "Britz", "Christmann", "Grüßinger", "Eppinger", "Großkreuz", "Hilberts",
                "McConner", "Onkh", "Weil", "Guggenheim", "Kahn", "Neuburger", "Foucher", "Einstein", "Moss", "Bloch",
                "Rosenthal", "Mayer", "Maier", "Kohm", "Rothschild", "Schwarz", "Rot", "Levi", "Landauer", "Rosenbauer",
                "Dräger", "Levy", "Steiner", "Hirsch", "Stern", "Kyle", "Reichenbach", "Low", "Peter", "Bernheim",
                "Brunner", "Adler", "Hirschfeld" };

        return forenames[new Random().nextInt(forenames.length)];
    }

    public static String getBirthday() {
        GregorianCalendar gc = new GregorianCalendar();

        int yearInBetween = randBetween(1900, 2010);

        gc.set(Calendar.YEAR, yearInBetween);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

        String year = "" + gc.get(Calendar.YEAR);
        String month = String.format("%02d", gc.get(Calendar.MONTH) + 1);
        String day = String.format("%02d", gc.get(Calendar.DAY_OF_MONTH));

        return year + "-" + month + "-" + day;
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    /**
     * @return the hospitalizationList
     */
    public static HospitalizationList getHospitalizationList() {
        return hospitalizationList;
    }

    /**
     * @param hospitalizationList
     *            the hospitalizationList to set
     */
    public static void setHospitalizationList(HospitalizationList hospitalizationList) {
        DedicatedServer.hospitalizationList = hospitalizationList;
    }

    public void testtesttest() {
        List<Patient> list = new LinkedList<>();

        for (int i = 0; i < 105; i++) {
            String forename = getForename();
            String name = getName();
            String birthday = getBirthday();

            try {
                Patient patient = new Patient(forename, name, birthday, String.valueOf(i));

                Hospitalization hospitalization = new Hospitalization(patient, employeeList.getRandomDoctor(),
                        ThreadLocalRandom.current().nextInt(36, 37 + 1),
                        ThreadLocalRandom.current().nextInt(35, 36 + 1),
                        ThreadLocalRandom.current().nextInt(120, 180 + 1),
                        ThreadLocalRandom.current().nextInt(40, 80 + 1),
                        ThreadLocalRandom.current().nextInt(10, 30 + 1), ThreadLocalRandom.current().nextInt(5, 10 + 1),
                        DedicatedServer.getDatabase().getAvailableRoom(), LocalDate.now());

                DedicatedServer.addHospilatization(hospitalization);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
