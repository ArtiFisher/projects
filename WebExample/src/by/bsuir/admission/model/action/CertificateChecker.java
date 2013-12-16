package by.bsuir.admission.model.action;

import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import org.apache.log4j.Logger;

/**
 * This class checks can be added certificate to user
 * @author AndreAY
 */
public class CertificateChecker {

    public static final String MSG_ERR_NO_CERTIFICATE = "error.certificate.not.found";
    public static final String MSG_ERR_DISCIPLINE = "error.certificate.discipline.repeat";
    public static final String MSG_ERR_PASSPORT_NUMBER = "error.certificate.passport.number.not.correspond";
    public static final String MSG_ERR_CERTIFICATE_NUMBER = "error.certificate.number";
    public static final String XML_PARAM_CERTIFICATE_COUNT = "max-certificate-number";
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(CertificateChecker.class);
    /**
     * This object in which are written reports on incerrect data
     */
    private MessageManager messages;

    public CertificateChecker(MessageManager messages) {
        this.messages = messages;
    }

    /**
     * This method checks can be added certificate to user     *
     * @param certificate a added certificate
     * @param user a user which is added certificate
     * @return true if certificate can be added and user not null and certificate not null
     * @throws NoPropertyException if in XML not found property "max-certificate-number"
     */
    public boolean checkCertificate(Certificate certificate, User user) throws NoPropertyException {
        boolean certificateOk = true;
        if (user != null && certificate != null) {
            certificateOk &= checkCertificateCount(user);
            certificateOk &= checkDisciplineAndPassportNumber(certificate, user);
        } else {
            messages.addMessage(Resource.getStr(MSG_ERR_NO_CERTIFICATE));
            certificateOk = false;
        }
        return certificateOk;
    }

    /**
     * This method checks the amount a certificate already available beside user.
     * If their less than maximum amount certificate method returns true
     * @param user a user which is added certificate
     * @return true if certificate can be added
     * @throws NoPropertyException if in XML not found property "max-certificate-number"
     */
    private boolean checkCertificateCount(User user) throws NoPropertyException {
        int maxCertificateCount = Integer.parseInt(XMLManager.getFirstElement(XML_PARAM_CERTIFICATE_COUNT));
        int certificateCount = user.getCertificatesMap().values().size();
        if (certificateCount < maxCertificateCount) {
            return true;
        } else {
            messages.addMessage(Resource.getStr(MSG_ERR_CERTIFICATE_NUMBER));
            return false;
        }
    }

    /**
     * This method checks that beside user was not a certificate
     * on this discipline. Is it In the same way checked that beside
     * all certificate was one number of the passport
     * @param certificate a added certificate
     * @param user a user which is added certificate
     * @return true if certificate can be added
     */
    private boolean checkDisciplineAndPassportNumber(Certificate certificate, User user) {
        for (Certificate userCertificate : user.getCertificatesMap().values()) {
            if (userCertificate.getDiscipline().getDisciplineId() == certificate.getDiscipline().getDisciplineId()) {
                messages.addMessage(Resource.getStr(MSG_ERR_DISCIPLINE));
                logger.info(Resource.getStr(MSG_ERR_DISCIPLINE) + user.getLogin());
                return false;
            }
            if (!userCertificate.getPassportNumber().equals(certificate.getPassportNumber())) {
                messages.addMessage(Resource.getStr(MSG_ERR_PASSPORT_NUMBER));
                logger.info(user.getLogin() + " " + Resource.getStr(MSG_ERR_PASSPORT_NUMBER));
                return false;
            }
        }
        return true;
    }
}
