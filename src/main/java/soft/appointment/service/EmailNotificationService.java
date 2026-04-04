/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soft.appointment.service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import soft.appointment.domain.User;

/**
 *
 * @author user
 */
public class EmailNotificationService implements Observer {

    @Override
    public void notify(User user, String messageContent) {
        // 1. Load credentials from .env
        Dotenv dotenv = Dotenv.load();
        String senderEmail = dotenv.get("EMAIL_USERNAME");
        String senderPassword = dotenv.get("EMAIL_PASSWORD");

        // 2. Setup SMTP Server (Hardcoded for Gmail as requested)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 3. Create Session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // 4. Send the Email
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            // We assume the username IS the email (e.g. ali@gmail.com)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getUsername())); 
            message.setSubject("Appointment System Notification");
            message.setText("Dear " + user.getUsername() + ",\n\n" + messageContent);

            Transport.send(message);
            System.out.println("DEBUG: Email sent to " + user.getUsername());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
