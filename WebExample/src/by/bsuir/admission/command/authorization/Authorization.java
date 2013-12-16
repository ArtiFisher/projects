package by.bsuir.admission.command.authorization;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.CertificateDAO;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class authorizes users
 * @author AndreAY
 */
public class Authorization extends Command {

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_LAST_LOGIN = "lastLogin";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USER = "user";
    public static final String FORWARD_AUTHORIZATION = "forward.authorization";
    public static final String MSG_USER_ENTER = "logger.message.user.enter";
    public static final String MSG_LOGIN_ERROR = "error.login.or.password";
    /**
     * This is a instance of class <code>UserDAO</code> which links entity <code>User</code>
     * with the database
     */
    private UserDAO userDAO = new UserDAO();
    /**
     * This is a instance of class <code>CertificateDAO</code> which links entity <code>Certificate</code>
     * with the database
     */
    private CertificateDAO certificateDAO = new CertificateDAO();
    /**
     * This is a user which is entering in system
     */
    private User user;

    /**
     * This gets the login and password from reques and find user in database
     * If sach user exist this user will add to session and user will enter in system
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        try {
            if (login.length() > 0 && password.length() > 0) {
                user = userDAO.getUserByLoginPassword(login, password);
            }
            if (user != null) {
                user.setCertificatesMap(certificateDAO.getUserCertificates(user.getUserId()));
                setForward(Resource.getStr(FORWARD_MAIN));
                request.getSession().setAttribute(PARAM_USER, user);
                logger.info(user.getLogin() + " " + Resource.getStr(MSG_USER_ENTER));
            } else {
                request.setAttribute(PARAM_LAST_LOGIN, login);
                getMessages().addMessage(Resource.getStr(MSG_LOGIN_ERROR));
                setForward(Resource.getStr(FORWARD_AUTHORIZATION));
            }
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
