package com.addinghelp.mailer;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mailer {

	/**
	   Outgoing Mail (SMTP) Server
	   requires TLS or SSL: smtp.gmail.com (use authentication)
	   Use Authentication: Yes
	   Port for SSL: 465
	 */
    private final String username = "noreply.addinghelp@gmail.com";
    private final String password = "africanChild101";
    private final String reciever;
    private final Properties props = System.getProperties();
    public Mailer(String reciever){
        this.reciever = reciever;

    }

    public Mailer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void connectAndSend(String code){
       
    props.put("mail.smtp.starttls.enable", true); // added this line
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.user", "noreply.addinghelp@gmail.com");
    props.put("mail.smtp.password", "africanChild101");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");



    Session session = Session.getInstance(this.props,null);
    MimeMessage message = new MimeMessage(session);

    System.out.println("Port: "+session.getProperty("mail.smtp.port"));

    // Create the email addresses involved
    try {
        InternetAddress from = new InternetAddress(this.username);
        message.setSubject("Yes we can");
        message.setFrom(from);
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(this.reciever));

        // Create a multi-part to combine the parts
        Multipart multipart = new MimeMultipart("alternative");

        // Create your text message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("some text to send");

        // Add the text part to the multipart
        multipart.addBodyPart(messageBodyPart);

        // Create the html part
        messageBodyPart = new MimeBodyPart();
        String htmlMessage = "<h3>Our html text</h3><br>"+code;
        messageBodyPart.setContent(htmlMessage, "text/html");


        // Add html part to multi part
        multipart.addBodyPart(messageBodyPart);

        // Associate multi-part with message
        message.setContent(multipart);

        // Send message
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", this.username, this.password);
        System.out.println("Transport: "+transport.toString());
        transport.sendMessage(message, message.getAllRecipients());


    } catch (AddressException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (MessagingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }
}
