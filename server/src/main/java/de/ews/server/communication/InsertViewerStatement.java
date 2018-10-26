package de.ews.server.communication;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ews.server.dedicated.DedicatedServer;
import de.ews.server.exception.DoubleEntryException;
import de.ews.server.exception.NoAvailableBedsException;
import de.ews.server.hospitalization.Hospitalization;
import de.ews.server.patient.Patient;

public class InsertViewerStatement extends AbstractStatement {

    private String              insuranceid;
    private String              surname;
    private String              name;
    private String              birthday;
    private float               maxtemp;
    private float               mintemp;
    private int                 maxsystolic;
    private int                 mindiastolic;
    private int                 maxbreathrate;
    private int                 minbreathrate;
    private static final Logger LOGGER = LogManager.getLogger();

    public InsertViewerStatement(String iid, String sn, String n, String b, float mat, float mit, int mas, int mid,
            int mab, int mib) {
        int age;
        this.insuranceid = iid;
        this.surname = sn;
        this.name = n;
        this.birthday = b;
        age = Period.between(LocalDate.parse(birthday), LocalDate.now()).getYears();
        this.maxtemp = mat != 0 ? mat
                : Util.selectValue(age, Util.MAX_TEMP_CHILD, Util.MAX_TEMP_ADULT, Util.MAX_TEMP_SENIOR);
        this.mintemp = mit != 0 ? mit
                : Util.selectValue(age, Util.MIN_TEMP_CHILD, Util.MIN_TEMP_ADULT, Util.MIN_TEMP_SENIOR);
        this.maxsystolic = mas != 0 ? mas
                : Util.selectValue(age, Util.MAX_SYSTOLIC_CHILD, Util.MAX_SYSTOLIC_ADULT, Util.MAX_SYSTOLIC_SENIOR);
        this.mindiastolic = mid != 0 ? mid
                : Util.selectValue(age, Util.MIN_DIASTOLIC_CHILD, Util.MIN_DIASTOLIC_ADULT, Util.MIN_DIASTOLIC_SENIOR);
        this.maxbreathrate = mab != 0 ? mab
                : Util.selectValue(age, Util.MAX_BREATHRATE_CHILD, Util.MAX_BREATHRATE_ADULT,
                        Util.MAX_BREATHRATE_SENIOR);
        this.minbreathrate = mib != 0 ? mib
                : Util.selectValue(age, Util.MIN_BREATHRATE_CHILD, Util.MIN_BREATHRATE_ADULT,
                        Util.MIN_BREATHRATE_SENIOR);
    }

    @Override
    public void perform() throws NullPointerException {
        LOGGER.info("perform: insert patient");
        // TODO insert limit values
        String response = "INSERT_SUCCEDED,";
        Patient p = new Patient(name, surname, birthday, insuranceid);

        try {
            Hospitalization hosp = new Hospitalization(p, DedicatedServer.getEmployeeList().getRandomDoctor(), maxtemp,
                    mintemp, maxsystolic, mindiastolic, maxbreathrate, minbreathrate, -1, null);
            p = DedicatedServer.addHospilatization(hosp);

            response += p.getId() + "," + p.getInsuranceID() + "," + p.getStation().getName().trim() + "," + p.getRoom()
                    + ";".trim();

            response = response.replace(" ", "");
        } catch (DoubleEntryException e1) {
            LOGGER.error("");
        } catch (NoAvailableBedsException noBeds) {
            response = "INSERT_FAILED;";
        }

        try {
            CommunicationFactory.getViewerConnector().getOutputStream().write(response.getBytes());
            LOGGER.info("Send: " + response);
        } catch (IOException e) {
            LOGGER.error("Could not execute, response should be: " + response, e);
        }
    }

    @Override
    public boolean hasAnswer() {
        return true;
    }

}
