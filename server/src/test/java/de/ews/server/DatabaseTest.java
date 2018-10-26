package de.ews.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.employee.Doctor;
import de.ews.server.employee.Employee;
import de.ews.server.employee.EmployeeList;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.patient.Patient;

public class DatabaseTest {

    private static Database        testee;
    private static DedicatedServer dedicatedServer;

    @BeforeClass
    public static void setUp() {

        try {
            DatabaseTest.dedicatedServer = new DedicatedServer();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        testee = DedicatedServer.getDatabase();
    }

    @AfterClass
    public static void deleteDatabaseFile() {
        File file = new File("server.sqlite3");
        file.delete();
    }

    @Test
    public void testInsertPatientIsOK() {
        String insuranceID = "1234567890";
        new Patient("Vorname", "Nachname", "2001-01-01", insuranceID);
    }

    @Test(expected = DoubleEntryException.class)
    public void testInsertPatientDuplicateFAIL() throws DoubleEntryException {
        String insuranceID = "1234567890";
        new Patient("Vorname", "Nachname", "2001-01-01", insuranceID);
    }

    @Test(expected = DoubleEntryException.class)
    public void testGetEmployeeListOK() throws DoubleEntryException {
        String mail = "test2@test.de";
        Doctor doctor = new Doctor(12, "Name", "Vorname", mail);

        testee.insertEmployee(doctor);
        EmployeeList list = testee.getEmployeeList();

        assertEquals(doctor, list.contains(mail));
    }

    @Test
    public void testGetEmployeeListFAIL() {
        String mail = "Not@There.Fail";
        new Doctor(12, "Name", "Vorname", mail);

        EmployeeList list = testee.getEmployeeList();

        assertEquals(null, list.contains(mail));
    }

    @Test
    public void testSetEmployeActiveStateTrue() {
        String mail = DedicatedServer.getPropertyManager().getStringProperty("defaultDoctor", null);
        Employee employee = new Employee(1, "Default", "Doctor", mail);
        employee.setActive(true);

        testee.setEmployeActiveState(employee);

        Employee employee2 = testee.getEmployee(mail);

        assertTrue(employee2.isActive());
    }

    @Test
    public void testSetEmployeActiveStateFalse() throws Exception {
        String mail = DedicatedServer.getPropertyManager().getStringProperty("defaultDoctor", null);
        Employee employee = new Employee(1, "Default", "Doctor", mail);
        employee.setActive(false);

        testee.setEmployeActiveState(employee);

        Employee employee2 = testee.getEmployee(mail);

        assertFalse(employee2.isActive());
    }

    @Test
    public void testGetEmployeeOK() {
        String mail = "doctor@example.com";
        Employee reference = new Employee(1, "Doctor", "Default", mail);
        Employee employee = testee.getEmployee(mail);

        assertEquals(reference, employee);

    }

    @Test
    public void testGetStationList() {
        // System.out.println(testee.getStationList().toString());
    }
}
