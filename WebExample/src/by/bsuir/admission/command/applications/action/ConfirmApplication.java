package by.bsuir.admission.command.applications.action;

import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * This class confirms an application by user choise
 * @author AndreAY
 */
public class ConfirmApplication implements IApplicationAction {

    public static final String PARAM_APPLICATION_ID = "applicationId";
    public static final String PARAM_USER = "user";
    public static final String LOGGER_MSG_APPLICATION_CONFIRMED = "logger.message.user.confirm.application";
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
     * This method confirms an application by user choise
     * This method removes all user applications, except the one he chose
     * @param request a HttpServletRequest
     * @param messages a object which keeps a list of masseges
     * @throws SQLException a SQLException
     */
    public void execute(HttpServletRequest request, MessageManager messages) throws SQLException {
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        int applicationId = Integer.parseInt(request.getParameter(PARAM_APPLICATION_ID));
        ArrayList<Integer> changedSpecialities = new SpecialityDAO().getSpecialitiesIdByUser(user.getUserId());
        applicationDAO.deleteOtherApplications(applicationId, user.getUserId());
        logger.info(Resource.getStr(LOGGER_MSG_APPLICATION_CONFIRMED).replace("1", user.getLogin()));
        new PassMarkSetter().setToSpecialities(changedSpecialities);
    }
}
