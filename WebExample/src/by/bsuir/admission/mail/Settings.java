package by.bsuir.admission.mail;

import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import java.io.Serializable;
import org.apache.log4j.Logger;
/**
 * This is beans class
 * This class has got a mail settings
 * @author AndreAY
 */
public class Settings implements Serializable {

    public static final String HOST = "mail.smtps.host";
    public static final String PORT = "mail.smtp.port";
    public static final String USER = "smtps.auth.user";
    public static final String PASSWORD = "smtps.auth.pass";
    public static final String PROTOCOL = "mail.transport.protocol";
    public static final String SEND_PARTIAL = "mail.smtp.sendpartial";
    public static final String PARAM_HOST = "host";
    public static final String PARAM_PORT = "port";
    public static final int DEFAULT_PORT = 465;
    public static final String PARAM_USER = "user";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_PROTOCOL = "protocol";
    /**
     * This is address of SMTP server
     */
    private String hostValue;
    /**
     * This is port of SMTP server
     */
    private int portValue;
    /**
     * This post which will be sent mail
     */
    private String userValue;
    /**
     * This is is the password of this mail
     */
    private String passwordValue;
    /**
     * This is name of mail protocol
     */
    private String protocolValue;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(Settings.class);

    /**
     * This constructor load settings value from XML
     * @throws NoPropertyException a exception if some property not found
     */
    public Settings() throws NoPropertyException {
        hostValue = XMLManager.getFirstElement(PARAM_HOST);
        try {
            portValue = Integer.parseInt(XMLManager.getFirstElement(PARAM_PORT));
        } catch (NumberFormatException ex) {
            portValue = DEFAULT_PORT;
            logger.error(ex);
        }
        userValue = XMLManager.getFirstElement(PARAM_USER);
        passwordValue = XMLManager.getFirstElement(PARAM_PASSWORD);
        protocolValue = XMLManager.getFirstElement(PARAM_PROTOCOL);
    }

    public String getHostValue() {
        return hostValue;
    }

    public void setHostValue(String hostValue) {
        this.hostValue = hostValue;
    }

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    public int getPortValue() {
        return portValue;
    }

    public void setPortValue(int portValue) {
        this.portValue = portValue;
    }

    public String getProtocolValue() {
        return protocolValue;
    }

    public void setProtocolValue(String protocolValue) {
        this.protocolValue = protocolValue;
    }

    public String getUserValue() {
        return userValue;
    }

    public void setUserValue(String userValue) {
        this.userValue = userValue;
    }
}
