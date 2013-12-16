package com.fisher.logins;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


import com.fisher.beans.Librarian;
import com.fisher.database.dao.LibrarianDAO;
import com.fisher.database.resource.Resource;
import org.apache.log4j.Logger;

/**
 * This class implements a pattern command
 * This class authorizes users
 * @author AndreAY
 */
public class Authorization{

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_LAST_LOGIN = "lastLogin";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USER = "user";
    public static final String FORWARD_AUTHORIZATION = "forward.authorization";
    public static final String MSG_USER_ENTER = "logger.message.user.enter";
    public static final String MSG_LOGIN_ERROR = "error.login.or.password";
    public static final String MSG_DATABASE_ERROR = "error.database.fail";
    public static final String FORWARD_ERROR = "forward.error";
    public static final String FORWARD_MAIN = "forward.main";
    public static final String PARAM_BODY_PAGE = "bodyPage";
    /**
     * This address to which will is realized transition after performing the command
     */
    private String forward;
    /**
     * This is logger which print some messages to log file
     */
    protected static Logger logger = Logger.getLogger(Authorization.class);

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    /**
     * This is a instance of class <code>UserDAO</code> which links entity <code>User</code>
     * with the database
     */
    private LibrarianDAO librDAO = new LibrarianDAO();
    /**
     * This is a user which is entering in system
     */
    private Librarian librarian;

    /**
     * This gets the login and password from reques and find user in database
     * If sach user exist this user will add to session and user will enter in system
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws javax.servlet.ServletException a ServletException
     * @throws java.io.IOException a IOException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        try {
            if (login.length() > 0 && password.length() > 0) {
                librarian = librDAO.getUserByLoginPassword(login, password);
            }
            if (librarian != null) {
                setForward(Resource.getStr(FORWARD_MAIN));
                request.getSession().setAttribute(PARAM_USER, librarian);
                logger.info(librarian.getName() + " " + Resource.getStr(MSG_USER_ENTER));
            } else {
                request.setAttribute(PARAM_LAST_LOGIN, login);
                setForward(Resource.getStr(FORWARD_AUTHORIZATION));
            }
        } catch (SQLException ex) {
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
