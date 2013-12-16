package by.bsuir.admission.command.specialities;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.database.dao.FacultiesDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.database.dao.UniversityDAO;
import by.bsuir.admission.model.beans.Faculty;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.University;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class loads a university list
 * @author AndreAY
 */
public class LoadUniversities extends Command {

    public static final String PARAM_UNIVERSITY_LIST = "universityList";
    public static final String PARAM_FACULTY_LIST = "facultyList";
    public static final String PARAM_SPECIALITY_LIST = "specialityList";
    public static final String PARAM_SELECTED_UNIVERSITY = "selectedUniversity";
    public static final String PARAM_SELECTED_FACULTY = "selectedFaculty";
    public static final String PAGE_BODY_VIEW_SPECIALITIES = "forward.body.view.specialities";
    /**
     * This is a list of all universities
     */
    private ArrayList<University> universityList;
    /**
     * This is a list of faculties specific university
     */
    private ArrayList<Faculty> facutyList;
    /**
     * This is a list of specialties specific faculty
     */
    private ArrayList<Speciality> specialitiesList;
    /**
     * This is a instance of class <code>UniversityDAO</code> which links entity <code>University</code>
     * with the database
     */
    private UniversityDAO universityDAO = new UniversityDAO();
    /**
     * This is a instance of class <code>FacultyDAO</code> which links entity <code>Faculty</code>
     * with the database
     */
    private FacultiesDAO facultiesDAO = new FacultiesDAO();
    /**
     * This is a instance of class <code>SpecialityDAO</code> which links entity <code>Speciality</code>
     * with the database
     */
    private SpecialityDAO specialityDAO = new SpecialityDAO();

    /**
     * This method loads a university list
     * This method also loads a faculty list for the first university in the list
     * This method also loads the list of specialties for the first faculty in the list
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer selectedUniversity = null;
            Integer selectedFaculty = null;
            universityList = universityDAO.getUniversitiesList();
            request.getSession().setAttribute(PARAM_UNIVERSITY_LIST, universityList);
            if (universityList.size() != 0) {
                selectedUniversity = universityList.get(0).getUniversityId();
                facutyList = facultiesDAO.getFacultiesList(selectedUniversity);
                if (facutyList.size() != 0) {
                    selectedFaculty = facutyList.get(0).getFacultyId();
                    specialitiesList = specialityDAO.getSpecialitiesList(selectedFaculty);
                }
            }
            request.getSession().setAttribute(PARAM_FACULTY_LIST, facutyList);
            request.getSession().setAttribute(PARAM_SPECIALITY_LIST, specialitiesList);
            request.getSession().setAttribute(PARAM_SELECTED_UNIVERSITY, selectedUniversity);
            request.getSession().setAttribute(PARAM_SELECTED_FACULTY, selectedFaculty);
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_VIEW_SPECIALITIES));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}
