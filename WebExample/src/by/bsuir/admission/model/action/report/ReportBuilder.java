package by.bsuir.admission.model.action.report;

import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.FacultiesDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.database.dao.UniversityDAO;
import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Faculty;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.University;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import jxl.write.WriteException;
import org.apache.log4j.Logger;

/**
 * This class buids report of users enrolled in universities
 * @author AndreAY
 */
public class ReportBuilder {

    private String reportPath;
    /**
     * This object writes a report to Excel file
     */
    private ApplicationWriter applicationWriter;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(ReportBuilder.class);
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
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    public ReportBuilder(String reportPath) {
        this.reportPath = reportPath;
    }

    /**
     * This method builds a report
     * @throws SQLException a database exception
     */
    public void build() throws SQLException {
        try {
            applicationWriter = new ApplicationWriter(reportPath);
            ArrayList<University> universityList = universityDAO.getUniversitiesList();
            for (University university : universityList) {
                applicationWriter.writeUniversity(university);
                ArrayList<Faculty> facultyList = facultiesDAO.getFacultiesList(university.getUniversityId());
                writeFaculties(facultyList);
            }
            applicationWriter.close();
        } catch (IOException ex) {
            logger.error(ex);
        } catch (WriteException ex) {
            logger.error(ex);
        }
    }

    /**
     * This method writes an information about faculties
     * @param facultyList a list of faculties
     * @throws IOException an exception writing in file
     * @throws WriteException an exception writing in excel file
     */
    private void writeFaculties(ArrayList<Faculty> facultyList) throws WriteException, SQLException {
        for (Faculty faculty : facultyList) {
            applicationWriter.writeFaculty(faculty);
            ArrayList<Speciality> specialitiesList = specialityDAO.getSpecialitiesList(faculty.getFacultyId());
            writeSpecialities(specialitiesList);
        }
    }

    /**
     * This method writes an information about specialities
     * @param specialitiesList a list of speciality
     * @throws IOException an exception writing in file
     * @throws WriteException an exception writing in excel file
     */
    private void writeSpecialities(ArrayList<Speciality> specialitiesList) throws WriteException, SQLException {
        for (Speciality speciality : specialitiesList) {
            applicationWriter.writeSpeciality(speciality, true);
            ArrayList<Application> applications = applicationDAO.getApplicationListBySpeciality(speciality.getSpecialityId(), false);
            Collections.sort(applications, new ApplicationsComparatorByUserName<Application>());
            applicationWriter.writeApplications(applications, speciality.getPassMark());
            applicationWriter.writeSpeciality(speciality, false);
            applications = applicationDAO.getApplicationListBySpeciality(speciality.getSpecialityId(), true);
            Collections.sort(applications, new ApplicationsComparatorByUserName<Application>());
            applicationWriter.writeApplications(applications, speciality.getPassMarkPaid());
        }
    }
}
