package de.ews.server.employee;

public class Employee {

    private int     id;
    private String  name;
    private String  forename;
    private String  email;
    private boolean active;

    /**
     * @param id
     * @param name
     * @param forname
     * @param email
     * @param role
     */
    public Employee(int id, String name, String forename, String email) {
        super();
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.email = email;
    }

    public String getRole() {
        if (this instanceof Doctor) {
            return "doctor";
        } else if (this instanceof Nurse) {
            return "nurse";
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append(",");
        stringBuilder.append(this.getForname());
        stringBuilder.append(",");
        stringBuilder.append(this.getRole());
        stringBuilder.append(",");
        stringBuilder.append(this.getEmail());

        return stringBuilder.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForname() {
        return forename;
    }

    /**
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * 
     * @param Object
     *            obj The object to test
     * 
     * @return True if both employees are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Employee))
            return false;

        Employee employee = (Employee) obj;
        if (this.getEmail().equals(employee.getEmail())) {
            return true;
        }

        return false;
    }
}