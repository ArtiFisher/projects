package by.bsuir.admission.command.applications;

import by.bsuir.admission.command.applications.action.IApplicationAction;
import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Speciality;
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
 * This class load a list of application for concrete speciality
 * Also this class execute the method <code>execute</code> of the instance of class which
 * implements the interface <code>IApplicationAction</code>
 * @author AndreAY
 */
public class SpecialityApplications extends Command {

    public static final String PARAM_IS_PAID = "isPaid";
    public static final String PARAM_SPECIALITY = "speciality";
    public static final String PARAM_SPECIALITY_ID = "specialityId";
    public static final String PARAM_APPLICATION_LIST = "applicationList";
    public static final String PAGE_BODY_VIEW_SPECIALITY_APPLICATIONS = "forward.body.view.speciality.applications";
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

    public SpecialityApplications(IApplicationAction applicationAction) {
        this.applicationAction = applicationAction;
    }

    /**
     * This method loads a list of application for concrete speciality
     * Also this class execute the method <code>execute</code> of the instance of class which
     * implements the interface <code>IApplicationAction</code>
     * This method can delete an application
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int specialityId = Integer.parseInt(request.getParameter(PARAM_SPECIALITY_ID));
            boolean isPaid = Boolean.parseBoolean(request.getParameter(PARAM_IS_PAID));
            applicationAction.execute(request, getMessages());
            Speciality speciality = new SpecialityDAO().getSpecialityById(specialityId);
            applicationList = applicationDAO.getApplicationListBySpeciality(specialityId, isPaid);
            request.setAttribute(PARAM_APPLICATION_LIST, applicationList);
            request.setAttribute(PARAM_SPECIALITY, speciality);
            request.setAttribute(PARAM_IS_PAID, isPaid);
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_VIEW_SPECIALITY_APPLICATIONS));
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


