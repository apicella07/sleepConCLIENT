/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import Client.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class PatientManager implements PatientManagerInterface  {

   
    private Connection c;
	
	public PatientManager(Connection connection) {
		this.c=connection;
	}
	
    /**
     * Insert into table Reports a new report of the patient.
     * @param rep object of type Report with all its atributes.
     */
    @Override
        public void addDailyreport(Report rep) {
            try {
                String sql = "INSERT INTO Reports (patient_dni, report_date, quality,exhaustion,hours,movement,time,rest,awake,times,dreams,worries, mood,doubts)"
                                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                PreparedStatement prep = c.prepareStatement(sql);
                prep.setString(1, rep.getPatdni());
                prep.setDate(2, (java.sql.Date) rep.getTodaysDate());
                prep.setString(3, rep.getsleepQuality());
                prep.setString(4, rep.getExhaustion());
                prep.setString(5, rep.getAverageHours());
                prep.setString(6, rep.getMovement());
                prep.setString(7, rep.gettimeToFallAsleep());
                prep.setString(8, rep.getRest());
                prep.setString(9, rep.getStayAwake());
                prep.setString(10, rep.getTimesAwake());
                prep.setString(11, rep.getDreams());
                prep.setString(12, rep.getWorries());
                prep.setString(13, rep.getTodaysMood());
                prep.setString(14, rep.getdoubtsForDoctor());
                prep.executeUpdate();
                prep.close();
                }
            catch(Exception e) {
                    e.printStackTrace();
                    }
    }
            
    /**
     *
     * @param dni
     * @return the ArrayList of Reports that the patient  with this DNI has.
     */
    @Override
    public ArrayList<Report> reportHistory(String dni){
        ArrayList<Report> repList = new ArrayList<Report>();
        try {
                String sql = "SELECT * FROM Reports WHERE patient_dni LIKE ?";
                PreparedStatement prep = c.prepareStatement(sql);
                prep.setString(1, "%"+dni+"%");
                ResultSet rs = prep.executeQuery();
                
                while (rs.next()) {
                    String dni1=rs.getString("patient_dni");
                    java.util.Date repdate=rs.getDate("report_date");
                    String quality=rs.getString("quality");
                    String exhaust=rs.getString("exhaustion");
                    String averageHours=rs.getString("hours");
                    String movem=rs.getString("movement");
                    String timeToFall=rs.getString("time");
                    String res=rs.getString("rest");
                    String awake=rs.getString("awake");
                    String timAwake=rs.getString("times");
                    String dreams=rs.getString("dreams");
                    String worr=rs.getString("worries");
                    String mood=rs.getString("mood");
                    String doubts=rs.getString("doubts");
                    Report repnew = new Report (dni1,repdate, quality, exhaust, averageHours, movem, timeToFall, res, awake, timAwake,dreams, worr, mood, doubts);
                    repList.add(repnew);
                }

        } catch (Exception e) {
                e.printStackTrace();
        }
        return repList;
    }
    
     //This method is exclusively for the graphical interface part, it is just used there 
    public  ObservableList<Report> showReports() {
        ObservableList<Report> repList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Reports";
            PreparedStatement prep = c.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                String dnipat = rs.getString("patient_dni");
                java.util.Date repdate=rs.getDate("report_date");
                 String quality=rs.getString("quality");
                    String exhaust=rs.getString("exhaustion");
                    String averageHours=rs.getString("hours");
                    String movem=rs.getString("movement");
                    String timeToFall=rs.getString("time");
                    String res=rs.getString("rest");
                    String awake=rs.getString("awake");
                    String timAwake=rs.getString("times");
                    String dreams=rs.getString("dreams");
                    String worr=rs.getString("worries");
                    String mood=rs.getString("mood");
                    String doubts=rs.getString("doubts");
                    Report repnew = new Report (dnipat,repdate, quality, exhaust, averageHours, movem, timeToFall, res, awake, timAwake,dreams, worr, mood, doubts);
                    repList.add(repnew);
            }

        } catch (Exception e) {
                e.printStackTrace();
        }
        return repList;
    }

    /**
     * Getting an specific report of a patient knowing the date of the report.
     * @param dateReport date of the report you are looking for.
     * @return
     */
    @Override
    public  Report getDailyReport(java.util.Date  dateReport){
        Report newreport = new Report();
        String sql = "SELECT * FROM Reports WHERE Report_date LIKE ?";
        try {
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%"+dateReport+"%");
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                String dni=rs.getString("patient_dni");
                java.util.Date repdate=rs.getDate("report_date");
                String quality=rs.getString("quality");
                String exhaust=rs.getString("exhaustion");
                String averageHours=rs.getString("hours");
                String movem=rs.getString("movement");
                String timeToFall=rs.getString("time");
                String res=rs.getString("rest");
                String awake=rs.getString("awake");
                String timAwake=rs.getString("times");
                String dreams=rs.getString("dreams");
                String worr=rs.getString("worries");
                String mood=rs.getString("mood");
                String doubts=rs.getString("doubts");
                newreport = new Report(dni,repdate, quality, exhaust, averageHours, movem, timeToFall, res, awake, timAwake, dreams, worr, mood, doubts);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newreport;
    }
    public  Report getReportByDni(String dni){
                Report newreport = new Report();
                String sql = "SELECT * FROM Reports WHERE patient_dni LIKE ?";
        try {
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%"+dni+"%");
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                String dni1=rs.getString("patient_dni");
                java.util.Date repdate=rs.getDate("report_date");
                String quality=rs.getString("quality");
                String exhaust=rs.getString("exhaustion");
                String averageHours=rs.getString("hours");
                String movem=rs.getString("movement");
                String timeToFall=rs.getString("time");
                String res=rs.getString("rest");
                String awake=rs.getString("awake");
                String timAwake=rs.getString("times");
                String dreams=rs.getString("dreams");
                String worr=rs.getString("worries");
                String mood=rs.getString("mood");
                String doubts=rs.getString("doubts");
                newreport = new Report(dni1,repdate, quality, exhaust, averageHours, movem, timeToFall, res, awake, timAwake, dreams, worr, mood, doubts);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
                return newreport;
            }
            
    /**
     * Shows all the patients of the table Patients of the database.
     * @return an ArrayList of objects Patient.
     */
    @Override
    public  ArrayList<Patient> showPatients() {
        ArrayList<Patient> patList = new ArrayList<Patient>();
        try {
            String sql = "SELECT * FROM Patients";
            PreparedStatement prep = c.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int patId = rs.getInt("patient_id");
                String patName = rs.getString("name");
                String patLastName = rs.getString("lastname");
                String patTele = rs.getString("telephone");
                String patAddress = rs.getString("Address");
                java.util.Date patdob=rs.getDate("dob");
                String patdni = rs.getString("dni");
                String patgender = rs.getString("gender");
                Patient newPatient = new Patient(patId, patName, patLastName, patTele, patAddress, patdob,patdni, patgender);
                patList.add(newPatient);
            }

        } catch (Exception e) {
                e.printStackTrace();
        }
        return patList;
    }

    /**
     * Adding a Patient's object to the table Patients of the database.
     * @param pat object of Patient with all its atributes.
     */
    @Override
    public void addpatientbyRegister(Patient pat) {
        try {

            String sql = "INSERT INTO Patients (name, lastname, telephone, address,DOB,dni,gender)"
                            + " VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, pat.getName());
            prep.setString(2, pat.getLastname());
            prep.setString(3, pat.getTelephone());
            prep.setString(4, pat.getAddress());
            prep.setDate(5, (java.sql.Date) pat.getDateOfBirth());
            prep.setString(6, pat.getDni());
            prep.setString(7, pat.getGender());
            prep.executeUpdate();
            prep.close();
            }
    catch(Exception e) {
            e.printStackTrace();
            }
    }
  
    /**
     * Returns an object Patient with all its atributes knowing its dni.
     * @param dni of the patient you are looking for.
     * @return the patient with this dni
     */
    @Override
     public  Patient searchSpecificPatientByDNI(String dni){
         Patient patientfound=new Patient();
            try {
                    String sql = "SELECT * FROM Patients WHERE dni LIKE ?";
                    PreparedStatement prep = c.prepareStatement(sql);
                    prep.setString(1, "%"+dni+"%");
                    ResultSet rs = prep.executeQuery();

                    while(rs.next()) {
                            int id = rs.getInt("patient_id");
                            String name = rs.getString("name");
                            String lastname = rs.getString("lastname");
                            String tele = rs.getString("telephone");
                            String address = rs.getString("address");
                            java.util.Date dob=rs.getDate("dob");
                            String gender = rs.getString("gender");
                            patientfound = new Patient(id,name,lastname,tele,address,dob,dni,gender);
                    }
            }catch(Exception e) {
                    e.printStackTrace();
            }
            return patientfound;
     }

    
     public void addBitalinoFrame(Patient pat) {
		try {
     
			String sql = "INSERT INTO Patients (name, lastname, telephone, address,DOB,dni,gender)"
					+ " VALUES (?,?,?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, pat.getName());
			prep.setString(2, pat.getLastname());
			prep.setString(3, pat.getTelephone());
                        prep.setString(4, pat.getAddress());
                        prep.setDate(5, (java.sql.Date) pat.getDateOfBirth());
                        prep.setString(6, pat.getDni());
                        prep.setString(7, pat.getGender());
			prep.executeUpdate();
			prep.close();
			}
		catch(Exception e) {
			e.printStackTrace();
			}
	}      
     
    /**
     * Adding an EEG to table EEGs in the database thanks to the ArrayList obtained by bitalino.
     * @param eeg object of Signals with the lists.
     */
    @Override
     public void addEEG(Signals eeg) {
         try{
             String sql = "INSERT INTO EEGs (patient_dni, eeg_date, eeg, eeg_lux) VALUES (?,?,?,?)";
             PreparedStatement prep = c.prepareStatement(sql);
             prep.setString(1, eeg.getDni());
             prep.setDate(2,java.sql.Date.valueOf(eeg.getEegDate()));
             String values=(eeg.getEegValues()).toString();
             String luxvalues=(eeg.getEegLUX()).toString();
             prep.setString(3, values);
             prep.setString(4, luxvalues);
             prep.executeUpdate();
             prep.close();
         }catch(SQLException ex){
             ex.printStackTrace();
         }
         
     }
    
    /**
     * Method that prints the values of the EEG of an specific patient.
     * @param dni of the patient you want to look for.
     */
    public void viewEEGString(String dni) {
        
         try {
            String sql1= "SELECT eeg FROM EEGs WHERE patient_dni LIKE ?";
            PreparedStatement prep1 = c.prepareStatement(sql1);
            prep1.setString(1, "%"+dni+"%");
            ResultSet rs2 = prep1.executeQuery();
            while (rs2.next()) {
                String eegString=rs2.getString("EEG");
                System.out.println("The eeg values are: "+eegString); //NI SIQUIERA SE HACE ESTO CUANDO LO DESCOMENTO!!!!!!
            }  	
        }catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     *Method that prints the values of the EEG with LUX of an specific patient.
     * @param dni of the patient you want to look for.
     */
    @Override
      public void viewEEGStringLUX(String dni) {
        
         try {
            String sql1= "SELECT eeg_lux FROM EEGs WHERE patient_dni LIKE ?";
            PreparedStatement prep1 = c.prepareStatement(sql1);
            prep1.setString(1, "%"+dni+"%");
            ResultSet rs2 = prep1.executeQuery();
            while (rs2.next()) {
                String eegString=rs2.getString("EEG_LUX");
                System.out.println("The eeg values with LUX are: "+eegString);
            }  	
        }catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
      
    /**
     * Give an ArrayList of the Signals of the patient you are looking for.
     * @param dni of the patient you are looking for.
     * @return an ArrayList of Signals with the date of the different EEG values and dni of the patient.
     */
    @Override
    public ArrayList<Signals> viewEEGHistory(String dni) {
         ArrayList<Signals> eegs = new ArrayList<Signals>();
         ArrayList<Integer> values=new ArrayList<>();
         String[] valuesString;
         Connection c1 = null;
            try {
                        String sql1= "SELECT * FROM EEGs WHERE patient_dni LIKE ?";
                        PreparedStatement prep1 = c.prepareStatement(sql1);
			prep1.setString(1, "%"+dni+"%");
			ResultSet rs2 = prep1.executeQuery();
                        while (rs2.next()) {
                            String dni1=rs2.getString("patient_dni");
                            java.util.Date date = rs2.getDate("EEG_date");
                            String EEG=rs2.getString("EEG");
                            String eeg_lux=rs2.getString("EEG_LUX");
                            //LocalDate date1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            //System.out.println(date1);
                            Signals eeg= new Signals(date,dni);
                            eegs.add(eeg);
                        }
		}catch(Exception e) {
			e.printStackTrace();
		}
            return eegs;
    }
	  
   
	

    
    
    private static Database.DBManagerInterface dbman;
    private static PatientManagerInterface pmi;
    private static UserManagerInterface umi;
    private static BufferedReader br;
    public static void main(String[] args) throws IOException, ParseException, Exception {
        dbman = new DBManager();
        dbman.connect();
        pmi = dbman.getPatientManager();
        br = new BufferedReader(new InputStreamReader(System.in));
        String dni="10";
        
        viewEEGHistory1(dni);
    }
    public static void viewEEGHistory1(String dni) throws IOException {
        pmi.viewEEGHistory(dni); 
}
    public static ArrayList<Integer> conversion(String s) {
        ArrayList<Integer> values=new ArrayList<Integer>();
        String[] valuesString=s.split(", ",-1);
        System.out.println(valuesString);
        for (int i=0; i<valuesString.length;i++){
            if (i!=0 && i!=(valuesString.length-1)){
                values.add(Integer.parseInt(valuesString[i]));
                System.out.println(valuesString[i]);
            }
        } return values;
    }
}
        /*public static void addPatientByRegister() throws IOException, ParseException{
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
        newpat = new Patient(name, lastname, telephone, address,dob, dni, gender);
        String password = ui.takePasswordAndHashIt(br, "Introduce a password:");
         System.out.println(password);
        //User user = new User(dni, password);
	//umi.createUserRegister(user);
        
        System.out.println("\nYOUR INFORMATION IS: " + newpat+"\n");
        pmi.addpatientbyRegister(newpat);
    
        
        }catch(NullPointerException e){
            e.printStackTrace(); 
        }
 public void viewEEGHistory2(String dni) {
         ArrayList<Signals> eegs = new ArrayList<Signals>();
         Signals neweeg=new Signals();
            try {
                String sql1= "SELECT * FROM EEGs WHERE patient_dni =?";
                PreparedStatement prep1 = c.prepareStatement(sql1);
                prep1.setString(1, "%"+dni+"%");
                ResultSet rs2 = prep1.executeQuery();
                while (rs2.next()) {
                    String dni1=rs2.getString("patient_dni");
                    java.util.Date date = rs2.getDate("EEG_date");
                    //LocalDate date1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Signals eeg= new Signals(date,dni1);
                    System.out.println(eeg.toStringWithoutValues());
                    eegs.add(eeg);
                    
                }
                
		}catch(Exception e) {
			e.printStackTrace();
		}
    }*/

    /* public ArrayList<Integer> viewEEG(String dni) {
        ArrayList<Integer> values=new ArrayList<>();
        String eegString="";
         try {
            String sql1= "SELECT EEG FROM EEGs WHERE PATIENT_DNI = ?";
            PreparedStatement prep1 = c.prepareStatement(sql1);
            prep1.setString(1, "%"+dni+"%");
            ResultSet rs2 = prep1.executeQuery();
            //while (rs2.next()) {
                eegString=rs2.getString("EEG");
                String[] valuesString=eegString.split(", ",-1);
                System.out.println(valuesString);
                for (int i=0; i<valuesString.length;i++){
                    if (i!=0 && i!=(valuesString.length-1)){
                        values.add(Integer.parseInt(valuesString[i]));
                        System.out.println(valuesString[i]);
                    }
                } System.out.println(values);
                	
            //}
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PatientManager.class.getName()).log(Level.SEVERE, null, ex);
        } return values;
    }
     
     public Signals viewEEGLUX(String dni) {
         Signals eeg=new Signals();
         ArrayList<Integer> values=new ArrayList<>();
         String[] valuesString;
         Connection c1 = null; 
            try {
                        String sql1= "SELECT EEG_LUX FROM EEGs WHERE patient_dni =?";
                        PreparedStatement prep1 = c.prepareStatement(sql1);
			prep1.setString(1, "%"+dni+"%"); 
                        
			ResultSet rs2 = prep1.executeQuery();
                        while (rs2.next()) { 
                            java.util.Date date = rs2.getDate("eeg_date");
                            String EEG=rs2.getString("EEG_LUX"); 
                            System.out.println(EEG);
                            valuesString=EEG.split(", ",-1); 
                             for (int i=0; i<valuesString.length;i++){
                                if (i!=0 & i!=(valuesString.length-1)){
                                    values.add(Integer.parseInt(valuesString[i]));
                                }
                            }
                            LocalDate date1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            eeg=new Signals(date1,dni,null,values); 
                      }	
		}catch(Exception e) {
			e.printStackTrace();
		}
        return eeg;
    }
*/
    
	
