package by.bsuir.admission.model.action.builders;

import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import javax.servlet.http.HttpServletRequest;

/**
 * This class gets a certificate number and a passport number from
 * request and builds a certificate
 * @author AndreAY
 */
public class CertificateBuilder {

    public static final String PATTERN_PASSPORT_NUMBER = "pattern.passport.number";
    public static final String PARAM_CERTIFICATE_ID = "certificateId";
    public static final String PARAM_PASSPORT_NUMBER = "passportNumber";
    public static final String MSG_ERR_CERTIFICATE_ID = "error.certificate.id";
    public static final String MSG_ERR_PASSPORT_NUMBER = "error.pattern.passport.number";
    /**
     * This is new certificate which  will is built
     */
    private Certificate certificate = new Certificate();
    /**
     * This object in which are written reports on incerrect data
     */
    private MessageManager messages;

    public CertificateBuilder(MessageManager messages) {
        this.messages = messages;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * This method builds a certificate
     * This method read all certificate properties, if each proterty
     * is successfully read, that certificate is successfully built
     * @param request a HttpServletRequest
     * @return <code>true</code> if certificate has built successfully
     */
    public boolean build(HttpServletRequest request) {
        boolean readingSuccessful = true;
        readingSuccessful &= readCertificateId(request);
        readingSuccessful &= readPassportNumber(request);
        return readingSuccessful;
    }

    /**
     * This method gets certificate number from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if certificate number is correct
     */
    private boolean readCertificateId(HttpServletRequest request) {
        String certificateId = request.getParameter(PARAM_CERTIFICATE_ID);
        try {
            certificate.setCertificateId(Integer.parseInt(certificateId));
            return true;
        } catch (NumberFormatException ex) {
            messages.addMessage(Resource.getStr(MSG_ERR_CERTIFICATE_ID));
            return false;
        }
    }

    /**
     * This method gets passport number from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if passport number is correct
     */
    private boolean readPassportNumber(HttpServletRequest request) {
        String passportNumber = request.getParameter(PARAM_PASSPORT_NUMBER);
        if (passportNumber != null) {
            certificate.setPassportNumber(passportNumber);
            if (passportNumber.matches(Resource.getStr(PATTERN_PASSPORT_NUMBER))) {
                return true;
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_PASSPORT_NUMBER) + " " + Resource.getStr(PATTERN_PASSPORT_NUMBER));
        return false;
    }
}
