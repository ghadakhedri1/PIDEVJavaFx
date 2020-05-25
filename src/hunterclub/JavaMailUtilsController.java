/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunterclub;

import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import tn.pidev.services.JournalService;
import tn.pidev.utils.Vars;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class JavaMailUtilsController {

    /**
     * Initializes the controller class.
     */
   
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send:");
        Properties properties = new Properties();
        String myAccountEmail = "ghada.khedri1@esprit.tn";
        String password = "193JFT0022";

        properties.put("com.hof.email.starttime", "20170519094544");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.connectiontimeout", "60000");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.timeout", "60000");
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient);
        Transport.send(message);
        System.out.println("Message send successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) throws SQLException {
        try {
            JournalService Act = new JournalService();
            Message message = new MimeMessage(session);
            message.setContent(message, "text/html");
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Wouho Score !!");
            JournalService ser = new JournalService();
         
                String s=ser.CalculScore1(Vars.getCurrentUser().getId());
              


            
            message.setText("bonjour "+ser.findUSerById_user(Vars.getCurrentUser().getId())+",\n"+"felicitation votre score maintenant est "
                   + s +".");
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMailUtilsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
}
