using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Remoting.Channels;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Timers;
using System.Windows.Forms;

namespace Desktopanwendung
{
    public partial class WindowForm : Form
    {
        private Object thisLock = new Object();
        private static WindowForm form;
        private bool end = false;
        enum Mode { Day, Night, Patient }

        private int _currentmode = (int)Mode.Day;
        private DataGridView _patientsDataGridView;

        private WindowForm()
        {
            Bounds = Screen.PrimaryScreen.Bounds;
            WindowState = FormWindowState.Maximized;
            InitializeComponent();
            _TagTagmodus.Enabled = false;
            _NachtNachtmodus.Enabled = false;

        }

        public static WindowForm GetForm()
        {
            if (form == null)
            {
                form = new WindowForm();
            }
            return form;
        }

        

        private void NachtmodusToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            GoToNight();
        }

        private void _NachtTagmodus_Click(object sender, EventArgs e)
        {
            GoToDay();
        }

        private void patientenAnzeigenToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            Patientenmode();
        }

        private void patientenAnzeigenToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Patientenmode();
        }

        private void _PatientTagmodus_Click(object sender, EventArgs e)
        {
            GoToDay();
        }

        private void _PatientNachtmodus_Click(object sender, EventArgs e)
        {
            GoToNight();
        }

        //Change to Patient Panel
        private void Patientenmode()
        {
            _Nachtpanel.Visible = false;
            _Tagpanel.Visible = false;
            _Patientenpanel.Visible = true;
            _currentmode = (int)Mode.Patient;
            PrintGui(false);
        }

        //Change to Night Panel
        private void GoToNight()
        {
            _Tagpanel.Visible = false;
            _Patientenpanel.Visible = false;
            _Nachtpanel.Visible = true;
            _currentmode = (int)Mode.Night;
            PrintGui(false);
        }

        //Change to Day Panel
        private void GoToDay()
        {
            _Nachtpanel.Visible = false;
            _Patientenpanel.Visible = false;
            _Tagpanel.Visible = true;
            _currentmode = (int)Mode.Day;
            PrintGui(false);
        }

        private void _AddPatient_Click(object sender, EventArgs e)
        {
            AddNewPatientForm f2 = new AddNewPatientForm();
            f2.Show();
        }
        
        private void _TabStation_Selecting(object sender, TabControlCancelEventArgs e)
        {
            PrintGui(false);
        }

        //Main Print Gui. Decides based on the current mode which gui should be printed
        public void PrintGui(bool fetch)
        {
            MethodInvoker invoker = delegate
            {
                lock (thisLock) 
                {
                    if (_currentmode == (int)Mode.Day)
                    {
                        PrintGuiDay();
                    }
                    else if (_currentmode == (int)Mode.Night)
                    {
                        PrintGuiNight();
                    }
                    else if (_currentmode == (int)Mode.Patient && !fetch)
                    {
                        PrintGuiPatient();
                    }
                }
            };
            try
            {
                Invoke(invoker);
            }
            catch (ObjectDisposedException ex)
            {

            }


        }

        //prints the gui for the day mode
        //based on the station(1-5) no matter which state, all patients are displayed
        private void PrintGuiDay()
        {
            _TabStation.TabPages[_TabStation.SelectedIndex].Controls.Clear();

            InitializeGrid();

            LinkedListNode<Patient> node = Patient.GetPatients().First;
            int index = 0;
            while (node != null)
            {
                if (node.Value.Sid == (_TabStation.SelectedIndex + 1))  //_TabStation.SelectedTab.Text
                {
                    _patientsDataGridView.Rows.Insert(index, node.Value.Pid,
                        node.Value.Surname, node.Value.Name, node.Value.AgeGroup, node.Value.Room);
                    _patientsDataGridView.Rows[index].Cells[5].Style.BackColor = node.Value.ColState;
                    index++;
                }
                node = node.Next;
            }

            _TabStation.TabPages[_TabStation.SelectedIndex].Controls.Add(_patientsDataGridView);


        }

        //prints the gui for the night ode
        //all patients with yellow or red state are displayed
        private void PrintGuiNight()
        {
            _PrintNachtPanel.Controls.Clear();
            InitializeGrid();

            LinkedListNode<Patient> node = Patient.GetPatients().First;
            int index = 0;
            while (node != null)
            {
                if ((node.Value.State == 1 && _ShowAllCheckBox.Checked) || node.Value.State == 2 || node.Value.State == 3)
                {
                    _patientsDataGridView.Rows.Insert(index, node.Value.Pid,
                        node.Value.Surname, node.Value.Name, node.Value.AgeGroup,
                        node.Value.Station, node.Value.Room);
                    _patientsDataGridView.Rows[index].Cells[6].Style.BackColor = node.Value.ColState;
                    index++;
                }

                node = node.Next;
            }

            _PrintNachtPanel.Controls.Add(_patientsDataGridView);
        

        }

        //prints the gui for the patientadministration mode
        //all patients are ordered by surname, then by the name and are displayed
        private void PrintGuiPatient()
        {
            
            _PrintPatientPanel.Controls.Clear();
            InitializeGrid();

            _patientsDataGridView.CellContentClick += patientsDataGridView_CellContentClick;

            var sortedPatients = Patient.GetPatients()
                .OrderBy(x => x.Surname)
                .ThenBy(x => x.Name)
                .ToList();

            int index = 0;
            foreach (Patient element in sortedPatients)
            {
                _patientsDataGridView.Rows.Insert(index, element.Pid, element.Surname, element.Name, element.Station, element.Room);
                index++;
            }

            _PrintPatientPanel.Controls.Add(_patientsDataGridView);
        

        }

        //Initialize the Grids for the respective mode
        private void InitializeGrid()
        {
            _patientsDataGridView = new DataGridView
            {
                AllowDrop = false,
                AllowUserToAddRows = false,
                AllowUserToDeleteRows = false,
                AllowUserToResizeColumns = false,
                AllowUserToOrderColumns = false,
                AllowUserToResizeRows = false,
                RowHeadersWidthSizeMode = DataGridViewRowHeadersWidthSizeMode.DisableResizing,
                ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.DisableResizing,

                ReadOnly = true,
                Enabled = true,
                RowHeadersVisible = false,
                EnableHeadersVisualStyles = false,
                ScrollBars = ScrollBars.Vertical,

                Columns =
                    {
                        {"cID", "ID"},
                        {"cSurname", "Name"},
                        {"cName", "Vorname"},
                        {"cAge", "Altersgruppe"},
                        {"cStation", "Station"},
                        {"cRoomm", "Raum"},
                        {"cState", "Status"}
                    },

                Anchor = AnchorStyles.Bottom,

            };

            _patientsDataGridView.RowsDefaultCellStyle.SelectionBackColor = Color.White;
            _patientsDataGridView.RowsDefaultCellStyle.SelectionForeColor = Color.Black;


            if (_currentmode == (int)Mode.Night)
            {
                _patientsDataGridView.Width = _PrintNachtPanel.Width;
                _patientsDataGridView.Height = _PrintNachtPanel.Height;
            }
            else if (_currentmode == (int)Mode.Day)
            {
                _patientsDataGridView.Columns.Remove("cStation");
                _patientsDataGridView.ScrollBars = ScrollBars.None;
                _patientsDataGridView.Enabled = false;
                _patientsDataGridView.Width = _TabStation.SelectedTab.Width;
                _patientsDataGridView.Height = _TabStation.SelectedTab.Height;
            }
            else if (_currentmode == (int)Mode.Patient)
            {
                _patientsDataGridView.Columns.Remove("cState");
                _patientsDataGridView.Columns.Remove("cAge");


                DataGridViewCheckBoxColumn checkBoxColumn = new DataGridViewCheckBoxColumn();
                checkBoxColumn.Name = "checkBoxColumn";
                checkBoxColumn.HeaderText = "Entlassen";
                checkBoxColumn.FalseValue = false;
                checkBoxColumn.TrueValue = true;
                checkBoxColumn.CellTemplate.Value = false;
                checkBoxColumn.CellTemplate.Style.NullValue = false;

                _patientsDataGridView.Columns.Add(checkBoxColumn);

                _patientsDataGridView.Width = _PrintPatientPanel.Width;
                _patientsDataGridView.Height = _PrintPatientPanel.Height;
                _patientsDataGridView.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            }

            int columnsCount = _patientsDataGridView.ColumnCount;

            for (int i = 0; i < columnsCount; i++)
            {
                _patientsDataGridView.Columns[i].Width = _patientsDataGridView.Width / columnsCount;
                _patientsDataGridView.Columns[i].SortMode = DataGridViewColumnSortMode.NotSortable;
                _patientsDataGridView.Columns[i].Resizable = DataGridViewTriState.False;

            }
        }

        //gets called if a cellcontent is clicked 
        //if the checkbox is checked, the corresponding patient will be deactivated
        private void patientsDataGridView_CellContentClick(Object sender, DataGridViewCellEventArgs e)
        {
            Console.WriteLine(((DataGridView)sender).CurrentCell.ColumnIndex + " " + _patientsDataGridView.ColumnCount);
            
            try
            {
                if (((DataGridView)sender).CurrentCell.ColumnIndex == _patientsDataGridView.ColumnCount - 1)
                {
                    int rowIndex = ((DataGridView)sender).CurrentCell.RowIndex;
                    
                    DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)_patientsDataGridView.Rows[rowIndex].Cells[_patientsDataGridView.ColumnCount -1];

                    if (checkBoxCell.Value == null)
                    {
                        checkBoxCell.Value = false;
                    }

                    switch (checkBoxCell.Value.ToString())
                    {
                        case "True":
                            break;
                        case "False":
                            checkBoxCell.Value = true;
                            checkBox(sender, checkBoxCell);
                            break;
                    }
                }
            }
            catch (Exception ex)
            {

            }
        }

        private void checkBox(object sender, DataGridViewCheckBoxCell checkBoxCell)
        {
            int rowIndex = ((DataGridView)sender).CurrentCell.RowIndex;
            String surname = ((DataGridView)sender).Rows[rowIndex].Cells[1].Value.ToString();
            String name = ((DataGridView)sender).Rows[rowIndex].Cells[2].Value.ToString();

            DialogResult dialogResult =
                MessageBox.Show("Patient entlassen?\n\n" + surname + " " + name,
                    "Patient entlassen", MessageBoxButtons.YesNo);


            if (dialogResult == DialogResult.Yes)
            {
                checkBoxCell.Value = true;
                checkBoxCell.FlatStyle = FlatStyle.Flat;
                checkBoxCell.Style.ForeColor = Color.LightGray;
                _patientsDataGridView.Rows[rowIndex].DefaultCellStyle.ForeColor = Color.LightGray;
                _patientsDataGridView.Rows[rowIndex].DefaultCellStyle.SelectionForeColor = Color.LightGray;

                String patientId = ((DataGridView)sender).Rows[rowIndex].Cells[0].Value.ToString();

                String ret = SendToServer.DeactivatePatient(patientId);
                String[] split = ret.Split(';');
                if (split[0] == "DELETE_SUCCEDED")
                {
                    Patient deletePatient = new Patient(Int32.Parse(patientId), null, null, 0, null, null, 0, null, false);
                    Request.AddToList(deletePatient);
                    PrintGui(false);
                    MessageBox.Show("Patient erfolgreich entlassen", "Deaktivierung erfolgreich");
                }
                else if (split[0] == "DELETE_FAILED")
                {
                    MessageBox.Show("Patient konnte nicht entfernt werden", "Deaktivierung fehlgeschlagen");
                    checkBoxCell.Value = false;
                    checkBoxCell.FlatStyle = FlatStyle.Standard;
                    checkBoxCell.Style.ForeColor = Color.Black;
                    _patientsDataGridView.Rows[rowIndex].DefaultCellStyle.ForeColor = Color.Black;
                    _patientsDataGridView.Rows[rowIndex].DefaultCellStyle.SelectionForeColor = Color.Black;

                }
            }
            else
            {
                checkBoxCell.Value = false;
            }
        }

        private void _ShowAllCheckBox_CheckStateChanged(object sender, EventArgs e)
        {
            PrintGui(false);
        }

        private void patientenverwaltungToolStripMenuItem1_Click(object sender, EventArgs e)
        {

        }

        private void WindowForm_Load(object sender, EventArgs e)
        {
            PrintGui(false);
        }

        private void WindowForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            end = true;
        }

        public bool getEnd()
        {
            return end;
        }
    }
}
