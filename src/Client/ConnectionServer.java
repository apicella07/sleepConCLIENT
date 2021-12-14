/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Database.DBManager;
import Database.PatientManagerInterface;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionServer {

    private Connection c;
    private static Database.DBManagerInterface dbman;
    private static PatientManagerInterface pmi;
    private static BufferedReader br;
    public static void main(String[] args) throws ClassNotFoundException, ParseException, UnknownHostException, IOException {
        dbman = new DBManager();
        dbman.connect();
        pmi = dbman.getPatientManager();
        br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        int max;
        int num, numUsing;
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("MENU CLIENT TO SELECT CONNECTION");
        System.out.println("First, write your IP: ");
        String ipString = sc.next();
        InetAddress ip = InetAddress.getByName(ipString);
        String dni = ui.takeDNI(br, "Write the patient DNI of the data you want to send:");
        System.out.println("\nWhat do you want to do?\n" + "1.Send patient.\n" + "2.Send Report.\n" + "3.Send File with EEG values.\n");
        max = 3;
        Patient pat = pmi.searchSpecificPatientByDNI(dni);
        Report rep = pmi.getReportByDni(dni);
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

    }

    /**
     * Sendind an object patient through a socket to a server.
     *
     * @param patient
     * @param ip
     */
    public static void sendPatient(Patient patient, InetAddress ip) {
        Socket socketSender = null;
        PrintWriter print = null;
        SimpleDateFormat formato;
        String total, name, lastName, telephone, address, date, dni, gender;

        try {
            socketSender = new Socket(ip, 9010);
            print = new PrintWriter(socketSender.getOutputStream(), true);
            print.println("0000");
            name = patient.getName();
            print.println(name);
            lastName = patient.getLastname();
            print.println(lastName);
            telephone = patient.getTelephone();
            print.println(telephone);
            address = patient.getAddress();
            print.println(address);
            //formato = new SimpleDateFormat("yyyy-MM-dd");
            //date = formato.format(patient.getDateOfBirth());
            dni = patient.getDni();
            print.println(dni);
            gender = patient.getGender();
            print.println(gender);
            print.println("finish");
            //total = "\n PATIENT'S INFORMATION \n Name: " + name + "\n Lastname: " + lastName + "\n Telephone: " + telephone + "\n Address: " + address + "\n Date of birth: " /*+ date */+ "\n DNI: " + dni + "\n Gender: " + gender + "\n" + "\n 0000 "+" \n finish";
            //print.println(total);
            releaseResources(print, socketSender);
        } catch (IOException io) {
            System.out.println("No possible to connect.");
            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, io);
        }
    }

    /**
     * Sending an object Report through a socket to a server.
     *
     * @param rep
     * @param ip
     */
    public static void sendReport(Report rep, InetAddress ip) {

        Socket socketSender = null;
        PrintWriter print = null;

        try {
            socketSender = new Socket(ip, 9010);
            print = new PrintWriter(socketSender.getOutputStream(), true);
            String dni, date, sleepqual, exhaus, average, movement, timeToFall, rest, stayAwake, timesAwake, dreams, worries, todaysMood, doubtsForDoctor;
          
            print.println("1111");
             dni = rep.getPatdni();
            print.println(dni);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            date = formato.format(rep.getTodaysDate());
            print.println(date);
            sleepqual = rep.getsleepQuality();
            print.println(sleepqual);
            exhaus = rep.getExhaustion();
            print.println(exhaus);
            average = rep.getAverageHours();
            print.println(average);
            movement = rep.getMovement();
            print.println(movement);
            timeToFall = rep.gettimeToFallAsleep();
            print.println(timeToFall);
            rest = rep.getRest();
            print.println(rest);
            stayAwake = rep.getStayAwake();
            print.println(stayAwake);
            timesAwake = rep.getTimesAwake();
            print.println(timesAwake);
            dreams = rep.getDreams();
            print.println(dreams);
            worries = rep.getWorries();
            print.println(worries);
            todaysMood = rep.getTodaysMood();
            print.println(todaysMood);
            doubtsForDoctor = rep.getdoubtsForDoctor();
            print.println(doubtsForDoctor);
           print.println("finish");
            releaseResources(print, socketSender);
        } catch (IOException io) {
            System.out.println("No possible to connect.");
            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, io);
        }
    }

    /**
     * Sending a File with the information about EEG of a patient, its name and
     * the time of the EEG through a socket to a server.
     *
     * @param file
     * @param ip
     */
    public static void sendFile(Patient pat, InetAddress ip) {
        File file = new File("./recordedSignal_" + pat.getDni() + ".txt");

        try {
            Socket socket = new Socket(ip, 9010);
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new FileReader(file));
            int character;
            while ((character = br.read()) != -1) {
                outputStream.write(character);
                outputStream.flush();

            }
            outputStream.flush();
            releaseResources(outputStream, br, socket);
        } catch (IOException ex) {
            System.out.println("No possible to connect.");
            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private static void releaseResources(PrintWriter p, Socket socket) {
        try {
            p.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int requestNumber(int max) {
        br = new BufferedReader(new InputStreamReader(System.in));
        int num;
        do {

            num = ui.takeInteger(br, "Introduce the number: ");

        } while (ui.CheckOption(num, max));

        return num;
    }

    private static void releaseResources(OutputStream outputStream,
            BufferedReader br, Socket socket) {
        try {
            try {
                br.close();

            } catch (IOException ex) {
                Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                outputStream.close();

            } catch (IOException ex) {
                Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ConnectionServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
