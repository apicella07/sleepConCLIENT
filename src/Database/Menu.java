/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import BITalino.BitalinoDemo;
import Client.*;
import static Client.ConnectionServer.*;
import static Client.ui.areYouSure;
import java.io.*;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author marin
 */
public class Menu {
    private Connection c;
    private static Database.DBManagerInterface dbman;
    private static PatientManagerInterface pmi;
    private static UserManagerInterface umi;
    private static BufferedReader br;
    private  PatientManager pm;
    private static ConnectionServer cs;
    private static BitalinoDemo bit;
    private static Patient patientUsing = new Patient();
    private static Report repUsing = new Report();
    private static int num,numUsing;
    private static boolean inUse;
    private static boolean logged;
    private static String ipString;
    private static InetAddress ip;

    public static void main(String[] args) throws IOException, ParseException, Exception {
        dbman = new DBManager();
        dbman.connect();
        //dbman.createTables();
        pmi = dbman.getPatientManager();
        umi=dbman.getUserManager();
        umi.connect();
      
        br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        
        inUse=false;
        logged=false;
        int max;
        System.out.println("\nWELCOME TO SLEEP CONTROL\n");
        System.out.println("\nPlease, introduce the IP you are connected with:");
        ipString=sc.next();
        ip=InetAddress.getByName(ipString);
        
        while(true){
            
               System.out.println("\nWhat do you want to do?\n"+"1. Register.\n"+"2. Login.\n");
                max=2;
            if(logged){
                System.out.println("3. View your report history.\n"+"4. Do your daily report.\n"+"5. Modify your personal information.\n"+"6. View your actual EEG values.\n"+"7. View your actual EEG values with LUX.\n"+"8. View your EEG history.\n"+"9. Record your EEG \n" + "10. Connection with the server.\n");
                System.out.println("11. Log out.\n");
                max=11;
            }
            System.out.println("0. Exit.\n");
            num=requestNumber(max);
            inUse=true;
            numUsing=num;
            while(inUse){
                switch(numUsing){
                    case 1:
                        if(logged) {
                            System.out.println("You are already logged");
                            break;
                        }
                        addPatientByRegister();
                       cs.sendPatient(patientUsing, ip);
                        break;
                    case 2:
                        if(logged) {
                            System.out.println("You are already logged");
                            break;
                        }
                        login();
                        //cs.sendPatient(patientUsing, ip);
                        break;
                    case 3:
                        reportHistory(patientUsing.getDni()); 
                        break;
                    case 4:
                        repUsing = addDailyReport(patientUsing.getDni()); 
                        cs.sendReport(repUsing, ip);
                        break;
                    case 5: 
                        modifyInformation(); 
                        
                        break;
                    case 6:
                        viewEEG(patientUsing.getDni()); 
                        break;
                    case 7:
                        viewEEGLUX(patientUsing.getDni()); 
                        break;
                    case 8:
                        viewEEGHistory(patientUsing.getDni());
                        break;
                    case 9:
                       boolean sure =areYouSure(br,"Are you sure the hospital is connected?");
                       if(sure){
                          recordEEG(patientUsing.getDni());
                         cs.sendFile(patientUsing, ip); 
                       }
                        break;
                        
                    case 10:
                        System.out.println("\nWhat do you want to do?\n" + "1.Send patient.\n" + "2.Send Report.\n" + "3.Send File with EEG values.\n");
                        max = 3;
                        Patient pat = pmi.searchSpecificPatientByDNI(patientUsing.getDni());
                        Report rep = pmi.getReportByDni(patientUsing.getDni());
                        System.out.println("0. Exit.\n");
                        num = requestNumber(max);
                        numUsing = num;
                        switch (num) {
                            case 1:
                                sendPatient(pat, ip);
                                break;
                            case 2:
                                sendReport(rep, ip);
                                break;
                            case 3:
                                sendFile(pat, ip);
                                break;
                            case 0:
                                System.exit(0);
                                break;
                        }
                        break;
                    
                    case 11:
                        dbman.disconnect();
                        logged=false;
                        patientUsing=new Patient();
                        umi.disconnect();
                        //System.exit(0);
                        break;
                     
                    case 0:
                        inUse=false;
                        logged=false;
                        System.exit(0);
                        break;
                }
                break;
            }
      
            pressEnter();
    }

    }
    
  
    public static void addPatientByRegister() throws IOException, ParseException{
        try{
        Patient newpat = null;
        System.out.println("Type your name: ");
        String name = br.readLine();
        System.out.println("Type your lastname:");
        String lastname = br.readLine();
        String telephone = ui.takeTelephone(br,"Type your telephone:");
        System.out.println("Type your address: ");
        String address = br.readLine();
        LocalDate data= ui.takeDate(br,"Type your Date of Birth (followed by yyyy-MM-dd)");
        java.util.Date dob = java.sql.Date.valueOf(data);
        String dni = ui.takeDNI(br,"Type your DNI (numeric only)");
        String gender = ui.takeGender(br, "Type your gender: ");
        patientUsing = new Patient(name, lastname, telephone, address,dob, dni, gender);
        String password = ui.takePasswordAndHashIt(br, "Introduce a password:");
        User user = new User(dni, password);
	umi.createUserRegister(user);
        
        System.out.println("\nYOUR INFORMATION IS: " + patientUsing+"\n");
       // pmi.addpatientbyRegister(newpat);
        
        }catch(NullPointerException e){
            e.printStackTrace(); 
        }
    }
    
    
    public static void addPatient() throws IOException, ParseException {
        try{
        Patient newpat = null;
        System.out.println("Type the name of the patient you'll add");
        String name = br.readLine();
        System.out.println("Type the lastname of the patient:");
        String lastname = br.readLine();
        String telephone = ui.takeTelephone(br,"Type the telephone of the patient");
        System.out.println("Type the address of the patient");
        String address = br.readLine();
        LocalDate data= ui.takeDate(br,"Type the Date of Birth of the patient followed by yyyy-MM-dd");
        java.util.Date dob = java.sql.Date.valueOf(data);
        String dni = ui.takeDNI(br,"Type the DNI of the patient (numeric only)");
        String gender = ui.takeGender(br, "Type the gender of the patient: ");
        newpat = new Patient(name, lastname, telephone, address,dob, dni, gender);
        System.out.println("The new patient is: " + newpat);
        pmi.addpatientbyRegister(newpat);
    
        
    }catch(NullPointerException e){
        e.printStackTrace(); 
    }
    }
    
    public static void addUser(){
       
        try{
            User newuser=null;
            System.out.println("Introduce your DNI, as username (without letters)");
            String dniuser = br.readLine();
            String password = ui.takePasswordAndHashIt(br, "Add the password you want:");
            newuser = new User(dniuser, password);
            System.out.println("The user added is:" +newuser);
            umi.createUserRegister(newuser);
            //Byte[] pass = (String ) br.readLine();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    
    public static void searchbyDNI() throws IOException{
        System.out.println("Type the dni of the patient you want to search" );
        String dniobtained = br.readLine();
        Patient newpat = pmi.searchSpecificPatientByDNI(dniobtained);
        System.out.println("The patient is:" +newpat.toString());
       
    }

    
    public static Report addDailyReport(String dni) throws IOException{
        
        LocalDate data=now();
        java.util.Date todaysdate = java.sql.Date.valueOf(data);
        System.out.println("Have you slept well during the night?");
        String quality = br.readLine();
        System.out.println("Do you feel exhausted like you didnt sleep through the night?");
        String exhausted = br.readLine();
        System.out.println("What is the average of hours you sleep daily?");
        String avgHours = br.readLine();
        System.out.println("Do you stir a lot during the night");
        String movement = br.readLine();
        System.out.println("How long until you fall asleep? Does it take too long or a few minutes?");
        String timeToFallAsleep = br.readLine();
        System.out.println("Have you rested?");
        String rest = br.readLine();
        System.out.println("Are you able to stay awake during the day?");
        String awake = br.readLine();
        System.out.println("How many times did you wake up during the night?");
        String timesAwake = br.readLine();
        System.out.println("Do you remember the dream?");
        String dream = br.readLine();
        System.out.println("Do you feel nervous or have any worries?");
        String worries = br.readLine();
        System.out.println("How do you feel today? Symptons?");
        String mood = br.readLine();
        System.out.println("Doubts for the doctor");
        String doubtsDoctor = br.readLine();
        
        Report newRep = new Report(dni,todaysdate, quality, exhausted, avgHours, movement, timeToFallAsleep, rest, awake, timesAwake, dream, worries, mood, doubtsDoctor);
        System.out.println("The report introduced is" +newRep);
        pmi.addDailyreport(newRep);
        System.out.println("Report added succesfully");
        return newRep;
    }
    
       public static void getReport() throws IOException{
        LocalDate data= ui.takeDate(br,"Type the date (yyyy-MM-dd) of the report you want to see: ");
        java.util.Date repsday = java.sql.Date.valueOf(data);
        
        Report newrepobtained = pmi.getDailyReport(repsday);
        System.out.println("The report is: " +newrepobtained);
    }
       
       
       
    public static void viewEEG(String dni) throws IOException {
       pmi.viewEEGString(dni); 

    }
    public static void viewEEGLUX(String dni) {
        pmi.viewEEGStringLUX(dni); 
    }
       
       public static void reportHistory(String dni){
          ArrayList<Report> reps = new ArrayList<Report>();
          Report newrepo;
          reps = pmi.reportHistory(dni);
          Iterator it = reps.iterator();
          while(it.hasNext()){
              newrepo = (Report) it.next();
              System.out.println(newrepo.toString());
              System.out.println("");
          }
       }
       public static void viewEEGHistory(String dni){
          ArrayList<Signals> eegs = new ArrayList<Signals>();
          Signals eeg;
          eegs = pmi.viewEEGHistory(dni);
          Iterator it = eegs.iterator();
          while(it.hasNext()){
              eeg = (Signals) it.next();
              System.out.println(eeg.toStringWithoutValues());
              System.out.println("");
          }
       }
       /*public static void viewEEGHistory(String dni){
        System.out.println("YOUR EEG HISTORY IS:\n");   
        pmi.viewEEGHistory(dni);
          
       }*/
       
       public static void modifyInformation() throws IOException{
           System.out.println(patientUsing.toString());
           System.out.println("\nThis is your personal information, what do you want to change?\n");
           System.out.println("1.Name.\n"+"2.Lastname.\n"+"3.Telephone.\n"+"4.Address.\n"+"5.Nothing, going back.");
           int number=requestNumber(5);
           switch(number){
               case 1:
                   System.out.println("Introduce your new name: ");
                   String nam=br.readLine();
                   patientUsing.setName(nam);
                   break;
               case 2:
                   System.out.println("Introduce your new lastname: ");
                   String las=br.readLine();
                   patientUsing.setLastname(las);
                   break;
               case 3:
                   System.out.println("Introduce your new telephone: ");
                   String tel=br.readLine();
                   patientUsing.setTelephone(tel);
                   break;
               case 4:
                   System.out.println("Introduce your new address: ");
                   String ad=br.readLine();
                   patientUsing.setAddress(ad);
                   break;   
               case 5:
                   break;
           } 
           cs.sendPatient(patientUsing, ip);
           
       }
       
       public static void recordEEG(String dni){
           bit.startEEGrecording();
           List<Integer> eeg =bit.getEEGvalues();
           List<Integer> eeglux = bit.getEEGLUXvalues();
           
            Signals signal=new Signals(now(),dni,eeg,eeglux);
            pmi.addEEG(signal);
            System.out.println("The recording was introduced in the database correctly");
          
          Patient.createFile(patientUsing, eeg, eeglux);
          System.out.println("File was created correectly!!");
      
           
       
       }
       
       public static int requestNumber(int max) {
		int num;
		do {
                    num = ui.takeInteger(br, "Introduce the number: ");
		} while (ui.CheckOption(num, max));

		return num;
	}
       public static void login(){ 
            boolean check = true;
        do{    
            String dni = ui.takeDNI(br, "Introduce your DNI:");
           
            String password = ui.takePasswordAndHashIt(br, "Introduce your password:");
            User user = new User(dni, password);
            User userCheck1= new User();
            userCheck1 = umi.checkUserGood(dni);
            //User userCheck = umi.checkPasswordGood(user);
            if (userCheck1 == null) {
                System.out.println("Wrong credentials, please select an option: ");
		System.out.println("1. Introduce them again. ");
		System.out.println("0. Go back to the menu. ");
                int option = requestNumber(2);
                switch (option) {
                case 1:
                        break;
                case 0:
                        check = false;
                }
            } else {
                System.out.println("Welcome patient!");
                patientUsing.setDni(dni);
                patientUsing = pmi.searchSpecificPatientByDNI(dni);
                logged = true;
                check = false;
            }
        } while(check);       
       }
       public static void pressEnter() {
		System.out.println("Press enter to go to the main menu and continue.");
		try {
			String nothing;
			nothing = br.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
       
       
}
