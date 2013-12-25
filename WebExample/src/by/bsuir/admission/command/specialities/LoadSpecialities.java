package by.bsuir.admission.command.specialities;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class loads a speciality list by selected faculty
 * @author AndreAY
 */
public class LoadSpecialities extends Command {

    public static final String PARAM_SPECIALITY_LIST = "specialityList";
    public static final String PARAM_SELECTED_FACULTY = "selectedFaculty";
    public static final String PAGE_BODY_VIEW_SPECIALITIES = "forward.body.view.specialities";
    /**
     * This is a list of specialties specific faculty
     */
    private ArrayList<Speciality> specialitiesList;
    /**
     * This is a instance of class <code>SpecialityDAO</code> which links entity <code>Speciality</code>
     * with the database
     */
    private SpecialityDAO specialityDAO = new SpecialityDAO();

    /**
     * This method loads a speciality list by selected faculty
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String selectedFaculty = request.getParameter(PARAM_SELECTED_FACULTY);
            specialitiesList = specialityDAO.getSpecialitiesList(Integer.parseInt(selectedFaculty));
            request.getSession().setAttribute(PARAM_SPECIALITY_LIST, specialitiesList);
            request.getSession().setAttribute(PARAM_SELECTED_FACULTY, new Integer(selectedFaculty));
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_VIEW_SPECIALITIES));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
