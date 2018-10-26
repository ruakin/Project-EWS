package de.ews.server.hospitalization;

import java.util.LinkedList;

public class HospitalizationList {
    private LinkedList<Hospitalization> list = new LinkedList<>();

    public HospitalizationList() {

    }

    public HospitalizationList(LinkedList<Hospitalization> hospitalizationList) {
        this.setList(hospitalizationList);
    }

    /**
     * @return the list
     */
    public LinkedList<Hospitalization> getList() {
        return list;
    }

    private void setList(LinkedList<Hospitalization> hospitalizationList) {
        this.list = hospitalizationList;
    }

    public void addHospitalization(Hospitalization hospitalization) {
        this.list.add(hospitalization);
    }

    public void deleteHospitalization(Hospitalization hospitalization) {

        this.list.remove(hospitalization);
    }

}
