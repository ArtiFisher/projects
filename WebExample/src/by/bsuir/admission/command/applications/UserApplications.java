package by.bsuir.admission.command.applications;

import by.bsuir.admission.command.applications.action.IApplicationAction;
import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.model.beans.Application;
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
 * This class load a list of application for concrete user
 * Also this class execute the method <code>execute</code> of the instance of class which
 * implements the interface <code>IApplicationAction</code>
 * @author AndreAY
 */
public class UserApplications extends Command {

    public static final String PARAM_USER = "user";
    public static final String PARAM_APPICATION_LIST_SIZE = "applicationListSize";
    public static final String PARAM_APPLICATION_LIST = "applicationList";
    public static final String PAGE_BODY_VIEW_APPLICATIONS = "forward.body.view.applications";
    /**
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    /**
     * A list of application for concrete speciality
     */
    private ArrayList<Application> applicationList;
    /**
     * This is link to instanse of class which
     * implements the interface <code>IApplicationAction</code>
     * This object change some application
     */
    private IApplicationAction applicationAction;

    public UserApplications(IApplicationAction applicationAction) {
        this.applicationAction = applicationAction;
    }

    /**
     * This method loads a list of application for concrete user
     * Also this class execute the method <code>execute</code> of the instance of class which
     * implements the interface <code>IApplicationAction</code>
     * This method can delete an application or confirm a user's application
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            applicationAction.execute(request, getMessages());
            User user = (User) request.getSession().getAttribute(PARAM_USER);
            applicationList = applicationDAO.getApplicationListByUser(user);
            request.setAttribute(PARAM_APPLICATION_LIST, applicationList);
            request.setAttribute(PARAM_APPICATION_LIST_SIZE, applicationList.size());
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_VIEW_APPLICATIONS));
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
}
