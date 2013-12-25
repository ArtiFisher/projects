package by.bsuir.admission.command.applications.action;

import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.mail.SenderThread;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import by.bsuir.admission.xml.NoPropertyException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * This class deletes a user application by admin choise
 * @author AndreAY
 */
public class DeleteApplicationByAdmin implements IApplicationAction {

    public static final String PARAM_APPLICATION_ID = "applicationId";
    public static final String PARAM_DELITING_REASON = "deletingReason";
    public static final String PARAM_USER_ID = "userId";
    public static final String MSG_CONTENT = "mail.content.applicatin.has.deleted";
    public static final String MSG_APPLICATION_DELETED = "message.application.deleted";
    public static final String MSG_MISSING_DELETING_REASON = "message.missing.deleting.reason";
    public static final String LOGGER_MSG_APPLICATION_DELETED = "logger.message.admin.delete.application";
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(DeleteApplication.class);
    /**
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    /**
     * This is a instance of class <code>UserDAO</code> which links entity <code>User</code>
     * with the database
     */
    private UserDAO userDAO = new UserDAO();

    /**
     * This method deletes a user application by admin choise.
     * With this method, the admin can delete a user application.
     * Method sends an e-mail to user in which the reason for removal.
     * @param request a HttpServletRequest
     * @param messages a object which keeps a list of masseges
     * @throws SQLException a SQLException
     * @throws NoPropertyException a exception if some property not found
     */
    public void execute(HttpServletRequest request, MessageManager messages) throws SQLException, NoPropertyException {
        String deletingReason = request.getParameter(PARAM_DELITING_REASON);
        if (deletingReason.trim().length() > 0) {
            int applicationId = Integer.parseInt(request.getParameter(PARAM_APPLICATION_ID));
            User user = userDAO.getUserByApplication(applicationId);
            int specialityId = applicationDAO.getSpecialityIdByApplication(applicationId);
            applicationDAO.deleteApplication(applicationId);
            sendAlert(deletingReason, user);
            new PassMarkSetter().setToSpeciality(specialityId);
            logger.info(Resource.getStr(LOGGER_MSG_APPLICATION_DELETED).replace("1", user.getLogin()).replace("2", deletingReason));
            messages.addMessage(Resource.getStr(MSG_APPLICATION_DELETED));
        } else {
            messages.addMessage(Resource.getStr(MSG_MISSING_DELETING_REASON));
        }
    }

    private void sendAlert(String deletingReason, User user) throws NoPropertyException {
        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        SenderThread alertSenderThread = new SenderThread(userList, Resource.getStr(MSG_CONTENT) + deletingReason);
        alertSenderThread.start();
    }
}
