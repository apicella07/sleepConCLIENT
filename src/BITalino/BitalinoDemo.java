package BITalino;


import Client.Patient;
import static Client.Patient.createFile;
import Client.Signals;
import Client.ui;
import Database.DBManager;
import Database.PatientManager;
import Database.PatientManagerInterface;
import Database.UserManagerInterface;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.sql.Connection;
import static java.time.LocalDate.now;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.RemoteDevice;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitalinoDemo {

    public static Frame[] frame;
    public static List<Integer> values1 = new ArrayList();
    public static List<Integer> values2 = new ArrayList();
    private static String[] res;
    
    private Connection c;
    private static Database.DBManagerInterface dbman;
    private static PatientManagerInterface pmi;
    private  PatientManager pm;
    private static BufferedReader br;
    
    
    public static void setEEGvalues(List<Integer> val1){
        BitalinoDemo.values1 = val1;
    }
    
     public static void setEEGLUXvalues(List<Integer> val2){
        BitalinoDemo.values2 = val2;
    }

    public static List<Integer> getEEGvalues() {
        return values1;
    }

    public static List<Integer> getEEGLUXvalues() {
        return values2;
    }
    
     
     
     
    public static void startEEGrecording() {
        dbman = new DBManager();
        dbman.connect();
        pmi = dbman.getPatientManager();
        
        values1 = new ArrayList<Integer>();
        values2 = new ArrayList<Integer>();
        
        BITalino bitalino = null;
        try {
            bitalino = new BITalino();
            // Code to find Devices
            //Only works on some OS
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            String macAddress = "20:17:11:20:52:36";
            
            int SamplingRate = 100; //partimos de 100 
            bitalino.open(macAddress, SamplingRate);

            // Start acquisition on analog channels A2 and A6
            // We use channels A4 and A6, eeg and light
            int[] channelsToAcquire = {3,5}; 
            bitalino.start(channelsToAcquire);

            //Read in total 10000000 times
            for (int j = 0; j < 5; j++) {

                //Each time read a block of 10 samples 
                int block_size=10;
                frame = bitalino.read(block_size);

                System.out.println("size block: " + frame.length);

                //Print the samples
                for (int i = 0; i < frame.length; i++) {
                    System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                            + frame[i].analog[0] + " " 
                            + frame[i].analog[1] + " "
                    //  + frame[i].analog[2] + " "
                    //  + frame[i].analog[3] + " "
                    //  + frame[i].analog[4] + " "
                    //  + frame[i].analog[5]
                    );
                values1.add(frame[i].analog[0]);
                values2.add(frame[i].analog[1]);
                }               
            }
            
            setEEGvalues(values1);
            setEEGLUXvalues(values2);
            
            //ArrayList<Integer> val1=new ArrayList(values1);
            //ArrayList<Integer> val2=new ArrayList(values2);
            //createFile(pat,val1, val2);
            //String values=(val1).toString();
            //String luxvalues=(val2).toString();
            //Signals signal=new Signals(now(),dni,val1,val2);
            //System.out.println(signal.toString());
            //pmi.addEEG(signal); //to add the eeg into the database 

            // values.add(frame);
             //Patient.createFile(frame);
            //stop acquisition
            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(BitalinoDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
