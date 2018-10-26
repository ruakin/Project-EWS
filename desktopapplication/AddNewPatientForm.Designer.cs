namespace Desktopanwendung
{
    partial class AddNewPatientForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AddNewPatientForm));
            this._AddPanel = new System.Windows.Forms.Panel();
            this._InsuranceBox = new System.Windows.Forms.TextBox();
            this._BorderLabel = new System.Windows.Forms.Label();
            this._PVName = new System.Windows.Forms.TextBox();
            this._PName = new System.Windows.Forms.TextBox();
            this._Birthday = new System.Windows.Forms.Label();
            this._DayBirthBox = new System.Windows.Forms.TextBox();
            this._MonthBirthBox = new System.Windows.Forms.TextBox();
            this._YearBirthBox = new System.Windows.Forms.TextBox();
            this._AddPatientBut = new System.Windows.Forms.Button();
            this._AddPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // _AddPanel
            // 
            this._AddPanel.Controls.Add(this._InsuranceBox);
            this._AddPanel.Controls.Add(this._BorderLabel);
            this._AddPanel.Controls.Add(this._PVName);
            this._AddPanel.Controls.Add(this._PName);
            this._AddPanel.Controls.Add(this._Birthday);
            this._AddPanel.Controls.Add(this._DayBirthBox);
            this._AddPanel.Controls.Add(this._MonthBirthBox);
            this._AddPanel.Controls.Add(this._YearBirthBox);
            this._AddPanel.Controls.Add(this._AddPatientBut);
            this._AddPanel.Location = new System.Drawing.Point(-1, 0);
            this._AddPanel.Name = "_AddPanel";
            this._AddPanel.Size = new System.Drawing.Size(438, 451);
            this._AddPanel.TabIndex = 0;
            // 
            // _InsuranceBox
            // 
            this._InsuranceBox.Location = new System.Drawing.Point(39, 136);
            this._InsuranceBox.Name = "_InsuranceBox";
            this._InsuranceBox.Size = new System.Drawing.Size(173, 26);
            this._InsuranceBox.TabIndex = 5;
            this._InsuranceBox.Text = "Versichertennummer";
            this._InsuranceBox.MouseClick += new System.Windows.Forms.MouseEventHandler(this._InsuranceBox_MouseClick);
            this._InsuranceBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this._InsuranceBox_KeyDown);
            this._InsuranceBox.Leave += new System.EventHandler(this._InsuranceBox_Leave);
            // 
            // _BorderLabel
            // 
            this._BorderLabel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this._BorderLabel.Location = new System.Drawing.Point(13, 180);
            this._BorderLabel.Name = "_BorderLabel";
            this._BorderLabel.Size = new System.Drawing.Size(413, 2);
            this._BorderLabel.TabIndex = 12;
            // 
            // _PVName
            // 
            this._PVName.Location = new System.Drawing.Point(227, 31);
            this._PVName.Name = "_PVName";
            this._PVName.Size = new System.Drawing.Size(173, 26);
            this._PVName.TabIndex = 1;
            this._PVName.Text = "Vorname";
            this._PVName.MouseClick += new System.Windows.Forms.MouseEventHandler(this._PVName_MouseClick);
            this._PVName.Leave += new System.EventHandler(this._PVName_Leave);
            // 
            // _PName
            // 
            this._PName.Location = new System.Drawing.Point(39, 31);
            this._PName.Name = "_PName";
            this._PName.Size = new System.Drawing.Size(173, 26);
            this._PName.TabIndex = 0;
            this._PName.Text = "Name";
            this._PName.MouseClick += new System.Windows.Forms.MouseEventHandler(this._PName_MouseClick);
            this._PName.Leave += new System.EventHandler(this._PName_Leave);
            // 
            // _Birthday
            // 
            this._Birthday.AutoSize = true;
            this._Birthday.Location = new System.Drawing.Point(35, 72);
            this._Birthday.Name = "_Birthday";
            this._Birthday.Size = new System.Drawing.Size(112, 20);
            this._Birthday.TabIndex = 11;
            this._Birthday.Text = "Geburtsdatum";
            // 
            // _DayBirthBox
            // 
            this._DayBirthBox.Location = new System.Drawing.Point(39, 95);
            this._DayBirthBox.Name = "_DayBirthBox";
            this._DayBirthBox.Size = new System.Drawing.Size(38, 26);
            this._DayBirthBox.TabIndex = 2;
            this._DayBirthBox.Text = "TT";
            this._DayBirthBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this._DayBirthBox.MouseClick += new System.Windows.Forms.MouseEventHandler(this._DayBirthBox_MouseClick);
            this._DayBirthBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this._TagGebBox_KeyDown);
            this._DayBirthBox.Leave += new System.EventHandler(this._DayBirthBox_Leave);
            // 
            // _MonthBirthBox
            // 
            this._MonthBirthBox.Location = new System.Drawing.Point(83, 95);
            this._MonthBirthBox.Name = "_MonthBirthBox";
            this._MonthBirthBox.Size = new System.Drawing.Size(38, 26);
            this._MonthBirthBox.TabIndex = 3;
            this._MonthBirthBox.Text = "MM";
            this._MonthBirthBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this._MonthBirthBox.MouseClick += new System.Windows.Forms.MouseEventHandler(this._MonthBirthBox_MouseClick);
            this._MonthBirthBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this._MonthBirthBox_KeyDown);
            this._MonthBirthBox.Leave += new System.EventHandler(this._MonthBirthBox_Leave);
            // 
            // _YearBirthBox
            // 
            this._YearBirthBox.Location = new System.Drawing.Point(127, 95);
            this._YearBirthBox.Name = "_YearBirthBox";
            this._YearBirthBox.Size = new System.Drawing.Size(46, 26);
            this._YearBirthBox.TabIndex = 4;
            this._YearBirthBox.Text = "JJJJ";
            this._YearBirthBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this._YearBirthBox.MouseClick += new System.Windows.Forms.MouseEventHandler(this._YearBirthBox_MouseClick);
            this._YearBirthBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this._YearBirthBox_KeyDown);
            this._YearBirthBox.Leave += new System.EventHandler(this._YearBirthBox_Leave);
            // 
            // _AddPatientBut
            // 
            this._AddPatientBut.Location = new System.Drawing.Point(101, 398);
            this._AddPatientBut.Name = "_AddPatientBut";
            this._AddPatientBut.Size = new System.Drawing.Size(240, 40);
            this._AddPatientBut.TabIndex = 6;
            this._AddPatientBut.Text = "Patient hinzufügen";
            this._AddPatientBut.UseVisualStyleBackColor = true;
            this._AddPatientBut.Click += new System.EventHandler(this._AddPatientBut_Click);
            // 
            // AddNewPatientForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(437, 450);
            this.Controls.Add(this._AddPanel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "AddNewPatientForm";
            this.Text = "Patient hinzufügen";
            this._AddPanel.ResumeLayout(false);
            this._AddPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel _AddPanel;
        private System.Windows.Forms.TextBox _PVName;
        private System.Windows.Forms.TextBox _PName;
        private System.Windows.Forms.Button _AddPatientBut;
        private System.Windows.Forms.TextBox _DayBirthBox;
        private System.Windows.Forms.TextBox _MonthBirthBox;
        private System.Windows.Forms.TextBox _YearBirthBox;
        private System.Windows.Forms.Label _Birthday;
        private System.Windows.Forms.Label _BorderLabel;
        private System.Windows.Forms.TextBox _InsuranceBox;
    }
}