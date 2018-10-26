using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Desktopanwendung
{
    public class SendToServer
    {
        private static TcpClient client = new TcpClient();
        //test
        private static bool _connected = false;
        private static Object obj = new Object();

        public static void InitClient()
        {
            try
            {
                //connec over IP and port to a Server
                client.Connect("192.168.4.1", 8000);

                _connected = true;
            }
            catch (SocketException ex)
            {
                _connected = false;
                if (!WindowForm.GetForm().getEnd())
                {
                    Thread.Sleep(5000);
                    InitClient();
                }

            }
            catch (ObjectDisposedException ex)
            {
                
            }
        }

        /*
         * TCP Nachrichtenformat Desktop: INSERT,<insuranceid>,
         * <surname>,<name>,<birthday>,<maxtemp>,<mintemp>,
         * <maxsystolic>,<mindiastolic>,<maxbreathrate>,<minbreathrate>;
        */
        //prepare the statement for the server message for adding patients
        public static String AddPatient(String insuranceid, String surname, String name, String date,
            String diagnostics)
        {
            String message = "INSERT," + insuranceid + "," + surname + "," + name + "," + date + "," + diagnostics +
                             ";";
            
            return Send(message);
            //return "INSERT_SUCCEDED,1,X123456789,AbteilungA,A.123;";
        }

        //prepare the statement for the server message for deactivating patients
        public static String DeactivatePatient(String patientId)
        {
            String message = "DELETE," + patientId + ";";
            return Send(message);
            //return "DELETE_FAILED";
        }

        //sends the passed message to the _connected server
        public static String Send(String message)
        {
            Monitor.Enter(obj);
            try
            {
                if (_connected)
                {
                    try
                    {
                        //client stream for writing and receiving
                        NetworkStream stream = client.GetStream();

                        //translate the passed message into ASCII and store it as a byte array
                        Byte[] data = Encoding.ASCII.GetBytes(message);
              
                        //write and send the message to the _connected server
                        stream.Write(data, 0, data.Length);

                        //Server Response
                        //Byte array for storing the responsed message
                        Byte[] responseDataBytes = new byte[10000];
                        //String for the responsed message
                        String response = "";

                        
                        //Reading the response
                        int i;
                        //while not needed reads the whole buffer at once
                        //while ((i = stream.Read(responseDataBytes, 0, responseDataBytes.Length)) != 0)
                        //{
                        Trace.WriteLine("Lese Response");
                        i = stream.Read(responseDataBytes, 0, responseDataBytes.Length);
                            response = response + Encoding.ASCII.GetString(responseDataBytes, 0, i);
                       
                        //}

                        Trace.Write("Antwort: "+ response);

                        //think about when to close the stream
                        //stream.Close();

                        return response;

                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("Exception: {0}", e);
                        //close the stream
                        
                        //close the client
                        client.Close();
                        InitClient();
                    }
                }
            }
            finally
            {
                Monitor.Exit(obj);
            }


            return "FAILED";
        }
    }
}

    
