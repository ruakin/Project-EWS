package de.ews.server.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.alarm.Check;
import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.patient.Measurement;
import de.ews.server.patient.Patient;

public class InsertMeasureStatement extends AbstractStatement {

    private static final Logger LOGGER = LogManager.getLogger();
    private int                 hospid;
    private int                 systolic;
    private int                 diastolic;
    private float               temp;
    private int                 breath;
    private String              time;

    public InsertMeasureStatement(int h, int s, int d, float t, int b, String ti) {
        hospid = h;
        this.breath = b;
        this.diastolic = d;
        this.systolic = s;
        this.temp = t;
        time = ti;
    }

    @Override
    public void perform() throws NullPointerException {
        LOGGER.info("perform: insert measure");
        Patient p = DedicatedServer.getPatientByHospIdid(hospid);
        int alarmNew, alarmOld;
        alarmOld = p.getLastMeasurement() == null ? -1 : Check.getAlarmLevel(p);

        Measurement measurement = new Measurement(temp, systolic, diastolic, breath, null, time);
        DedicatedServer.addMeasurement(p, measurement);// TODO
                                                       // //insert
        alarmNew = Check.getAlarmLevel(p); // get new alarm level
        if (alarmNew != alarmOld) {
            Util.fetchList.add(new FetchItem(p.getId(), alarmNew + 1));
        }
    }

    public boolean hasAnswer() {
        return false;
    }

}
