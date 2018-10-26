package de.ews.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.Util.PropertyManager;
import de.ews.server.alarm.Check;
import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.employee.Doctor;
import de.ews.server.employee.Employee;
import de.ews.server.employee.EmployeeList;
import de.ews.server.employee.Nurse;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.exception.PatientAlreadyExistsException;
import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.hospitalization.HospitalizationList;
import de.ews.server.patient.Measurement;
import de.ews.server.patient.Patient;
import de.ews.server.station.Room;
import de.ews.server.station.Station;
import de.ews.server.station.StationList;

//TODO finish implementation
public class Database {
    private static final Logger LOGGER = LogManager.getLogger(Database.class);

    private PropertyManager propertyManager = DedicatedServer.getPropertyManager();
    private Connection      connection      = null;
    private Statement       statement       = null;
    private String          filename;
    private File            file;

    /**
     * Creates a new database with a given filename. If the file does not exists it
     * will create the database with all needed tables
     * 
     * @param filename
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Database(String filename) throws SQLException, ClassNotFoundException {

        LOGGER.info("Trying to open sqlite file: " + filename);
        this.filename = filename;
        this.file = new File(filename);

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
        connection.setAutoCommit(false);

        if (file.length() == 0) {
            LOGGER.info("Database not found. Creating database ...");
            createTables();
        }
    }

    /**
     * Creates the tables in the database file
     * 
     * @return True if the tables are created successfully
     */
    synchronized private boolean createTables() {
        LOGGER.info("Creating database file: " + filename);

        try {
            statement = connection.createStatement();

            String flags[] = { "PRAGMA foreign_keys" };

            String[] dropTables = { "drop table  if exists measurement", "drop table if exists message",
                    "drop table if exists hospitalization", "drop table if exists patient", "drop table if exists room",
                    "drop table if exists station", "drop table if exists employee" };

            String[] createTables = {
                    "CREATE TABLE message (" + "messageid INTEGER primary key autoincrement,"
                            + "content varchar(256) not null" + ")",

                    "CREATE TABLE employee(" + "employeeid integer primary key autoincrement,"
                            + "name varchar(50) not null," + "surname varchar(50) not null,"
                            + "email varchar(150) unique not null," + "type varchar(50) not null,"
                            + "active varchar(8) not null" + ")",

                    "CREATE TABLE patient(" + "patientid integer primary key autoincrement," + "name varchar(50),"
                            + "surname varchar(50)," + "birthday varchar(25)," + "insuranceID varchar(20) unique" + ")",

                    "CREATE TABLE room(" + "roomid integer primary key autoincrement,"
                            + "stationid integer(10) not null,"
                            + "beds smallint(5) check (beds >= 0 and beds is not null)," + "name varchar(10),"
                            + "foreign key (stationid) references station(stationid)" + ")",

                    "CREATE TABLE station(" + "stationid integer primary key autoincrement,"
                            + "nurseid integer(10) not null," + "name varchar(50) not null unique,"
                            + "foreign key (nurseid) references employee(employeeid)" + ")",

                    "CREATE TABLE hospitalization(" + "hospid integer primary key autoincrement,"
                            + "patientid integer(10) not null," + "roomid integer(10) not null,"
                            + "doctorid integer(10) not null," + "start varchar(25) not null," + "end varchar(25),"
                            + "maxtemp real(10) not null," + "mintemp real(10) not null,"
                            + "maxsystolic real(10) not null," + "mindiastolic real(10) not null,"
                            + "maxbreathrate real(10) not null," + "minbreathrate real(10) not null,"
                            + "foreign key (patientid) references patient(patientid),"
                            + "foreign key (roomid) references room(roomid),"
                            + "foreign key (doctorid) references employee(employeeid)" + ")",

                    "CREATE TABLE measurement(" + "measureid integer primary key autoincrement,"
                            + "hospid integer(10) not null," + "messageid integer(10)," + "temp real(10) not null,"
                            + "breathrate real(10) not null," + "systolic real(10) not null,"
                            + "diastolic real(10) not null," + "time varchar(25) not null,"
                            + "foreign key (hospid) references hospitalization(hospid),"
                            + "foreign key (messageid) references message(messageid)" + ")" };

            String[] defaultValues = {
                    "insert into employee(name, surname, email, type, active)" + "values ('Default', 'Doctor', " + "'"
                            + propertyManager.getStringProperty("defaultDoctor", "doctor@example.com") + "'"
                            + ", 'doctor', 'true')",
                    "insert into employee(name, surname, email, type, active)" + "values ('Normal', 'Nurse'," + "'"
                            + propertyManager.getStringProperty("defaultNurse", "nurse@example.com") + "'"
                            + propertyManager.getStringProperty("defaultNurse", "nurse@example.com") + "'"
                            + ", 'nurse', 'true')" };

            String[] beds = insertBedsString();

            for (String string : flags) {
                statement.executeQuery(string);
            }

            for (String string : dropTables) {
                statement.execute(string);
            }

            for (String string : createTables) {
                statement.execute(string);
            }

            for (String string : defaultValues) {
                statement.execute(string);
            }

            for (String string : beds) {
                statement.execute(string);
            }

            connection.commit();
            return true;
        } catch (SQLException sql) {
            System.out.println(sql.getClass().getName() + "\n" + sql.getSQLState());
            sql.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Error occured during closing connection", e);
                e.printStackTrace();
            }
        }

        return false;
    }

    synchronized public void setMeasurementByPatient(Patient patient) {
        ResultSet resultSet = null;
        Statement statement = null;

        try {

            statement = connection.createStatement();

            String sql = "select * from measurement where hospid = '" + patient.getHospitalization().getId()
                    + "' order by measureid desc limit 2;";

            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {

                float temperature = resultSet.getFloat("temp");
                int systolic = resultSet.getInt("systolic");
                int diastolic = resultSet.getInt("diastolic");
                int breathingRate = resultSet.getInt("breathrate");
                String message = null;
                String time = resultSet.getString("time");

                Measurement measurement = new Measurement(temperature, systolic, diastolic, breathingRate, message,
                        time);

                patient.setCurrentMeasurement(measurement);
            }
        } catch (SQLException sql) {
            LOGGER.error("Could not read last measurement of patient: " + patient.getInsuranceID(), sql);
        }
    }

    /**
     * Reads all stations with id, station name and nurse off the database
     * 
     * @return the list of stations
     */
    synchronized public StationList getStationList() {
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        Statement statement2 = null;
        Statement statement3 = null;
        ResultSet resultSet3 = null;
        StationList list = new StationList();

        try {
            statement = connection.createStatement();
            statement2 = connection.createStatement();
            statement3 = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM station;");

            resultSet2 = statement2.executeQuery(
                    "SELECT email FROM employee WHERE employeeid = '" + resultSet.getInt("nurseid") + "' ;");

            resultSet3 = statement3.executeQuery("SELECT email FROM employee WHERE employeeid = '1' ;");

            Doctor doctor = (Doctor) getEmployee(resultSet3.getString("email"));

            while (resultSet.next()) {
                int id = resultSet.getInt("stationid");
                String name = resultSet.getString("name");

                Station station = new Station(id, name);

                Nurse nurse = (Nurse) getEmployee(resultSet2.getString("email"));

                station.setDoctor(doctor);
                station.setNurse(nurse);
                station.setStationList(list);

                list.addStation(station);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during reading of station list", e);
            return null;
        } finally {
            try {
                resultSet2.close();
                statement2.close();
                resultSet.close();
                // statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }

        return list;
    }

    /**
     * 
     * @param newPatient
     *            The patient to be inserted
     * @return Returns the insuranceid if insert was correct, otherwise -1
     * @throws DoubleEntryException
     */
    synchronized public String insertPatient(Patient newPatient) throws PatientAlreadyExistsException {
        try {
            statement = connection.createStatement();

            String forename = newPatient.getForename();
            String name = newPatient.getName();
            String birthday = newPatient.getBirthday().toString();
            String insuranceId = String.valueOf(newPatient.getInsuranceID());

            String sql = "INSERT INTO patient(name, surname, birthday, insuranceID) VALUES ('" + name + "', '"
                    + forename + "', '" + birthday + "', '" + insuranceId + "'" + ")";

            statement.execute(sql);
            connection.commit();

        } catch (SQLException sqlException) {
            LOGGER.error("Error occured during inserting of patint", sqlException);

            if (sqlException.getMessage().contains("patient.insuranceID")) {
                LOGGER.error("patient.insuranceID: " + newPatient);
            }

            if (sqlException.getMessage()
                    .contains("A UNIQUE constraint failed (UNIQUE constraint failed: patient.insuranceID)")) {
                throw new PatientAlreadyExistsException(newPatient.getInsuranceID());
            }
            return "-1";
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Error occured during closing connection", e);
                e.printStackTrace();
            }
        }

        return newPatient.getInsuranceID();
    }

    synchronized public Patient getPatient(String insuranceID) {
        Patient patient = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filename);
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String sql = "SELECT * FROM patient WHERE insuranceID = '" + insuranceID + "'";

            resultSet = statement.executeQuery(sql);

            int id = resultSet.getInt("patientid");
            String name = resultSet.getString("name");
            String forename = resultSet.getString("surname");
            String birthday = resultSet.getString("birthday");

            patient = new Patient(forename, name, birthday, insuranceID);
            patient.setId(id);

        } catch (SQLException e) {
            LOGGER.error("Could not read patient list", e);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return patient;
    }

    synchronized public Patient getPatientById(int id) {
        Patient patient = null;
        ResultSet resultSet = null;
        String sql = null;

        try {
            statement = connection.createStatement();

            sql = "SELECT * FROM patient WHERE patientid = '" + id + "'";

            resultSet = statement.executeQuery(sql);

            String name = resultSet.getString("name");
            String forename = resultSet.getString("surname");
            String birthday = resultSet.getString("birthday");
            String insuranceID = resultSet.getString("insuranceID");

            patient = new Patient(forename, name, birthday, insuranceID);
            patient.setId(id);

        } catch (SQLException e) {
            LOGGER.error("Could not read patient list");
            LOGGER.error("Statment: " + sql);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return patient;
    }

    /**
     * Inserts a new employee in the database
     * 
     * @param employee
     * @return Returns the id of the employee
     * @throws DoubleEntryException
     *             If the email address already exists
     */
    synchronized public int insertEmployee(Employee employee) throws DoubleEntryException {
        try {
            statement = connection.createStatement();

            String name = employee.getName();
            String forename = employee.getForname();
            String email = employee.getEmail();
            String role = employee.getRole();
            String active = String.valueOf(employee.isActive());

            String sql = "INSERT INTO employee(name, surname, email, type, active) VALUES ('" + name + "', '" + forename
                    + "', '" + email + "','" + role + "', '" + active + "')";

            statement.execute(sql);
            connection.commit();

        } catch (SQLException sqlException) {
            LOGGER.error("Error during insertion of patients: ", sqlException);

        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Error occured during closing connection", e);
                e.printStackTrace();
            }
        }
        return getEmployee(employee.getEmail()).getId();
    }

    /**
     * Reads employees stored in the database
     * 
     * @return List of employees or null at failures
     */
    synchronized public EmployeeList getEmployeeList() {
        EmployeeList employeeList = new EmployeeList();
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM employee;");

            while (resultSet.next()) {
                int id = resultSet.getInt("employeeid");
                String name = resultSet.getString("name");
                String forename = resultSet.getString("surname");
                String type = resultSet.getString("type");
                String email = resultSet.getString("email");
                boolean active = resultSet.getBoolean("active");

                Employee newEmployee = null;
                switch (type.toLowerCase()) {
                case "doctor":
                    newEmployee = new Doctor(id, name, forename, email);
                    break;
                case "nurse":
                    newEmployee = new Nurse(id, name, forename, email);
                default:
                    break;
                }
                newEmployee.setActive(active);

                employeeList.addEmployee(newEmployee);
            }

        } catch (SQLException e) {
            LOGGER.error("Could not read employee list", e);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return employeeList;
    }

    /**
     * Reads a specified employee by email
     * 
     * @param Mail
     *            of the employee
     * @return The
     */
    synchronized public Employee getEmployee(String mail) {
        Employee newEmployee = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            String sql = "SELECT * FROM employee WHERE email = '" + mail + "'";

            resultSet = statement.executeQuery(sql);

            int id = resultSet.getInt("employeeid");
            String name = resultSet.getString("name");
            String forename = resultSet.getString("surname");
            String type = resultSet.getString("type");
            String email = resultSet.getString("email");
            boolean active = Boolean.valueOf(resultSet.getString("active"));

            switch (type.toLowerCase()) {
            case "doctor":
                newEmployee = new Doctor(id, name, forename, email);
                break;
            case "nurse":
                newEmployee = new Nurse(id, name, forename, email);
            default:
                break;
            }

            newEmployee.setActive(active);

        } catch (SQLException e) {
            LOGGER.error("Could not read employee list", e);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return newEmployee;
    }

    synchronized public void setEmployeActiveState(Employee employee) {
        try {
            statement = connection.createStatement();

            String sql = "UPDATE employee SET active = '" + employee.isActive() + "' WHERE email = '"
                    + employee.getEmail() + "'";
            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
    }

    synchronized public HospitalizationList getCurrentHospitalization() {
        HospitalizationList list = new HospitalizationList();

        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM patient, hospitalization WHERE patient.patientid = hospitalization.patientid AND end is null;";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("hospid");
                int patientId = resultSet.getInt("patientid");
                int roomId = resultSet.getInt("roomid");
                int doctorId = resultSet.getInt("doctorid");
                LocalDate start = LocalDate.parse(resultSet.getString("start"));
                float maxTemp = resultSet.getFloat("maxtemp");
                float minTemp = resultSet.getFloat("mintemp");
                int maxSystolic = resultSet.getInt("maxSystolic");
                int minDiastolic = resultSet.getInt("mindiastolic");
                int maxBreathRate = resultSet.getInt("maxbreathrate");
                int minBreathRate = resultSet.getInt("minbreathrate");

                Hospitalization hospitalization = new Hospitalization(getPatientById(patientId),
                        (Doctor) getEmployeeById(doctorId), maxTemp, minTemp, maxSystolic, minDiastolic, maxBreathRate,
                        minBreathRate, roomId, start);
                hospitalization.setId(id);
                hospitalization.getPatient().setId(patientId);
                hospitalization.getPatient().setHospitalization(hospitalization);
                hospitalization.getPatient().setRoom(roomId);

                list.addHospitalization(hospitalization);
            }

            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return list;
    }

    // FIXME
    synchronized public void addHospitalization(Hospitalization hospitalization) {
        LocalDate date = LocalDate.now();
        hospitalization.setStart(date);

        try {
            insertPatient(hospitalization.getPatient());
            Patient patient = getPatient(hospitalization.getPatient().getInsuranceID());
            hospitalization.getPatient().setId(patient.getId());

        } catch (DoubleEntryException doubleEntryException) {
            LOGGER.warn("Patient already exists " + hospitalization.getPatient().getInsuranceID());
        }

        if (isPatientEndTimeNotNull(hospitalization.getPatient())) {
            try {
                statement = connection.createStatement();

                int roomId = hospitalization.getRoom();
                int doctorId = DedicatedServer.getEmployeeList().getRandomDoctor().getId();
                float minTemp = hospitalization.getMinTemp();
                float maxTemp = hospitalization.getMaxTemp();
                int maxSystolic = hospitalization.getMaxSystolic();
                int minDiastolic = hospitalization.getMinDiastolic();
                int maxBreathrate = hospitalization.getMaxBreathrate();
                int minBreathrate = hospitalization.getMinBreathrate();
                String insuranceId = hospitalization.getPatient().getInsuranceID();

                String sql = "INSERT INTO hospitalization (patientid, roomid, doctorid, start, end, maxtemp, mintemp, maxsystolic, mindiastolic, maxbreathrate, minbreathrate) "
                        + "select patientid, '" + roomId + "', '" + doctorId + "', '" + date + "', NULL , '" + maxTemp
                        + "', '" + minTemp + "', '" + maxSystolic + "', '" + minDiastolic + "', '" + maxBreathrate
                        + "', '" + minBreathrate + "'" + "FROM patient WHERE insuranceID = '" + insuranceId + "';";

                statement.execute(sql);
                connection.commit();

            } catch (SQLException sql) {
                LOGGER.error("Could not insert new hospitalization: ", sql);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error("Could not close statement", e);
                }
            }

        } else {
            LOGGER.warn("Patient already in hospitalization: " + hospitalization.getPatient().getInsuranceID());
        }

    }

    /**
     * Checks if the patient has not an end time in the current hospitalization
     * 
     * @param The
     *            patient to be checked
     * @return True if the patient's end time is null
     */
    synchronized private boolean isPatientEndTimeNotNull(Patient patient) {
        ResultSet resultSet = null;
        Statement statement = null;
        String room = null;

        try {
            statement = connection.createStatement();

            String sql = "SELECT * FROM hospitalization, patient WHERE patient.patientId = hospitalization.patientId AND end IS NULL AND insuranceId = '"
                    + patient.getInsuranceID() + "';";
            resultSet = statement.executeQuery(sql);

            // ResultSet is empty
            if (!resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            LOGGER.error("Could not get state if patient already in hospitalization", e);
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            } catch (NullPointerException nullPointerException) {
                LOGGER.info("Patient is already stationed");
            }
        }

        return true;
    }

    synchronized public void addMeasurement(Patient patient, Measurement measurement) {

        measurement.setPatient(patient);
        patient.setCurrentMeasurement(measurement);

        String[] sql = new String[3];
        int alarmLevel = Check.getAlarmLevel(patient);

        switch (alarmLevel) {
        case 4:
        case 3:
        case 2:
            measurement.setMessage("[Kritisch]");
            break;

        case 1:
            measurement.setMessage("[Warnung]");
            break;
        default:
            break;
        }

        Date date = measurement.getTime();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String time = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);

        if (measurement.getMessage() == null) {
            sql[0] = "INSERT INTO measurement(hospID, messageID, temp, breathrate, systolic, diastolic, time) "
                    + "VALUES ('" + patient.getHospitalization().getId() + "', NULL, '" + measurement.getTemperature()
                    + "', '" + measurement.getBreathingRate() + "', '" + measurement.getSystolic() + "', '"
                    + measurement.getDiastolic() + "', '" + time + "');";

            Statement statement = null;
            try {
                statement = connection.createStatement();
                statement.execute(sql[0]);
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("Could not insert measurement: " + sql[0], e);
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    LOGGER.error("Could not close statement", e);
                }
            }

        } else {
            String docMail = measurement.getPatient().getHospitalization().getDoctor().getEmail();

            sql[0] = "INSERT INTO message(content) VALUES ('" + measurement.toString() + " an: " + docMail + "');";

            sql[1] = "insert into measurement(hospID, messageID, temp, breathrate, systolic, diastolic, time) "
                    + "SELECT '" + patient.getHospitalization().getId() + "', max(messageID), '"
                    + measurement.getTemperature() + "', '" + measurement.getBreathingRate() + "', '"
                    + measurement.getSystolic() + "', '" + measurement.getDiastolic() + "', '" + time
                    + "' from message;";

            Statement statement1 = null;
            Statement statement2 = null;
            try {
                statement1 = connection.createStatement();
                statement2 = connection.createStatement();

                statement1.execute(sql[0]);
                statement1.execute(sql[1]);

                connection.commit();
                LOGGER.info("Successfully added measurement of patient " + measurement.getPatient().getInsuranceID());

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    statement1.close();
                    statement2.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public void dismissPatient(Patient patient, LocalDate date) {
        LOGGER.info("Dismissing insuranceId:" + patient.getInsuranceID());
        try {
            statement = connection.createStatement();

            String sql = "UPDATE hospitalization SET end = '" + date.toString() + "' WHERE patientid = '"
                    + patient.getId() + "'";

            LOGGER.info(sql);

            LOGGER.info("Dismissed patient id: " + patient.getId());

            statement.execute(sql);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Could not dismiss patient", e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
    }

    synchronized public Employee getEmployeeById(int id) {
        Employee newEmployee = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            String sql = "SELECT * FROM employee WHERE employeeid = '" + id + "'";

            resultSet = statement.executeQuery(sql);

            String name = resultSet.getString("name");
            String forename = resultSet.getString("surname");
            String type = resultSet.getString("type");
            String email = resultSet.getString("email");
            boolean active = Boolean.valueOf(resultSet.getString("active"));

            switch (type.toLowerCase()) {
            case "doctor":
                newEmployee = new Doctor(id, name, forename, email);
                break;
            case "nurse":
                newEmployee = new Nurse(id, name, forename, email);
            default:
                break;
            }

            newEmployee.setActive(active);

        } catch (SQLException e) {
            LOGGER.error("Could not read employee list", e);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }
        return newEmployee;
    }

    synchronized public LinkedList<Room> getRooms() {
        LinkedList<Room> rooms = new LinkedList<>();
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            String sql = "SELECT * FROM room";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("roomid");
                int station = resultSet.getInt("stationid");
                int beds = resultSet.getInt("beds");
                String name = resultSet.getString("name");

                Room room = new Room(id, station, beds, name);
                rooms.add(room);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }

        return rooms;
    }

    synchronized public int getAvailableRoom() {
        ResultSet resultSet = null;
        Statement statement = null;
        int room = -1;

        try {
            statement = connection.createStatement();

            String sql = "select roomid from room except select room.roomid from room, hospitalization where room.roomid = hospitalization.roomid and end is null;";
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                room = resultSet.getInt(1);
            }
        } catch (SQLException e) {

        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }

        return room;
    }

    synchronized public Station getStationByRoom(int roomid) {
        ResultSet resultSet = null;
        Statement statement = null;
        Station station = null;
        String name;
        int id;

        try {
            statement = connection.createStatement();

            String sql = "select * from station, room where station.stationid = room.stationid and room.roomid = "
                    + roomid + ";";
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                name = resultSet.getString("name");
                id = resultSet.getInt("stationid");

                station = new Station(id, name);
            }
        } catch (SQLException e) {

        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("An error occured during closing the conection", e);
            }
        }

        return station;
    }

    /**
     * Returns string array containing all insert statements for inserting beds into
     * all rooms of all stations
     * 
     * @return
     */
    synchronized private String[] insertBedsString() {
        String insert[] = {
                "insert into station(nurseid, name) " + "select employeeid, 'Abteilung A' " + "from employee "
                        + "where email = 'nurse@example.com' and type = 'nurse'",
                "insert into station(nurseid, name) " + "select employeeid, 'Abteilung B' " + "from employee "
                        + "where email = 'nurse@example.com' and type = 'nurse'",
                "insert into station(nurseid, name) " + "select employeeid, 'Abteilung C' " + "from employee "
                        + "where email = 'nurse@example.com' and type = 'nurse'",
                "insert into station(nurseid, name) " + "select employeeid, 'Abteilung D' " + "from employee "
                        + "where email = 'nurse@example.com' and type = 'nurse'",
                "insert into station(nurseid, name) " + "select employeeid, 'Abteilung E' " + "from employee "
                        + "where email = 'nurse@example.com' and type = 'nurse'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'A101' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A102' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'A103' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'A104' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A105' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'A106' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'A107' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A108' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'A109' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'A110' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A111' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'A112' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'A113' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'A114' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'A115' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'A116' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A117' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'A118' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'A119' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'A120' " + "from station "
                        + "where name = 'Abteilung A'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B101' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'B102' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B103' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B104' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'B105' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'B106' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B107' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B108' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B109' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B110' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'B111' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B112' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'B113' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B114' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'B115' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'B116' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B117' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'B118' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'B119' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'B120' " + "from station "
                        + "where name = 'Abteilung B'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C101' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'C102' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'C103' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C104' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'C105' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'C106' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C107' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'C108' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'C109' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'C110' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C111' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'C112' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'C113' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'C114' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'C115' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C116' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'C117' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'C118' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'C119' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'C120' " + "from station "
                        + "where name = 'Abteilung C'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'D101' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'D102' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'D103' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'D104' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'D105' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'D106' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'D107' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'D108' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'D109' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'D110' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'D111' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'D112' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'D113' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'D114' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'D115' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'D116' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'D117' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'D118' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'D119' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'D120' " + "from station "
                        + "where name = 'Abteilung D'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'E101' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'E102' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'E103' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'E104' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'E105' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'E106' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'E107' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'E108' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'E109' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'E110' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'E111' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'E112' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'E113' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'E114' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'E115' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 1, 'E116' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 4, 'E117' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 2, 'E118' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 3, 'E119' " + "from station "
                        + "where name = 'Abteilung E'",
                "insert into room(stationid, beds, name) " + "select stationid, 5, 'E120' " + "from station "
                        + "where name = 'Abteilung E'" };

        return insert;
    }

}