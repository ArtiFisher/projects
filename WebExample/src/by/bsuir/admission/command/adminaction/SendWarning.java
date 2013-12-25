package by.bsuir.admission.command.adminaction;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.mail.SenderThread;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.xml.NoPropertyException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class sends warning to users who have not confirmed the application
 * @author AndreAY
 */
public class SendWarning extends Command {

    public static final String MSG_WARNING_SENDED = "message.warning.sended";
    public static final String PAGE_BODY_NO_CONFIRMED_USERS = "forward.body.no.confirmed.users";
    public static final String PARAM_SYSTEM_STATE = "state";
    public static final String PARAM_USER_LIST = "userList";
    public static final String PARAM_USER_COUNT = "userCount";
    public static final String MSG_CONTENT = "mail.content.forsed.confirm";
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
     * This method sends warning to users who have not confirmed the application
     * They warn that if they do not confirm the application, the system automatically
     * confirms the application with the highest passing score
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userList = userDAO.getUserListByMorePass();
            getMessages().addMessage(Resource.getStr(MSG_WARNING_SENDED));
            SenderThread mailSender = new SenderThread(userList, Resource.getStr(MSG_CONTENT));
            mailSender.start();
            request.setAttribute(PARAM_USER_LIST, userList);
            request.setAttribute(PARAM_USER_COUNT, userList.size());
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_NO_CONFIRMED_USERS));
            setForward(Resource.getStr(FORWARD_MAIN));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }catch(NoPropertyException ex){
            getMessages().addMessage(ex.getMessage());
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
