using System;
using System.Collections.Generic;
using System.Net.Cache;
using Desktopanwendung;
using Microsoft.VisualStudio.TestTools.UnitTesting;


namespace UnitTestProject
{
    [TestClass]
    public class UnitTest1
    {
        /* PatientAgeGroupTest tests the Patient class and the constructor
         * tests if the values are sorted incorrectly and the agegroup sorting into "Kind, Erwachsen and Senior"
         */
        [TestMethod]
        public void PatientAgeGroupTest()
        {
            Patient pKid = new Patient(1, "1234567891", "1", 10, "Max", "Mustermann", 1, "15", true);
            Assert.AreEqual(pKid.Pid,1);
            Assert.AreEqual(pKid.InsuranceId, "1234567891");
            Assert.AreEqual(pKid.Station, "1");
            Assert.AreEqual(pKid.AgeGroup, "Kind");
            Assert.AreEqual(pKid.Name, "Max");
            Assert.AreEqual(pKid.Surname, "Mustermann");
            Assert.AreEqual(pKid.State, 1);
            Assert.AreEqual(pKid.Room, "15");
            Assert.AreEqual(pKid.Status, true);

            Patient pAdult = new Patient(2, "1234567891", "1", 18, "Fritz", "Mustermann", 1, "15", true);
            Assert.AreEqual(pAdult.Pid, 2);
            Assert.AreEqual(pAdult.InsuranceId, "1234567891");
            Assert.AreEqual(pAdult.Station, "1");
            Assert.AreEqual(pAdult.AgeGroup, "Erwachsen");
            Assert.AreEqual(pAdult.Name, "Fritz");
            Assert.AreEqual(pAdult.Surname, "Mustermann");
            Assert.AreEqual(pAdult.State, 1);
            Assert.AreEqual(pAdult.Room, "15");
            Assert.AreEqual(pAdult.Status, true);

            Patient pSenior = new Patient(3, "1234567891", "1", 65, "Günter", "Mustermann", 1, "15", true);
            Assert.AreEqual(pSenior.Pid, 3);
            Assert.AreEqual(pSenior.InsuranceId, "1234567891");
            Assert.AreEqual(pSenior.Station, "1");
            Assert.AreEqual(pSenior.AgeGroup, "Senior");
            Assert.AreEqual(pSenior.Name, "Günter");
            Assert.AreEqual(pSenior.Surname, "Mustermann");
            Assert.AreEqual(pSenior.State, 1);
            Assert.AreEqual(pSenior.Room, "15");
            Assert.AreEqual(pSenior.Status, true);
        }

        /* AddToListTest tests that the Patients gets sorted into the list corrects
         * sorted by status, newest person with the same status before the older ones
         * 
         */
        [TestMethod]
        public void AddToListTest()
        {
            //create test patients with different status
            Patient pOne = new Patient(2, "1234567891", "1", 10, "Rüdiger", "Mustermann", 1, "1", true);
            Patient pTwo = new Patient(3, "1234567892", "1", 18, "Fritz", "Mustermann", 2, "2", true);
            Patient pThree = new Patient(4, "1234567893", "1", 20, "Günter", "Mustermann", 1, "3", true);
            Patient pFour = new Patient(5, "1234567894", "1", 60, "Patrick", "Mustermann", 0, "4", true);

            //add them with addtolist to the patient list
            Request.AddToList(pOne);
            Request.AddToList(pTwo);
            Request.AddToList(pThree);
            Request.AddToList(pFour);

            LinkedList<Patient> nodeList = Patient.GetPatients();
            LinkedListNode<Patient>  node = nodeList.First;

            //prove the order
            Assert.AreEqual(pTwo.Pid, node.Value.Pid);
            node = node.Next;    
            Assert.AreEqual(pThree.Pid, node.Value.Pid);
            node = node.Next;
            Assert.AreEqual(pOne.Pid, node.Value.Pid);
            node = node.Next;
            Assert.AreEqual(pFour.Pid, node.Value.Pid);

            //change the status of the last patient to 2 (should be first now)
            Patient pFourChanged = new Patient(5, "1234567894", "1", 60, "Patrick", "Mustermann", 2, "4", true);
            Request.AddToList(pFourChanged);
            node = nodeList.First;
            //test the first node (should be pFourChanged patient with status 2
            Assert.AreEqual(pFourChanged.Pid, node.Value.Pid);
            Assert.AreEqual(pFourChanged.State, node.Value.State);

            //test the following patients if still in right order
            node = node.Next;
            Assert.AreEqual(pTwo.Pid, node.Value.Pid);
            node = node.Next;
            Assert.AreEqual(pThree.Pid, node.Value.Pid);
            node = node.Next;
            Assert.AreEqual(pOne.Pid, node.Value.Pid);
            
        }
    }
}
