/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Client.*;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;



public interface PatientManagerInterface {
    public void addpatientbyRegister(Patient pat);
    public Patient searchSpecificPatientByDNI(String dni);
    public  ArrayList<Patient> showPatients();
    public void addDailyreport(Report rep);
    public ArrayList<Report> reportHistory(String dni);
    public  Report getDailyReport(java.util.Date  dateReport);
    public ArrayList<Signals> viewEEGHistory(String dni);
    public ObservableList<Report> showReports();
    public void addEEG(Signals eeg);
    public void viewEEGString(String dni);
    public void viewEEGStringLUX(String dni);
    public  Report getReportByDni(String dni); 
}
