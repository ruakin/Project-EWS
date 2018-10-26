using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Remoting.Messaging;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Desktopanwendung
{
    class Request
    {
        private static Object lockObj = new Object();
        
        public static void Start()
        {
            Thread.Sleep(5000);
            SendToServer.InitClient();

            String fetchall = "FETCHALL;";
            String fetch = "FETCH;";
            Thread.Sleep(100);
            //throw new NotImplementedException();

            //FETCHALL

            Trace.WriteLine("Fetchall send: ");
            String ret = SendToServer.Send(fetchall);
           
            String[] split = ret.Split(';');

            String[] message = new String[10]; //mit Alter 10!
            foreach (String s in split)
            {
                if (s.Trim() != "")
                {
                    String[] splitStrings = s.Split(',');
                    int index = 0;
                    foreach (String st in splitStrings)
                    {
                        message[index] = st;
                        index++;
                        if (index == 9) 
                        {
                            index = 1;
                            int id = Int32.Parse(message[1]);
                            String insurance = message[2];
                            String name = message[3];
                            String surname = message[4];
                            String stationname = message[5];
                            String roomname = message[6];
                            int state = Int32.Parse(message[7]);
                            String date = message[8];
                            int age = -1;

                            if (date != null)
                            {
                                String[] splitDate = date.Split('-');
                                String[] dateString = new string[3];
                                int dateIndex = 0;
                                foreach (String stDate in splitDate)
                                {
                                    dateString[dateIndex] = stDate;
                                    dateIndex++;
                                }

                                int year = Int32.Parse(dateString[0]);
                                int month = Int32.Parse(dateString[1]);
                                int day = Int32.Parse(dateString[2]);

                                DateTime now = DateTime.Now;

                                if (now.Year - year != now.Year)
                                {
                                    age = now.Year - year;
                                    if (now.Month < month)
                                    {
                                        age--;
                                    }
                                    else if (now.Month == month)
                                    {
                                        if (now.Day < day)
                                        {
                                            age--;
                                        }
                                    }
                                }
                            }

                            Patient newPatient = new Patient(id, insurance, stationname, age, name, surname, state, roomname, true);
                            AddToList(newPatient);
                            
                        }
                    }
                    WindowForm.GetForm().PrintGui(false);
                }
            }

            Thread.Sleep(4000);

            
            while (true)
            {

                //FETCH
                ret = SendToServer.Send(fetch);
                Trace.WriteLine("Fetch");

                split = ret.Split(';');
                foreach (String s in split)
                {
                    if (s.Trim() != "")
                    {
                        String[] splitStrings = s.Split(',');
                        int index = 0;
                        foreach (String st in splitStrings)
                        {
                            message[index] = st;
                            index++;
                            if (index == 3)
                            {
                                index = 1;
                                int id = Int32.Parse(message[1]);
                                int state = Int32.Parse(message[2]);

                                foreach (Patient p in Patient.GetPatients())
                                {
                                    if (p.Pid == id)
                                    {
                                        String insurance = p.InsuranceId;
                                        String name = p.Name;
                                        String surname = p.Surname;
                                        String stationname = p.Station;
                                        String roomname = p.Room;

                                        Patient newPatient = new Patient(id, insurance, stationname, 10, name, surname, state, roomname, true);
                                        AddToList(newPatient);
                                        WindowForm.GetForm().PrintGui(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                Thread.Sleep(2500);
            }

            
        }

        /**
         * @fn public static void AddToList(Patient)
         * @brief adding the external patient paramter to the list.
         *        if it's already in the list, remove it and add it again
         * @param Patient
         */
        public static bool AddToList(Patient p)
        {
            Monitor.Enter(lockObj);
            try
            {
                LinkedListNode<Patient> node = Patient.GetPatients().First;

                while (node != null)
                {
                    //if patient is already in list
                    //if the ids of the patient object and the
                    //current node are the same
                    if (node.Value.Pid == p.Pid)
                    {
                        //removing node
                        Patient.GetPatients().Remove(node);
                        if (p.State < 1)
                        {
                            return false;
                        }
                        break;
                    }
                    node = node.Next;
                }

                node = Patient.GetPatients().First;

                //if there's no node in the list
                if (node == null)
                {
                    Patient.GetPatients().AddFirst(p);
                    return true;
                }

                while (node != null)
                {

                    //if the state of the patient object is greater or equal than
                    //the current state add it to the list
                    if (p.State >= node.Value.State)
                    {
                        //add the patient object to the patients list
                        Patient.GetPatients().AddBefore(node, p);
                        return true;
                    }

                    //if there's no more node after the current node
                    if (node.Next == null)
                    {
                        //add the patient object to the patients list
                        Patient.GetPatients().AddAfter(node, p);
                        return true;
                    }
                    node = node.Next;
                }
                return false;
            }
            finally
            {
                Monitor.Exit(lockObj);
            }
        }

        
    }
}
