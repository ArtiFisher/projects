package by.bsuir.admission.command.specialities;

import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.beans.Speciality;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 * This class reloads a list of <code>Speciality</code>
 * @author AndreAY
 */
public class SpecialityLoader {

    public static final String PARAM_SPECIALITY_LIST = "specialityList";
    public static final String PARAM_SELECTED_FACULTY = "selectedFaculty";
    /**
     * This is a instance of class <code>SpecialityDAO</code> which links entity <code>Speciality</code>
     * with the database
     */
    private SpecialityDAO specialityDAO = new SpecialityDAO();

    /**
     * This method receives a request from the selected faculty and loads the list of his specialties
     * @param request a HttpServletRequest
     * @throws SQLException a SQLException
     */
    public void reloadSpecialities(HttpServletRequest request) throws SQLException {
        Integer selectedFaculty = (Integer) request.getSession().getAttribute(PARAM_SELECTED_FACULTY);
        ArrayList<Speciality> specialitiesList = specialityDAO.getSpecialitiesList(selectedFaculty);
        request.getSession().setAttribute(PARAM_SPECIALITY_LIST, specialitiesList);
    }
}
