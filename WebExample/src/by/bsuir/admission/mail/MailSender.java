package by.bsuir.admission.mail;

import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.xml.NoPropertyException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * This class sends e-mails
 * @author AndreAY
 */
public class MailSender {

    /**
     * This is an e-mail address of user
     */
    private String mailTo;
    /**
     * This is an e-mail address of admission system
     */
    private String mailFrom;
    /**
     * This is a mail subject
     */
    private String mailSubject;
    /**
     * This is a mail content 
     */
    private String mailContent;
    /**
     * This is a mail session
     */
    private Session mailSession;
    /**
     * This is object with mail settings
     */
    private Settings settings;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(MailSender.class);
    public static final String RESPONSE_CONTEXT = "responce.context";
    public static final String MAIL_SENDED = "logger.message.mail.sended";
    public static final String TRUE = "true";

    public MailSender() throws NoPropertyException {
        settings = new Settings();
        this.mailFrom = settings.getUserValue();
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    /**
     * This method builds mail session
     */
    private void buildMailSession() {
        Properties props = new Properties();
        props.put(Settings.PROTOCOL, settings.getProtocolValue());
        props.put(Settings.HOST, settings.getHostValue());
        props.put(Settings.USER, TRUE);
        props.put(Settings.SEND_PARTIAL, TRUE);
        mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
    }

    /**
     * This method sends mail
     */
    public void send() {
        try {
            buildMailSession();
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject(mailSubject);
            message.setContent(mailContent, Resource.getStr(RESPONSE_CONTEXT));
            message.setSentDate(new Date());
            Transport transport = mailSession.getTransport();
            transport.connect(settings.getHostValue(), settings.getPortValue(), settings.getUserValue(), settings.getPasswordValue());
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            logger.info(Resource.getStr(MAIL_SENDED).replace("1", mailTo));
        } catch (AddressException e) {
            logger.error(e);
        } catch (MessagingException e) {
            logger.error(e);
        }
    }
}
