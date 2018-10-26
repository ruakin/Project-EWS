package de.ews.server.station;

public class Room {

    int    id;
    int    station;
    int    beds;
    String name;

    /**
     * @param id
     * @param station
     * @param beds
     * @param name
     */
    public Room(int id, int station, int beds, String name) {
        super();
        this.id = id;
        this.station = station;
        this.beds = beds;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getStation() {
        return station;
    }

    public int getBeds() {
        return beds;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "" + id + "," + station + "," + beds + "," + name + "";
    }

}
