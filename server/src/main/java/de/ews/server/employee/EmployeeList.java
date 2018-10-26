package de.ews.server.employee;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.Database;
import de.ews.server.dedicated.DedicatedServer;

public class EmployeeList {
    private static final Logger  LOGGER       = LogManager.getLogger();
    private LinkedList<Employee> employeeList = new LinkedList<Employee>();
    private Database             database     = DedicatedServer.getDatabase();

    public EmployeeList() {

    }

    /**
     * Adds a new Employee to the list
     * 
     * @param newEmployee
     * @return True if
     */
    public int addEmployee(Employee newEmployee) {
        int id;
        id = database.getEmployee(newEmployee.getEmail()).getId();
        employeeList.add(newEmployee);

        return id;

    }

    /**
     * Removes the Emplyee
     * 
     * @param employee
     */
    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    /**
     * 
     */
    public Employee getEmployee(int id) {
        for (Employee searchee : employeeList) {
            if (searchee.getId() == id) {
                return searchee;
            }
        }
        return null;
    }

    public Employee contains(String mail) {
        for (Employee employee : employeeList) {
            if (employee.getEmail().equals(mail)) {
                return employee;
            }
        }

        return null;
    }

    /**
     * 
     * 
     * @return
     */
    public Doctor getRandomDoctor() {
        for (Employee employee : employeeList) {
            if (employee.getRole().equals("doctor")) {
                return (Doctor) employee;
            }
        }

        return null;
    }

}
