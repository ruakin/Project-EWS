using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Desktopanwendung
{

    public class Patient
    {
        private static LinkedList<Patient> patients = new LinkedList<Patient>();
        

        public int Pid { get; } //Patient ID
        public String InsuranceId { get; }
        public int Sid { get; } //Station ID
        public String Station { get; }
        public string Name { get; } //Patient name
        public string Surname { get; } //Patient surname
        public String AgeGroup { get; }
        //Patient state: 1 = green ; 2 = yellow ; 3 = red
        public int State { get; }

        public Color ColState { get; set; }
        public string Room { get; } 
        //patient Status: activated = true ; deactivated = false
        public bool Status { get; }
        
        
        public Patient(int patientid, String pInsurance, String pStation, int pAge,
            string pName, string pSurname, int pState, string pRoom, bool pStatus)
        {
            Pid = patientid;
            InsuranceId = pInsurance;
            Name = pName;
            Surname = pSurname;
            Status = pStatus;
            if (pStatus)
            {
                State = pState;
                if (State == 1)
                {
                    ColState = Color.Green;
                }
                else if (State == 2)
                {
                    ColState = Color.Yellow;
                }
                else if (State == 3)
                {
                    ColState = Color.Red;
                }
                Room = pRoom;
                Station = pStation;
                pStation.Replace(" ", "");
                switch (pStation)
                {
                    case "AbteilungA":
                        Sid = 1;
                        break;
                    case "AbteilungB":
                        Sid = 2;
                        break;
                    case "AbteilungC":
                        Sid = 3;
                        break;
                    case "AbteilungD":
                        Sid = 4;
                        break;
                    case "AbteilungE":
                        Sid = 5;
                        break;
                }

                if (0 <= pAge && pAge < 18)
                {
                    AgeGroup = "Kind";
                }
                else if (18 <= pAge && pAge < 65)
                {
                    AgeGroup = "Erwachsen";
                }
                else if (65 <= pAge)
                {
                    AgeGroup = "Senior";
                }
            }
            else
            {
                Sid = -1;
                State = 0;
                Room = null;
                ColState = Color.White;
            }
        }

        public static LinkedList<Patient> GetPatients()
        {
            return patients;
        }
    }
}
