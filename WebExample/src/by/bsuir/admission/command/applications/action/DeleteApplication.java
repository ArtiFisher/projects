package by.bsuir.admission.command.applications.action;

import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
/**
 * This class deletes a user application by user choise
 * @author AndreAY
 */
public class DeleteApplication implements IApplicationAction {

    public static final String PARAM_APPLICATION_ID = "applicationId";
    public static final String PARAM_USER_ID = "userId";
    public static final String LOGGER_MSG_APPLICATION_DELETED = "logger.message.user.delete.application";
    /**
     * This is logger which print some messages to log file
     */
    private  static Logger logger = Logger.getLogger(DeleteApplication.class);
    /**
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    /**
     * This method deletes a user application by user choise
     * @param request a HttpServletRequest
     * @param messages a object which keeps a list of masseges
     * @throws SQLException a SQLException
     */
    public void execute(HttpServletRequest request, MessageManager messages) throws SQLException {
        int applicationId = Integer.parseInt(request.getParameter(PARAM_APPLICATION_ID));
        int specialityId = applicationDAO.getSpecialityIdByApplication(applicationId);
        applicationDAO.deleteApplication(applicationId);
        new PassMarkSetter().setToSpeciality(specialityId);
        logger.info(Resource.getStr(LOGGER_MSG_APPLICATION_DELETED));
    }
}
