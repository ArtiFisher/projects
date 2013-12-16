package by.bsuir.admission.command.adminaction;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.UserDAO;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.model.action.report.ReportBuilder;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.SystemStateEnum;
import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class completes the process proceeds
 * @author AndreAY
 */
public class AutoConfirmApplications extends Command {

    public static final String REPORT_PATH = "report.path";
    public static final String MSG_APPLICATION_CONFIRMED = "message.application.auto.confirmed";
    public static final String MSG_NOT_ALL_CONFIRMED = "message.not.all.confirmed";
    public static final String PARAM_SYSTEM_STATE = "state";
    public static final String LOGGER_MSG_STATE_CHANGED = "logger.message.admin.change.system.state";
    public static final String LOGGER_MSG_AUTO_CONFIRM = "logger.message.admin.auto.confirm.applications";
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
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    /**
     * This method completes the process proceeds
     * This method gets a list of users do not confirm the application and
     * automatically confirms the request with the highest passing score.
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userList = userDAO.getUserListByMorePass();
            getMessages().addMessage(Resource.getStr(MSG_APPLICATION_CONFIRMED));
            applicationDAO.autoConfirmApplications();
            for (User user : userList) {
                logger.info(Resource.getStr(LOGGER_MSG_AUTO_CONFIRM).replace("1", user.getLogin()));
            }
            new PassMarkSetter().setToAllSpecialities();
            changeSystemState(request);
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_NO_CONFIRMED_USERS));
            setForward(Resource.getStr(FORWARD_MAIN));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        } catch (NoPropertyException ex) {
            getMessages().addMessage(ex.getMessage());
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }

    /**
     * This method gets a list of users do not confirm the application and if it is empty,
     * the system status changes to <code>ADMISSION_COMPLETED</code> and builds a report
     * @param request a httpServletRequest
     * @throws SQLException a SQLException
     * @throws NoPropertyException a exception if property not found
     */
    private void changeSystemState(HttpServletRequest request) throws SQLException, NoPropertyException {
        userList = userDAO.getUserListByMorePass();
        if (userList.size() == 0) {
            XMLManager.setElement(PARAM_SYSTEM_STATE, SystemStateEnum.ADMISSION_COMPLETED.toString());
            logger.info(Resource.getStr(LOGGER_MSG_STATE_CHANGED).replace("1", SystemStateEnum.ADMISSION_COMPLETED.toString()));
            request.getSession().getServletContext().setAttribute(PARAM_SYSTEM_STATE, SystemStateEnum.ADMISSION_COMPLETED.toString());
            ReportBuilder reportBuilder = new ReportBuilder(request.getSession().getServletContext().getRealPath("") + Resource.getStr(REPORT_PATH));
            reportBuilder.build();
        } else {
            getMessages().addMessage(Resource.getStr(MSG_NOT_ALL_CONFIRMED));
            request.setAttribute(PARAM_USER_LIST, userList);
            request.setAttribute(PARAM_USER_COUNT, userList.size());
        }
    }
}
