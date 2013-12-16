package by.bsuir.admission.command.authorization;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.builders.UserBuilder;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class performs user registration
 * @author AndreAY
 */
public class Registration extends Command {

    public static final String MSG_ERR_EXIST = "error.login.exist";
    public static final String MSG_USER_CREATED = "message.user.created";
    public static final String LOGGER_MSG_USER_CREATED = "logger.message.user.has.registred";
    public static final String FORWARD_AUTHORIZATION = "forward.authorization";
    public static final String FORWARD_REGISTRATION = "forward.registration";
    public static final String PARAM_USER = "user";
    /**
     * This is a instance of class <code>UserDAO</code> which links entity <code>User</code>
     * with the database
     */
    UserDAO userDAO = new UserDAO();

    /**
     * This method creates a new user
     * This method gets the data about the user of the request, checks them,
     * and if they are correct, then creates a new user
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBuilder userBuilder = new UserBuilder(getMessages());
        try {
            if (userBuilder.build(request)) {
                User user = userBuilder.getUser();
                if (!userDAO.isUserExist(user.getLogin())) {
                    userDAO.createUser(user);
                    getMessages().addMessage(Resource.getStr(MSG_USER_CREATED));
                    logger.info(Resource.getStr(LOGGER_MSG_USER_CREATED).replace("1", user.getLogin()));
                    setForward(Resource.getStr(FORWARD_AUTHORIZATION));
                } else {
                    getMessages().addMessage(Resource.getStr(MSG_ERR_EXIST));
                    setForward(Resource.getStr(FORWARD_REGISTRATION));
                }
            } else {
                request.setAttribute(PARAM_USER, userBuilder.getUser());
                setForward(Resource.getStr(FORWARD_REGISTRATION));
            }
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
