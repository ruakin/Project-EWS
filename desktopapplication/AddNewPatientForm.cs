using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Desktopanwendung
{
    public partial class AddNewPatientForm : Form
    {
        public AddNewPatientForm()
        {
            InitializeComponent();
        }


        private void _PName_MouseClick(object sender, MouseEventArgs e)
        {
            _PName.ForeColor = Color.Black;
            if (_PName.Text == "Name")
            {
                _PName.Text = "";
            }
        }

        private void _PName_Leave(object sender, EventArgs e)
        {
            if (_PName.Text == "") 
            {
                _PName.Text = "Name";
            }
        }

        private void _PVName_MouseClick(object sender, MouseEventArgs e)
        {
            _PVName.ForeColor = Color.Black;
            if (_PVName.Text == "Vorname")
            {
                _PVName.Text = "";
            }
        }

        private void _PVName_Leave(object sender, EventArgs e)
        {
            if (_PVName.Text == "")
            {
                _PVName.Text = "Vorname";
            }
        }

        private void _AddPatientBut_Click(object sender, EventArgs e)
        {
            bool error = false;

            if (_PVName.Text.Equals("Vorname") || _PVName.Text.Equals(""))
            {
                error = true;
                _PVName.ForeColor = Color.Red;
            }

            if (_PName.Text.Equals("Name") || _PName.Text.Equals(""))
            {
                error = true;
                _PName.ForeColor = Color.Red;
            }

            if (_DayBirthBox.Text.Equals("TT") || _DayBirthBox.Text.Equals(""))
            {
                error = true;
                _DayBirthBox.ForeColor = Color.Red;
            }

            if (_MonthBirthBox.Text.Equals("MM") || _MonthBirthBox.Text.Equals(""))
            {
                error = true;
                _MonthBirthBox.ForeColor = Color.Red;
            }

            if (_YearBirthBox.Text.Equals("JJJJ") || _YearBirthBox.Text.Equals(""))
            {
                error = true;
                _YearBirthBox.ForeColor = Color.Red;
            }

            if (_InsuranceBox.Text.Equals("Versichertennummer") || _InsuranceBox.Text.Equals("") || _InsuranceBox.Text.Length < 10)
            {
                error = true;
                _InsuranceBox.ForeColor = Color.Red;
            }


            if (!error)
            {
                sending();
            }

        }

        private void sending()
        {
            String name = "null";
            if (_PVName.Text != "Vorname")
            {
                name = _PVName.Text;
            }

            String surname = "null";
            if (_PName.Text != "Name")
            {
                surname = _PName.Text;
            }

            String insurance = _InsuranceBox.Text;

            String date = "null";
            int age = -1;
            if (_DayBirthBox.Text != "TT" && _MonthBirthBox.Text != "MM"
                                          && _YearBirthBox.Text != "JJJJ")
            {
                Int32.TryParse(_DayBirthBox.Text, out int day);
                String dayString = day.ToString();
                if (day < 10)
                {
                    dayString = "0" + day;
                }

                Int32.TryParse(_MonthBirthBox.Text, out int month);
                String monthString = month.ToString();
                if (month < 10)
                {
                    monthString = "0" + month;
                }

                Int32.TryParse(_YearBirthBox.Text, out int year);
                
                date = _YearBirthBox.Text + "-" + monthString + "-" + dayString;

                DateTime now = DateTime.Now;
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
            
            

            String diagnostics = "null,null,null,null,null,null";

            String ret = SendToServer.AddPatient(insurance, surname, name, date, diagnostics);

            String[] split = ret.Split(',', ';');
            if (split[0] == "INSERT_SUCCEDED")
            {
                MessageBox.Show("Patient " + surname + ", " + name +"\nwurde erfolgreich hinzugefügt.", "Hinzufügen erfolgreich");
                int id = Int32.Parse(split[1]);
                String station = split[3];
                String room = split[4];
                Patient newPatient = new Patient(id, insurance, station, age, name, surname, 1, room, true);
                Request.AddToList(newPatient);
                WindowForm.GetForm().PrintGui(false);
                Close();
            }
            else if (split[0] == "INSERT_FAILED")
            {
                MessageBox.Show("Patient " + surname + ", " + name + "\nkonnte nicht hinzugefügt werden.", "Hinzufügen fehlgeschlagen");
            }
        }

        private void _TagGebBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (_DayBirthBox.Text == "TT")
            {
                _DayBirthBox.Text = "";
            }
            if (_DayBirthBox.Text.Length < 2 || e.KeyCode < Keys.Help)
            {
                if (!('0' <= e.KeyValue && e.KeyValue <= '9' || e.KeyCode < Keys.Help))
                {
                    e.SuppressKeyPress = true;
                }
            }
            else
            {
                e.SuppressKeyPress = true;
            }
        }

        private void _MonthBirthBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (_MonthBirthBox.Text == "MM")
            {
                _MonthBirthBox.Text = "";
            }
            if (_MonthBirthBox.Text.Length < 2 || e.KeyCode < Keys.Help)
            {
                if (!('0' <= e.KeyValue && e.KeyValue <= '9' || e.KeyCode < Keys.Help))
                {
                    e.SuppressKeyPress = true;
                }
            }
            else
            {
                e.SuppressKeyPress = true;
            }
            
        }

        private void _YearBirthBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (_YearBirthBox.Text == "JJJJ")
            {
                _YearBirthBox.Text = "";
            }
            if (_YearBirthBox.Text.Length < 4 || (e.KeyCode < Keys.Help))
            {
                if (!('0' <= e.KeyValue && e.KeyValue <= '9' || e.KeyCode < Keys.Help))
                {
                    e.SuppressKeyPress = true;
                }
            }
            else
            {
                e.SuppressKeyPress = true;
            }
            

            
            
        }

        private void _DayBirthBox_MouseClick(object sender, MouseEventArgs e)
        {
            _DayBirthBox.ForeColor = Color.Black;
            if (_DayBirthBox.Text == "TT")
            {
                _DayBirthBox.Text = "";
            }
        }

        private void _DayBirthBox_Leave(object sender, EventArgs e)
        {
            if (_DayBirthBox.Text == "TT" || _DayBirthBox.Text == "" || Int32.Parse(_DayBirthBox.Text) < 1 || Int32.Parse(_DayBirthBox.Text) > 31)
            {
                _DayBirthBox.Text = "TT";
            }
            
        }

        private void _MonthBirthBox_MouseClick(object sender, MouseEventArgs e)
        {
            _MonthBirthBox.ForeColor = Color.Black;
            if (_MonthBirthBox.Text == "MM")
            {
                _MonthBirthBox.Text = "";
            }
        }

        private void _MonthBirthBox_Leave(object sender, EventArgs e)
        {
            if (_MonthBirthBox.Text == "MM" || _MonthBirthBox.Text == "" || Int32.Parse(_MonthBirthBox.Text) < 1 || Int32.Parse(_MonthBirthBox.Text) > 12)
            {
                _MonthBirthBox.Text = "MM";
            }
        }

        private void _YearBirthBox_MouseClick(object sender, MouseEventArgs e)
        {
            _YearBirthBox.ForeColor = Color.Black;
            if (_YearBirthBox.Text == "JJJJ")
            {
                _YearBirthBox.Text = "";
            }
        }

        private void _YearBirthBox_Leave(object sender, EventArgs e)
        {
            DateTime yearDateTime = DateTime.Now;
            if (_YearBirthBox.Text == "JJJJ" || _YearBirthBox.Text == "" || Int32.Parse(_YearBirthBox.Text) < 1900 || Int32.Parse(_YearBirthBox.Text) > yearDateTime.Year)
            {
                _YearBirthBox.Text = "JJJJ";
            }
        }

        private void _InsuranceBox_Leave(object sender, EventArgs e)
        {
            if (_InsuranceBox.Text == "")
            {
                _InsuranceBox.Text = "Versichertennummer";
            }
        }

        private void _InsuranceBox_MouseClick(object sender, MouseEventArgs e)
        {
            _InsuranceBox.ForeColor = Color.Black;
            if (_InsuranceBox.Text == "Versichertennummer")
            {
                _InsuranceBox.Text = "";
            }
        }

        private void _InsuranceBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (_InsuranceBox.Text == "Versichertennummer")
            {
                _InsuranceBox.Text = "";
            }

            if ((_InsuranceBox.Text.Length > 0 && _InsuranceBox.Text.Length < 10) || e.KeyCode < Keys.Help)
            {
                if (!('0' <= e.KeyValue && e.KeyValue <= '9' || e.KeyCode < Keys.Help))
                {
                    e.SuppressKeyPress = true;
                    
                }

            }
            else if(_InsuranceBox.Text.Length < 1)
            {
                if (!(65 <= e.KeyValue && e.KeyValue < 91 || e.KeyCode < Keys.Help))
                {

                    e.SuppressKeyPress = true;
                }
            }
            else
            {
                e.SuppressKeyPress = true;
            }
        }
    }
}
