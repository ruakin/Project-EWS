package de.ews.server.employee;

public class Nurse extends Employee {

    public Nurse(int id, String name, String forname, String email) {
        super(id, name, forname, email);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getRole() {
        return "nurse";
    }
}