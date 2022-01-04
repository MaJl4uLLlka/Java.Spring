package com.example.mainspingproject.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class DefaultEmailService {
    private final JavaMailSender emailsender;
    final String from ="meetupservicenoreply@gmail.com";
    final  String password ="MaJl4uLLlka";
    final Properties props;

    @Autowired
    public DefaultEmailService(JavaMailSender emailsender)  {
        this.emailsender = emailsender;
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }


    public void sendRejectNotify(String email) throws MessagingException {
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });

        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress(email);

        MimeMessage message = new MimeMessage(session);
        message.setSender(addressFrom);
        message.setSubject("Rejected Request");
        message.setContent("Hello, sorry, but your request is rejected :[ Thank you, that you use our service!", "text/plain");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        transport.connect();
        Transport.send(message);
        transport.close();
    }

    public void sendApproveNotify(String email) throws MessagingException {
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });

        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress(email);

        MimeMessage message = new MimeMessage(session);
        message.setSender(addressFrom);
        message.setSubject("Approved Request");
        message.setContent("Hello, your request is approved! Thank you, that you use our service!", "text/plain");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        transport.connect();
        Transport.send(message);
        transport.close();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Approved Request");
//        message.setText("Hello, your request is approved! Thank you, that you use our service!");

        emailsender.send(message);

    }

}
