package by.bsuir.admission.command.applications;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.command.specialities.SpecialityLoader;
import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.action.DisciplineListChecker;
import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This method adds application
 * @author AndreAY
 */
public class AddApplication extends Command {

    public static final String PARAM_SPECIALITY_ID = "specialityId";
    public static final String PARAM_USER = "user";
    public static final String PARAM_IS_PAID = "isPaid";
    public static final String MSG_APPLICATION_ADDED = "message.application.added";
    public static final String MSG_ERR_DISCIPLINES_LIST = "error.application.incorrect.disciplines";
    public static final String MSG_ERR_APPLICATION_EXIST = "error.application.exist";
    public static final String PAGE_BODY_VIEW_SPECIALITIES = "forward.body.view.specialities";
    public static final String LOGGER_MSG_APPLICATION_ADDED = "logger.message.user.add.application";
    /**
     * This is a instance of class <code>SpecialityDAO</code> which links entity <code>Speciality</code>
     * with the database
     */
    private SpecialityDAO specialityDAO = new SpecialityDAO();
    /**
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    /**
     * This method adds application
     * This method gets from request information on added to application and is checked
     * possibility of its accompaniment. If all it is correct, that application is added
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
            Speciality speciality = specialityDAO.getSpecialityById(specialityId);
            User user = (User) request.getSession().getAttribute(PARAM_USER);
            createApplication(user, speciality, isPaid);
            new SpecialityLoader().reloadSpecialities(request);
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, Resource.getStr(PAGE_BODY_VIEW_SPECIALITIES));
        } catch (SQLException ex) {
            getMessages().addMessage(Resource.getStr(MSG_DATABASE_ERROR));
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }

    /**
     * This method buids a <code>Application</code> by user and speciality
     * @param user a user which want to add a new application
     * @param speciality a speciality ti which will add a application
     * @param isPaid it's <code>true</code> if user want add application to paid 
     * @throws SQLException a Exception in database
     */
    private void createApplication(User user, Speciality speciality, boolean isPaid) throws SQLException {
        DisciplineListChecker disciplineListChecker = new DisciplineListChecker(user, speciality);
        Application application = new Application(user, speciality, isPaid);
        if (disciplineListChecker.check()) {
            if (!applicationDAO.isApplicationFound(application)) {
                applicationDAO.createApplication(application);
                new PassMarkSetter().setToSpeciality(speciality.getSpecialityId());
                getMessages().addMessage(Resource.getStr(MSG_APPLICATION_ADDED));
                logger.info(Resource.getStr(LOGGER_MSG_APPLICATION_ADDED).replace("1", user.getLogin()).replace("2", speciality.getName()));
            } else {
                getMessages().addMessage(Resource.getStr(MSG_ERR_APPLICATION_EXIST));
            }
        } else {
            getMessages().addMessage(Resource.getStr(MSG_ERR_DISCIPLINES_LIST));
            getMessages().importMessages(disciplineListChecker.getMissingDisciplineList());
        }
    }
}
