package by.bsuir.admission.command.adminaction;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class  makes a transition to the page with
 * a list of users who have not confirmed the application
 * @author AndreAY
 */
public class NoConfirmedUsers extends Command {

    public static final String PAGE_BODY_NO_CONFIRMED_USERS = "forward.body.no.confirmed.users";
    public static final String PARAM_USER_LIST = "userList";
    public static final String PARAM_USER_COUNT = "userCount";
    /**
     * This is a list of users who are on two or more specialty
     * and have not yet confirmed one of the applications
     */
    private ArrayList<User> userList;
    /**
     * This is a instance of class <code>UserDAO</code> which links entity <code>User</code>
     * with the database
     */
    private UserDAO userDAO = new UserDAO();

    /**
     * This method makes a transition to the page with
     * a list of users who have not confirmed the application
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userList = userDAO.getUserListByMorePass();
            request.setAttribute(PARAM_USER_LIST, userList);
            request.setAttribute(PARAM_USER_COUNT, userList.size());
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_NO_CONFIRMED_USERS));
            setForward(Resource.getStr(FORWARD_MAIN));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
