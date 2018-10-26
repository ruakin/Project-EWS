package de.ews.server.Util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mail {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates and sends a new Mail
     * 
     * @param to
     *            The recipient
     * @param subject
     *            Subject of the mail
     * @param body
     *            Message to be send
     */
    public Mail(String to, String subject, String body) {
        final String from = "noreply@example.com";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.host", "example.com"); // SMTP Host
        props.put("mail.smtp.port", "25"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        sendEmail(session, from, to, subject, body);
    }

    private static void sendEmail(Session session, String from, String to, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(from, "NoReply"));
            msg.setReplyTo(InternetAddress.parse(from, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            Transport.send(msg);

            LOGGER.info("Mail successfully send");
        } catch (Exception e) {
            LOGGER.error("Could not send mail to: " + to + "", e);
        }
    }

}