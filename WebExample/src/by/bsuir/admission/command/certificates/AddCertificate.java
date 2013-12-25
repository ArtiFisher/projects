package by.bsuir.admission.command.certificates;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.CertificateDAO;
import by.bsuir.admission.model.action.CertificateChecker;
import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.builders.CertificateBuilder;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.xml.NoPropertyException;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class add a new certificate to the user
 * @author AndreAY
 */
public class AddCertificate extends Command {

    public static final String PARAM_USER = "user";
    public static final String PARAM_CERTIFICATE = "certificate";
    public static final String PAGE_BODY_ADD_CERTIFICATE = "forward.body.add.certificate";
    public static final String MSG_CERTIFICATE_ADDED = "message.certificate.added";
    public static final String LOGGER_MSG_CERTIFICATE_ADDED = "logger.message.user.add.certificate";
    /**
     * This is a instance of class <code>CertificateDAO</code> which links entity <code>Certificate</code>
     * with the database
     */
    private CertificateDAO certificateDAO = new CertificateDAO();
    /**
     * This object checks whether a new certificate be added to the list of user certificates
     */
    private CertificateChecker checker = new CertificateChecker(getMessages());

    /**
     * This method add a new certificate to the user
     * This method reads the certificate number and passport number of the request,
     * and searches for the certificate in the database, if the certificate is found,
     * it checks whether there can be added to the certificate to the list of user certificates.
     * If all right, it adds a certificate to the user
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CertificateBuilder certificateBuilder = new CertificateBuilder(getMessages());
        try {
            if (certificateBuilder.build(request)) {
                Certificate certificate = certificateBuilder.getCertificate();
                certificate = certificateDAO.getCertificateByIdAndPassport(certificate);
                User currentUser = (User) request.getSession().getAttribute(PARAM_USER);
                if (checker.checkCertificate(certificate, currentUser)) {
                    currentUser.addCertificate(certificate);
                    certificateDAO.setCeritificateToUser(currentUser.getUserId(), certificate.getCertificateId());
                    getMessages().addMessage(Resource.getStr(MSG_CERTIFICATE_ADDED));
                    logger.info(Resource.getStr(LOGGER_MSG_CERTIFICATE_ADDED).replace("1", currentUser.getLogin()).replace("2", certificate.getDiscipline().getName()));
                }
            }
            request.setAttribute(PARAM_CERTIFICATE, certificateBuilder.getCertificate());
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_ADD_CERTIFICATE));
            setForward(Resource.getStr(FORWARD_MAIN));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        } catch (NoPropertyException ex) {
            getMessages().addMessage(Resource.getStr(ex.getMessage()));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
