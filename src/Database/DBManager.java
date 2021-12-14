/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;
import Client.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager implements DBManagerInterface {


    
    private Connection c;
    private PatientManager patient;
    private UserManager user;
    //private UserManager user;

    public DBManager() {
        super();
    }

    public void connect() {
        try {
            String url = "jdbc:sqlite:lib/db/SleepControlDB2.db";
            c = DriverManager.getConnection(url);

            c.createStatement().execute("PRAGMA foreign_keys=ON");

            patient = new PatientManager(c);
            user = new UserManager(c);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public void disconnect() {
        try {
            // Close database connection
            c.close();
            // System.out.println("Database connection closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Creating tables of our database named SleepControlDB2.db
     * There are 4 different tables: Patients, Reports, EEGs and Users.
     */
    @Override
    public void createTables() {
        try {
            System.out.println("Database connection opened.");
            connect();

            Statement stmt1 = c.createStatement();
            String sql1 = "CREATE TABLE Patients "
                    + " (patient_id	INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "LASTNAME TEXT,"
                    + "TELEPHONE TEXT,"
                    + "ADDRESS TEXT,"
                    + "DOB DATE,"
                    + "DNI TEXT,"
                    + "GENDER TEXT)";

            stmt1.executeUpdate(sql1);
            stmt1.close();

            Statement stmt2 = c.createStatement();
            String sql2 = "CREATE TABLE Reports "
                    + "(report_id	INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "PATIENT_DNI TEXT,"
                    + "REPORT_DATE DATE,"
                    + "QUALITY TEXT," 
                    + "EXHAUSTION TEXT,"
                    + "HOURS TEXT,"
                    + "MOVEMENT TEXT," 
                    + "TIME TEXT,"
                    + "REST TEXT," 
                    + "AWAKE TEXT,"
                    + "TIMES TEXT" 
                    + "DREAMS TEXT,"
                    + "WORRIES TEXT,"
                    + "MOOD TEXT," 
                    + "DOUBTS TEXT)";

            stmt2.executeUpdate(sql2);
            stmt2.close();

            Statement stmt3 = c.createStatement();
            String sql3 = "CREATE TABLE EEGs "
                    + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "PATIENT_DNI TEXT,"
                    + "EEG_DATE DATE,"
                    + "EEG TEXT,"
                    + "EEG_LUX TEXT)";

            stmt3.executeUpdate(sql3);
            stmt3.close();
            Statement stmt4 = c.createStatement();
            String sql4 = "CREATE TABLE Users "
                    + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "PATIENT_DNI TEXT,"
                    + "Password BLOB)";

            stmt4.executeUpdate(sql4);
            stmt4.close();

            System.out.println("Tables created.");
            // Create table: end
            

            Statement stmtSeq = c.createStatement();
            String sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES('Patients', 1)";
            stmtSeq.executeUpdate(sqlSeq);
            sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('Reports', 1)";
            stmtSeq.executeUpdate(sqlSeq);
            sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('EEGs', 1)";
            stmtSeq.executeUpdate(sqlSeq);
            stmtSeq.close();

        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PatientManager getPatientManager() {
        return patient;
    }
    @Override
    public UserManager getUserManager() {
        return user;
    }

    /**
     * Delating the 4 tables of our database.
     */
    @Override
    public void deleteTables() {
        try {

            // Drop tables: begin
            Statement stmt2 = c.createStatement();
            String sql2 = "DROP TABLE Patients";
            stmt2.executeUpdate(sql2);
            stmt2.close();
            Statement stmt3 = c.createStatement();
            String sql3 = "DROP TABLE Reports";
            stmt3.executeUpdate(sql3);
            stmt3.close();
            Statement stmt4 = c.createStatement();
            String sql4 = "DROP TABLE EEGs";
            stmt4.executeUpdate(sql4);
            stmt4.close();
            System.out.println("Tables removed.");
            // Drop tables: end

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}