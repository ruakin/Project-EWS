package de.ews.server.station;

import java.util.LinkedList;
import java.util.List;

public class StationList {

    private List<Station> stations = new LinkedList<>();

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    /**
     * 
     * @param newStation
     */
    public void addStation(Station newStation) {
        stations.add(newStation);
    }

    /**
     * 
     * @param oldStation
     */
    public void removeStation(Station oldStation) {
        // TODO - implement StationList.removeStation
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a station y its id
     * 
     * @param id
     *            of the station to return
     * @return Station or null if there is no station with the specified id
     */
    public Station getStation(int id) {

        for (Station station : stations) {
            if (station.getId() == id) {
                return station;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Stations: " + stations.size() + ",\n");
        for (Station station : stations) {
            int id = station.getId();
            String name = station.getName();
            int patients = station.getPatients().size();

            stringBuilder.append("ID: " + id + ", Name: " + name + ", Patienten: " + patients + ",\n");
        }

        return stringBuilder.toString();
    }

}