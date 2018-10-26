package com.example.rukiye.myapplication;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.util.Scanner;

import static com.example.rukiye.myapplication.MainActivity.getStation;

public class Connector {
    private Socket socket;
    private OutputStreamWriter out;
    private BufferedReader in;



    /**
     * Creates a new Connection to a tcp server through a socket
     *
     * @param ip The IP of the Server
     * @param port The Port to connect to
     * @throws IOException IOException will be thrown if there was an error during creating of the Connector
     */
    public Connector (final String ip, final int port) throws IOException {
        this.socket = new Socket(ip, port);

        out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

    }

    /**
     * loads patients
     *
     * @return An ArrayList of patients
     */
    public ArrayList<Patient> loadPatient() {
        

        ArrayList<Patient> patients = new ArrayList<>();

        try {

            System.out.println("requestPatients: ");
           //patients.addAll(requestPatients("station-select"));
            //System.out.println("patients.getname: " + patients.get(0).getName());
            patients = requestPatients("station-select");
        } catch (JSONException json) {
            json.printStackTrace();
            patients = null;
        }



        return patients;
    }

    /**
     * Sends a json object to the server
     *
     * @param json The json object to send to
     */
    public void send(final JSONObject json){

        Runnable run = new Runnable() {
            public void run() {
                try {
                    out.write(json.toString() + '\n');
                    out.flush();
                } catch (IOException io){
                    io.printStackTrace();
                }
            }
        };

        new Thread(run).start();


    }

    /**
     * Sends a request and returns the answer
     *
     * @param requestCode
     * @return Returns the response as an ArrayList
     * @throws JSONException
     * @throws UnsupportedOperationException If a wrong requestCode is given
     */
    public ArrayList<Patient> requestPatients(final String requestCode) throws JSONException, UnsupportedOperationException {

        final ArrayList<Patient> response = new ArrayList<Patient>();

        Runnable run = new Runnable() {
            public void run() {
                /* Checks if the right requestCode is given */
                if (!"station-select".equals(requestCode)) {
                    throw new UnsupportedOperationException("Not supported requestCode: " + requestCode);
                }

                /* Prepare and send request */
                JSONObject json = new JSONObject();
                String station = getStation();
                try {
                    //json.put("name", "Abteilung A");
                    json.put("name", getStation() );
                    json.put("type", requestCode);
                } catch (JSONException jsone){
                    System.out.println("Kann nicht senden");
                    jsone.printStackTrace();
                }
                send(json);
                System.out.println("Senden erfolgreich.");

                /* Receive response */
                try {
                    System.out.println("readline");
                    //String line = in.readLine();
                    Scanner sc = new Scanner(in);
                    sc.useDelimiter("]");
                    String line = "";
                    do {
                        line += sc.next() + "]";
                    } while(countChar(line, ']') != countChar(line, '['));
                    System.out.println("read: " + line);
                    JSONArray jsonArray = new JSONArray(line);
                    JSONObject jsonObject;
                    System.out.println("################################" + line);

                    for(int i= 0; i <jsonArray.length(); i++){
                        jsonObject = (JSONObject) jsonArray.get(i);
                        int hospid = jsonObject.getInt("hospid");
                        String name = jsonObject.getString("name");
                        String surname = jsonObject.getString("surname");
                        String roomname = jsonObject.getString("roomname");
                        String stationname = jsonObject.getString("stationname");
                        response.add(new Patient(hospid, name, surname, roomname, stationname));
                    }






                } catch (JSONException jsone) {
                    jsone.printStackTrace();
                }

            }
        };

       Thread t =  new Thread(run);
       t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private int countChar(String s, char c)
    {
        int counter = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == c)
                counter++;
        }
        return counter;
    }

}
