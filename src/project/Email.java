/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static project.DBLoginData.password;
import static project.DBLoginData.uname;
import static project.DBLoginData.url;

/**
 *  Confirmation, Change and Reminder Email
 * @author Htet Htet 
 */
public class Email {

    private String appointment;
    private String receiver;
    private String confirm_subject = "Confirmation";
    private String confirm_body = "Your doctor Appointment has been confirmed!\nPlease remember to bring your insurance card with you to the appointment.\nYour Appointment is: ";
    private String cancel_subject = "Canceling";
    private String cancel_body = "Your doctor Appointment has been cancelled!!\nAppointment that has been cancelled is: ";
    private String reminder_subject = "Reminder";
    private String reminder_body = "You have Doctor Appointment in: ";
    private String edit_subject = "Shifted";
    private String edit_body = "Your Appointment has been shifted.\nYour new appointment: ";
    private String ID;
    private String Date;
    private String Time;
    String endText = "If you are unable to attend, remember to reschedule or cancel the appointment as early as possible";

    /**
     *
     * @param date date of the appointment
     * @param time time of the appointment
     * @param mail email address of the patient
     * @param id ID of the patient
     */
    
    public Email(String date, String time, String mail, String id) {
        
        this.Date = date;
        this.Time = time;
        this.ID = id;
        this.receiver = mail;
        appointment = date + " " + time + " o'clock";

    }

    //use to schedule a Task 
    public class reminder_time extends TimerTask {

        @Override
        public void run() {
            try {
                senden(2);          //put 2 as Type for methode "senden()" for reminder function       
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     *
     * @param query sql query that is to be executed
     * @param column of the database table
     * @return data that is retrieved from database
     * @throws ClassNotFoundException
     */
    //Method use to retrieve data from database
    public String database(String query, String column) throws ClassNotFoundException {
        String data = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            conn = (Connection) DriverManager.getConnection(url, uname, password);
            stmt = conn.prepareStatement(query);
            stmt.setString(1, ID);   
            stmt.setString(2, Date);
            stmt.setString(3, Time);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            data = rs.getString(column);

        } catch (SQLException ex) {
        }
        return data;
    }

    /**
     *
     * @param remindertype 1 for 1week, 2 for 3days, 3 for 1hour and 4 for
     * 10mins
     * @param remindertime time when reminder-mail needs to be sent
     */
    // Method used after adding or editing appointment to schedule a reminder time
    public void reminder_funktion(int remindertype, Date remindertime) throws ClassNotFoundException {
        
        String doctor_name = database("select doctor_name from app where ID = ? AND Date = ? AND Time = ?", "doctor_name");
        String doctor_address = database("select doctor_address from app where ID = ? AND Date = ? AND Time = ?", "doctor_address");
        
        switch (remindertype) {
            case 1 ->
                this.reminder_body = this.reminder_body + "1 week.\n" + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address;          //Veränderung 26.01.2022
            case 2 ->
                this.reminder_body = this.reminder_body + "3 days.\n" + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address;
            case 3 ->
                this.reminder_body = this.reminder_body + "1 hour.\n" + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address;
            case 4 ->
                this.reminder_body = this.reminder_body + "10 minutes.\n" + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address;
        }
        //schedule a Timer according to reminder time
        new Timer().schedule(new reminder_time(), remindertime);
    }

    /**
     *
     *
     * @param type 1 for edit, 2 for reminder, 3 for cancel and 4 for
     * confirmation
     * @throws java.lang.ClassNotFoundException
     */
    //Depends on the type of email, the body and subject change
    
    public void senden(int type) throws ClassNotFoundException {
        
        String doctor_name = database("select doctor_name from app where ID = ? AND Date = ? AND Time = ?", "doctor_name");
        String doctor_address = database("select doctor_address from app where ID = ? AND Date = ? AND Time = ?", "doctor_address");
        
        String subject = null;
        String body = null;
        
        switch (type) {
            case 1 -> {
                subject = this.edit_subject;
                body = this.edit_body + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address + "\n" + endText;

            }

            case 2 -> {
                subject = this.reminder_subject;
                body = this.reminder_body;
            }

            case 3 -> {
                subject = this.cancel_subject;
                body = this.cancel_body + appointment;
            }

            case 4 -> {
                subject = this.confirm_subject;
                body = this.confirm_body + " " + appointment + "\nDoctor: " + doctor_name + "\nAddress: " + doctor_address + "\n" + endText;
            }

        }

        //Email and password of sender email address
        String myAcc = "javaproject.ehealth@gmail.com";
        String myPwd = "#JavaProject4";

        //Get system properties
        Properties p = System.getProperties();

        //Setup the mail server
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.user", myAcc);
        p.put("mail.smtp.password", myPwd);
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.auth", "true");

        //Creating a session with username and password
        Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAcc, myPwd);
            }
        });

        //Compose the message
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAcc));  //Sender
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            //Set subject and Body of the mail
            message.setSubject(subject);
            message.setText(body);

            //Sent the message            
            Transport.send(message);
            System.out.println("Email sucessfully sent!");

        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
