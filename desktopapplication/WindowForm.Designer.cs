namespace Desktopanwendung
{
    partial class WindowForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(WindowForm));
            this._Nachtpanel = new System.Windows.Forms.Panel();
            this._ShowAllCheckBox = new System.Windows.Forms.CheckBox();
            this._PrintNachtPanel = new System.Windows.Forms.Panel();
            this.menuStrip3 = new System.Windows.Forms.MenuStrip();
            this.modusWechselnToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this._NachtTagmodus = new System.Windows.Forms.ToolStripMenuItem();
            this._NachtNachtmodus = new System.Windows.Forms.ToolStripMenuItem();
            this.patientenverwaltungToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.patientenAnzeigenToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this._Tagpanel = new System.Windows.Forms.Panel();
            this._TabStation = new System.Windows.Forms.TabControl();
            this._Station1 = new System.Windows.Forms.TabPage();
            this._Station2 = new System.Windows.Forms.TabPage();
            this._Station3 = new System.Windows.Forms.TabPage();
            this._Station4 = new System.Windows.Forms.TabPage();
            this._Station5 = new System.Windows.Forms.TabPage();
            this.menuStrip2 = new System.Windows.Forms.MenuStrip();
            this.modusWechselnToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this._TagTagmodus = new System.Windows.Forms.ToolStripMenuItem();
            this.nachtmodusToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.patientenverwaltungToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.patientenAnzeigenToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this._Patientenpanel = new System.Windows.Forms.Panel();
            this._PrintPatientPanel = new System.Windows.Forms.Panel();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.modusWechselnToolStripMenuItem2 = new System.Windows.Forms.ToolStripMenuItem();
            this._PatientTagmodus = new System.Windows.Forms.ToolStripMenuItem();
            this._PatientNachtmodus = new System.Windows.Forms.ToolStripMenuItem();
            this.patientHinzufügenToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this._AddPatient = new System.Windows.Forms.ToolStripMenuItem();
            this._Nachtpanel.SuspendLayout();
            this.menuStrip3.SuspendLayout();
            this._Tagpanel.SuspendLayout();
            this._TabStation.SuspendLayout();
            this.menuStrip2.SuspendLayout();
            this._Patientenpanel.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // _Nachtpanel
            // 
            this._Nachtpanel.Controls.Add(this._ShowAllCheckBox);
            this._Nachtpanel.Controls.Add(this._PrintNachtPanel);
            this._Nachtpanel.Controls.Add(this.menuStrip3);
            this._Nachtpanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this._Nachtpanel.Location = new System.Drawing.Point(0, 0);
            this._Nachtpanel.Name = "_Nachtpanel";
            this._Nachtpanel.Size = new System.Drawing.Size(800, 449);
            this._Nachtpanel.TabIndex = 1;
            // 
            // _ShowAllCheckBox
            // 
            this._ShowAllCheckBox.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this._ShowAllCheckBox.AutoSize = true;
            this._ShowAllCheckBox.Location = new System.Drawing.Point(573, 38);
            this._ShowAllCheckBox.Name = "_ShowAllCheckBox";
            this._ShowAllCheckBox.Size = new System.Drawing.Size(202, 24);
            this._ShowAllCheckBox.TabIndex = 3;
            this._ShowAllCheckBox.Text = "Alle Patienten anzeigen";
            this._ShowAllCheckBox.UseVisualStyleBackColor = true;
            this._ShowAllCheckBox.CheckStateChanged += new System.EventHandler(this._ShowAllCheckBox_CheckStateChanged);
            // 
            // _PrintNachtPanel
            // 
            this._PrintNachtPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this._PrintNachtPanel.Location = new System.Drawing.Point(0, 65);
            this._PrintNachtPanel.Name = "_PrintNachtPanel";
            this._PrintNachtPanel.Size = new System.Drawing.Size(800, 386);
            this._PrintNachtPanel.TabIndex = 2;
            // 
            // menuStrip3
            // 
            this.menuStrip3.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.menuStrip3.AutoSize = false;
            this.menuStrip3.Dock = System.Windows.Forms.DockStyle.None;
            this.menuStrip3.ImageScalingSize = new System.Drawing.Size(24, 24);
            this.menuStrip3.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.modusWechselnToolStripMenuItem,
            this.patientenverwaltungToolStripMenuItem});
            this.menuStrip3.Location = new System.Drawing.Point(0, 0);
            this.menuStrip3.Name = "menuStrip3";
            this.menuStrip3.Size = new System.Drawing.Size(800, 32);
            this.menuStrip3.TabIndex = 1;
            this.menuStrip3.Text = "menuStrip3";
            // 
            // modusWechselnToolStripMenuItem
            // 
            this.modusWechselnToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._NachtTagmodus,
            this._NachtNachtmodus});
            this.modusWechselnToolStripMenuItem.Name = "modusWechselnToolStripMenuItem";
            this.modusWechselnToolStripMenuItem.Size = new System.Drawing.Size(156, 28);
            this.modusWechselnToolStripMenuItem.Text = "Modus wechseln";
            // 
            // _NachtTagmodus
            // 
            this._NachtTagmodus.Name = "_NachtTagmodus";
            this._NachtTagmodus.Size = new System.Drawing.Size(198, 30);
            this._NachtTagmodus.Text = "Tagmodus";
            this._NachtTagmodus.Click += new System.EventHandler(this._NachtTagmodus_Click);
            // 
            // _NachtNachtmodus
            // 
            this._NachtNachtmodus.Name = "_NachtNachtmodus";
            this._NachtNachtmodus.Size = new System.Drawing.Size(198, 30);
            this._NachtNachtmodus.Text = "Nachtmodus";
            // 
            // patientenverwaltungToolStripMenuItem
            // 
            this.patientenverwaltungToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.patientenAnzeigenToolStripMenuItem1});
            this.patientenverwaltungToolStripMenuItem.Name = "patientenverwaltungToolStripMenuItem";
            this.patientenverwaltungToolStripMenuItem.Size = new System.Drawing.Size(183, 28);
            this.patientenverwaltungToolStripMenuItem.Text = "Patientenverwaltung";
            // 
            // patientenAnzeigenToolStripMenuItem1
            // 
            this.patientenAnzeigenToolStripMenuItem1.Name = "patientenAnzeigenToolStripMenuItem1";
            this.patientenAnzeigenToolStripMenuItem1.Size = new System.Drawing.Size(243, 30);
            this.patientenAnzeigenToolStripMenuItem1.Text = "Patienten anzeigen";
            this.patientenAnzeigenToolStripMenuItem1.Click += new System.EventHandler(this.patientenAnzeigenToolStripMenuItem1_Click);
            // 
            // _Tagpanel
            // 
            this._Tagpanel.Controls.Add(this._TabStation);
            this._Tagpanel.Controls.Add(this.menuStrip2);
            this._Tagpanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this._Tagpanel.Location = new System.Drawing.Point(0, 0);
            this._Tagpanel.Name = "_Tagpanel";
            this._Tagpanel.Size = new System.Drawing.Size(800, 449);
            this._Tagpanel.TabIndex = 2;
            // 
            // _TabStation
            // 
            this._TabStation.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this._TabStation.Controls.Add(this._Station1);
            this._TabStation.Controls.Add(this._Station2);
            this._TabStation.Controls.Add(this._Station3);
            this._TabStation.Controls.Add(this._Station4);
            this._TabStation.Controls.Add(this._Station5);
            this._TabStation.Location = new System.Drawing.Point(3, 48);
            this._TabStation.Name = "_TabStation";
            this._TabStation.SelectedIndex = 0;
            this._TabStation.Size = new System.Drawing.Size(796, 400);
            this._TabStation.TabIndex = 2;
            this._TabStation.Selecting += new System.Windows.Forms.TabControlCancelEventHandler(this._TabStation_Selecting);
            // 
            // _Station1
            // 
            this._Station1.Location = new System.Drawing.Point(4, 29);
            this._Station1.Name = "_Station1";
            this._Station1.Padding = new System.Windows.Forms.Padding(3);
            this._Station1.Size = new System.Drawing.Size(788, 367);
            this._Station1.TabIndex = 0;
            this._Station1.Text = "Station 1";
            this._Station1.UseVisualStyleBackColor = true;
            // 
            // _Station2
            // 
            this._Station2.Location = new System.Drawing.Point(4, 29);
            this._Station2.Name = "_Station2";
            this._Station2.Padding = new System.Windows.Forms.Padding(3);
            this._Station2.Size = new System.Drawing.Size(788, 367);
            this._Station2.TabIndex = 1;
            this._Station2.Text = "Sation 2";
            this._Station2.UseVisualStyleBackColor = true;
            // 
            // _Station3
            // 
            this._Station3.Location = new System.Drawing.Point(4, 29);
            this._Station3.Name = "_Station3";
            this._Station3.Padding = new System.Windows.Forms.Padding(3);
            this._Station3.Size = new System.Drawing.Size(788, 367);
            this._Station3.TabIndex = 2;
            this._Station3.Text = "Sation 3";
            this._Station3.UseVisualStyleBackColor = true;
            // 
            // _Station4
            // 
            this._Station4.Location = new System.Drawing.Point(4, 29);
            this._Station4.Name = "_Station4";
            this._Station4.Padding = new System.Windows.Forms.Padding(3);
            this._Station4.Size = new System.Drawing.Size(788, 367);
            this._Station4.TabIndex = 3;
            this._Station4.Text = "Station 4";
            this._Station4.UseVisualStyleBackColor = true;
            // 
            // _Station5
            // 
            this._Station5.Location = new System.Drawing.Point(4, 29);
            this._Station5.Name = "_Station5";
            this._Station5.Padding = new System.Windows.Forms.Padding(3);
            this._Station5.Size = new System.Drawing.Size(788, 367);
            this._Station5.TabIndex = 4;
            this._Station5.Text = "Station 5";
            this._Station5.UseVisualStyleBackColor = true;
            // 
            // menuStrip2
            // 
            this.menuStrip2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.menuStrip2.AutoSize = false;
            this.menuStrip2.Dock = System.Windows.Forms.DockStyle.None;
            this.menuStrip2.ImageScalingSize = new System.Drawing.Size(24, 24);
            this.menuStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.modusWechselnToolStripMenuItem1,
            this.patientenverwaltungToolStripMenuItem1});
            this.menuStrip2.Location = new System.Drawing.Point(0, 0);
            this.menuStrip2.Name = "menuStrip2";
            this.menuStrip2.Size = new System.Drawing.Size(800, 32);
            this.menuStrip2.TabIndex = 1;
            this.menuStrip2.Text = "menuStrip2";
            // 
            // modusWechselnToolStripMenuItem1
            // 
            this.modusWechselnToolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._TagTagmodus,
            this.nachtmodusToolStripMenuItem1});
            this.modusWechselnToolStripMenuItem1.Name = "modusWechselnToolStripMenuItem1";
            this.modusWechselnToolStripMenuItem1.Size = new System.Drawing.Size(156, 28);
            this.modusWechselnToolStripMenuItem1.Text = "Modus wechseln";
            // 
            // _TagTagmodus
            // 
            this._TagTagmodus.Name = "_TagTagmodus";
            this._TagTagmodus.Size = new System.Drawing.Size(252, 30);
            this._TagTagmodus.Text = "Tagmodus";
            // 
            // nachtmodusToolStripMenuItem1
            // 
            this.nachtmodusToolStripMenuItem1.Name = "nachtmodusToolStripMenuItem1";
            this.nachtmodusToolStripMenuItem1.Size = new System.Drawing.Size(252, 30);
            this.nachtmodusToolStripMenuItem1.Text = "Nachtmodus";
            this.nachtmodusToolStripMenuItem1.Click += new System.EventHandler(this.NachtmodusToolStripMenuItem1_Click);
            // 
            // patientenverwaltungToolStripMenuItem1
            // 
            this.patientenverwaltungToolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.patientenAnzeigenToolStripMenuItem});
            this.patientenverwaltungToolStripMenuItem1.Name = "patientenverwaltungToolStripMenuItem1";
            this.patientenverwaltungToolStripMenuItem1.Size = new System.Drawing.Size(183, 28);
            this.patientenverwaltungToolStripMenuItem1.Text = "Patientenverwaltung";
            this.patientenverwaltungToolStripMenuItem1.Click += new System.EventHandler(this.patientenverwaltungToolStripMenuItem1_Click);
            // 
            // patientenAnzeigenToolStripMenuItem
            // 
            this.patientenAnzeigenToolStripMenuItem.Name = "patientenAnzeigenToolStripMenuItem";
            this.patientenAnzeigenToolStripMenuItem.Size = new System.Drawing.Size(243, 30);
            this.patientenAnzeigenToolStripMenuItem.Text = "Patienten anzeigen";
            this.patientenAnzeigenToolStripMenuItem.Click += new System.EventHandler(this.patientenAnzeigenToolStripMenuItem_Click);
            // 
            // _Patientenpanel
            // 
            this._Patientenpanel.Controls.Add(this._PrintPatientPanel);
            this._Patientenpanel.Controls.Add(this.menuStrip1);
            this._Patientenpanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this._Patientenpanel.Location = new System.Drawing.Point(0, 0);
            this._Patientenpanel.Name = "_Patientenpanel";
            this._Patientenpanel.Size = new System.Drawing.Size(800, 449);
            this._Patientenpanel.TabIndex = 3;
            // 
            // _PrintPatientPanel
            // 
            this._PrintPatientPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this._PrintPatientPanel.Location = new System.Drawing.Point(0, 35);
            this._PrintPatientPanel.Name = "_PrintPatientPanel";
            this._PrintPatientPanel.Size = new System.Drawing.Size(800, 414);
            this._PrintPatientPanel.TabIndex = 1;
            // 
            // menuStrip1
            // 
            this.menuStrip1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.menuStrip1.AutoSize = false;
            this.menuStrip1.Dock = System.Windows.Forms.DockStyle.None;
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(24, 24);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.modusWechselnToolStripMenuItem2,
            this.patientHinzufügenToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(800, 32);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // modusWechselnToolStripMenuItem2
            // 
            this.modusWechselnToolStripMenuItem2.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._PatientTagmodus,
            this._PatientNachtmodus});
            this.modusWechselnToolStripMenuItem2.Name = "modusWechselnToolStripMenuItem2";
            this.modusWechselnToolStripMenuItem2.Size = new System.Drawing.Size(156, 28);
            this.modusWechselnToolStripMenuItem2.Text = "Modus wechseln";
            // 
            // _PatientTagmodus
            // 
            this._PatientTagmodus.Name = "_PatientTagmodus";
            this._PatientTagmodus.Size = new System.Drawing.Size(198, 30);
            this._PatientTagmodus.Text = "Tagmodus";
            this._PatientTagmodus.Click += new System.EventHandler(this._PatientTagmodus_Click);
            // 
            // _PatientNachtmodus
            // 
            this._PatientNachtmodus.Name = "_PatientNachtmodus";
            this._PatientNachtmodus.Size = new System.Drawing.Size(198, 30);
            this._PatientNachtmodus.Text = "Nachtmodus";
            this._PatientNachtmodus.Click += new System.EventHandler(this._PatientNachtmodus_Click);
            // 
            // patientHinzufügenToolStripMenuItem
            // 
            this.patientHinzufügenToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._AddPatient});
            this.patientHinzufügenToolStripMenuItem.Name = "patientHinzufügenToolStripMenuItem";
            this.patientHinzufügenToolStripMenuItem.Size = new System.Drawing.Size(183, 28);
            this.patientHinzufügenToolStripMenuItem.Text = "Patientenverwaltung";
            // 
            // _AddPatient
            // 
            this._AddPatient.Name = "_AddPatient";
            this._AddPatient.Size = new System.Drawing.Size(242, 30);
            this._AddPatient.Text = "Patient hinzufügen";
            this._AddPatient.Click += new System.EventHandler(this._AddPatient_Click);
            // 
            // WindowForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 449);
            this.Controls.Add(this._Tagpanel);
            this.Controls.Add(this._Nachtpanel);
            this.Controls.Add(this._Patientenpanel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "WindowForm";
            this.Text = "EWS - Early Warning System";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.WindowForm_FormClosed);
            this.Load += new System.EventHandler(this.WindowForm_Load);
            this._Nachtpanel.ResumeLayout(false);
            this._Nachtpanel.PerformLayout();
            this.menuStrip3.ResumeLayout(false);
            this.menuStrip3.PerformLayout();
            this._Tagpanel.ResumeLayout(false);
            this._TabStation.ResumeLayout(false);
            this.menuStrip2.ResumeLayout(false);
            this.menuStrip2.PerformLayout();
            this._Patientenpanel.ResumeLayout(false);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Panel _Nachtpanel;
        private System.Windows.Forms.MenuStrip menuStrip3;
        private System.Windows.Forms.ToolStripMenuItem modusWechselnToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem _NachtTagmodus;
        private System.Windows.Forms.ToolStripMenuItem _NachtNachtmodus;
        private System.Windows.Forms.Panel _Tagpanel;
        private System.Windows.Forms.MenuStrip menuStrip2;
        private System.Windows.Forms.Panel _Patientenpanel;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem modusWechselnToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem _TagTagmodus;
        private System.Windows.Forms.ToolStripMenuItem nachtmodusToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem modusWechselnToolStripMenuItem2;
        private System.Windows.Forms.ToolStripMenuItem patientHinzufügenToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem patientenverwaltungToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem patientenAnzeigenToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem patientenverwaltungToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem patientenAnzeigenToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem _PatientTagmodus;
        private System.Windows.Forms.ToolStripMenuItem _PatientNachtmodus;
        private System.Windows.Forms.ToolStripMenuItem _AddPatient;
        private System.Windows.Forms.TabControl _TabStation;
        private System.Windows.Forms.TabPage _Station2;
        private System.Windows.Forms.TabPage _Station3;
        private System.Windows.Forms.TabPage _Station4;
        private System.Windows.Forms.TabPage _Station5;
        private System.Windows.Forms.TabPage _Station1;
        private System.Windows.Forms.Panel _PrintNachtPanel;
        private System.Windows.Forms.Panel _PrintPatientPanel;
        private System.Windows.Forms.CheckBox _ShowAllCheckBox;
    }
}

