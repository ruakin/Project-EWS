package com.example.rukiye.myapplication;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends Activity {

    private static String station;
    Button Absenden;
    Spinner StationSpinner, PatientSpinner;
    EditText Temparatur,  Atemfrequenz, Systolic, Diastolic;
    ArrayList<Patient> PatientList;
    String  patient;
    String name, surname;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = sdf.format(new Date());

    JSONArray jsonArray2;
    Messure messure;


    private static Socket s;
    private static InputStreamReader isr;
    private static BufferedReader br;
    private static PrintWriter printWriter;
    String message;


    /* Connection details */

    private static final String IP = "192.168.4.1";
    private static final int PORT = 8001;
    private static Connector connector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Temparatur = (EditText) findViewById(R.id.TemparaturEdit);
        Atemfrequenz = (EditText) findViewById(R.id.AtemfrequenzEdit);
        Systolic = (EditText) findViewById(R.id.SystolicEdit);
        Diastolic = (EditText) findViewById(R.id.DiastolicEdit);
        StationSpinner = (Spinner) findViewById(R.id.StationDropdown);
        PatientSpinner = (Spinner) findViewById(R.id.PatientDropdown);



        PatientList = new ArrayList<>();

        Runnable run = new Runnable() {
            synchronized public void run() {
                try {
                    connector = new Connector(IP, PORT);

                } catch (IOException e) {
                    System.out.println(" ~~~ Connector ~~~ ");
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(run);
        t.start();

        // wait until the connector is not null;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        StationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                station = StationSpinner.getItemAtPosition(StationSpinner.getSelectedItemPosition()).toString();
                if(!station.equals("Select station..")){

                    //send_station();

                    System.out.println("loadPatient: " );
                    PatientList = connector.loadPatient();

                    System.out.println("nach loadpatient");
                    ArrayList<String> patientsAsString = new ArrayList<>();
                    for (Patient patient: PatientList) {
                        patientsAsString.add("" + patient.getName()+ ", "+ patient.getSurname() + "");

                    }
                    PatientSpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, patientsAsString));
                    System.out.println("setAdapter");
               }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });

        PatientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                patient = PatientSpinner.getItemAtPosition(PatientSpinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),patient,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });




       Absenden = (Button) findViewById(R.id.Absenden);
        Absenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_text(v);


            }
        });

    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null )
            result += line;
        inputStream.close();
        return result;
    }

    private boolean validate(){
        if(StationSpinner.getSelectedItem().toString().trim().equals(""))
            return false;
        else if(PatientSpinner.getSelectedItem().toString().trim().equals(""))
            return false;
        else if(Atemfrequenz.getText().toString().trim().equals(""))
            return false;
        else if(Temparatur.getText().toString().trim().equals(""))
            return false;
        else if(Systolic.getText().toString().trim().equals(""))
            return false;
        else if(Diastolic.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }


    /**
     * Sends patient measurement to server
     *
     */
    public void send_station(){

        String type;

        type = "station-select";


        JSONObject json2 = new JSONObject();
        try {
            json2.put("name",station);
            json2.put("type", type);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        connector.send(json2);
        Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();


    }


    public void send_text(View view){

        String type;
        int systolic, diastolic, breathrate;
        float temp;
       String name = PatientSpinner.getSelectedItem().toString();
        temp = Float.parseFloat(Temparatur.getText().toString());
        systolic = Integer.parseInt(Systolic.getText().toString());
        diastolic = Integer.parseInt(Diastolic.getText().toString());
        breathrate = Integer.parseInt(Atemfrequenz.getText().toString());
        int hospId = 0;
        type = "insert-measure";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        for (Patient patient: PatientList) {
            if((patient.getName() + ", " +patient.getSurname()).equals(name)) {
                hospId = patient.getHospid();
            }
        }
        //String name = "muster";
        //String hospid = "123";

        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
            json.put("hospid",hospId);
            json.put("systolic", systolic);
            json.put("diastolic", diastolic);
            json.put("temp", temp);
            json.put("breathrate", breathrate);
            json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("" + json.toString());
        connector.send(json);
        Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();


    }

    public static String getStation(){
        return station;

    }

}