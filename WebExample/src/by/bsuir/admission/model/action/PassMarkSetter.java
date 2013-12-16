package by.bsuir.admission.model.action;

import by.bsuir.admission.database.dao.ApplicationDAO;
import by.bsuir.admission.database.dao.SpecialityDAO;
import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Speciality;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassMarkSetter {

    /**
     * A list of application for concrete speciality
     */
    private ArrayList<Application> applicationList;
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
     * This is a instance of class <code>ApplicationDAO</code> which links entity <code>Application</code>
     * with the database
     */
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    /**
     * This method sets pass mark to all specialities
     * @throws SQLException a SQLException
     */
    public void setToAllSpecialities() throws SQLException {
        specialitiesList = specialityDAO.getAllSpecialities();
        for (Speciality speciality : specialitiesList) {
            setPassMark(speciality);
            setPassMarkPaid(speciality);
            specialityDAO.setPassMark(speciality);
        }
    }

    /**
     * This method sets pass mark to specialities from list
     * @param specialitiesList a list of specialities to which will set pass mark
     * @throws SQLException a SQLException
     */
    public void setToSpecialities(ArrayList<Integer> specialitiesList) throws SQLException {
        for (Integer specialityId : specialitiesList) {
            Speciality speciality = specialityDAO.getSpecialityById(specialityId);
            setPassMark(speciality);
            setPassMarkPaid(speciality);
            specialityDAO.setPassMark(speciality);
        }
    }

    /**
     * This method sets pass mark to speciality
     * @param specialityId a speciality to which will set pass mark
     * @throws SQLException a SQLException
     */
    public void setToSpeciality(int specialityId) throws SQLException {
        Speciality speciality = specialityDAO.getSpecialityById(specialityId);
        setPassMark(speciality);
        setPassMarkPaid(speciality);
        specialityDAO.setPassMark(speciality);
    }

    /**
     * This method calculates add sets pass mark to speciality
     * @param specialityId a speciality to which will set pass mark
     * @throws SQLException a SQLException
     */
    private void setPassMark(Speciality speciality) throws SQLException {
        applicationList = applicationDAO.getApplicationListBySpeciality(speciality.getSpecialityId(), false);
        if (applicationList.size() >= speciality.getIntake() && applicationList.size() > 0) {
            int passMark = applicationList.get(speciality.getIntake() - 1).getUser().getTotalMark();
            speciality.setPassMark(passMark);
        } else {
            speciality.setPassMark(0);
        }
    }

    /**
     * This method calculates add sets pass mark (paid) to speciality
     * @param specialityId a speciality to which will set pass mark
     * @throws SQLException a SQLException
     */
    private void setPassMarkPaid(Speciality speciality) throws SQLException {
        applicationList = applicationDAO.getApplicationListBySpeciality(speciality.getSpecialityId(), true);
        if (applicationList.size() >= speciality.getIntakePaid() && applicationList.size() > 0) {
            int passMarkPaid = applicationList.get(speciality.getIntakePaid() - 1).getUser().getTotalMark();
            speciality.setPassMarkPaid(passMarkPaid);
        } else {
            speciality.setPassMarkPaid(0);
        }
    }
}
