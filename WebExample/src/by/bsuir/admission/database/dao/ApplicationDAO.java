package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>Application</ code> with the database
 * @author AndreAY
 */
public class ApplicationDAO extends AbstractDAO {

    public static final String SQL_AUTOCONFIRM = "sql.application.autoconfirm";
    public static final String SQL_IS_APPLICATION_FOUND = "sql.is.application.found";
    public static final String SQL_GET_SPECIALITY_ID_BY_APPLICATION = "sql.get.speciality.id.by.application";
    public static final String SQL_CREATE_APPLICATION = "sql.create.application";
    public static final String SQL_DELETE_APPLICATION = "sql.delete.application";
    public static final String SQL_DELETE_OTHER_APPLICATIONS = "sql.delete.other.applications";
    public static final String SQL_GET_APPLICATION_LIST_BY_USER = "sql.get.application.list.by.user";
    public static final String SQL_GET_APPLICATION_LIST_BY_SPECIALITY = "sql.get.application.list.by.speciality";

    /**
     * This method returns the id of the specialty for which application
     * has been filed with the specified id
     * @param applicationId a id of application
     * @return a id of speciality
     * @throws SQLException a SQLException
     */
    public int getSpecialityIdByApplication(int applicationId) throws SQLException {
        connector = new DBConnector();
        int specialityId = 0;
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_SPECIALITY_ID_BY_APPLICATION));
            statement.setInt(1, applicationId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                specialityId = resultSet.getInt(1);
            }
        } finally {
            close();
        }
        return specialityId;
    }

    /**
     * This method checks whether the same application in a database
     * @param application a application
     * @return true if this application is exicting in database
     * @throws SQLException a SQLException
     */
    public boolean isApplicationFound(Application application) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_IS_APPLICATION_FOUND));
            statement.setInt(1, application.getUser().getUserId());
            statement.setInt(2, application.getSpeciality().getSpecialityId());
            statement.setBoolean(3, application.isIsPaid());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } finally {
            close();
        }
        return false;
    }

    /**
     * This method add an new application to database
     * @param application an application which will added to database
     * @throws SQLException a SQLException
     */
    public void createApplication(Application application) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_CREATE_APPLICATION));
            statement.setInt(1, application.getUser().getUserId());
            statement.setInt(2, application.getSpeciality().getSpecialityId());
            statement.setBoolean(3, application.isIsPaid());
            statement.executeUpdate();
        } finally {
            close();
        }
    }

    /**
     * This method gets all applications of the user
     * @param user a user whose applications are received
     * @return a list of applications of the user
     * @throws SQLException a SQLException
     */
    public ArrayList<Application> getApplicationListByUser(User user) throws SQLException {
        connector = new DBConnector();
        ArrayList<Application> applicationList = new ArrayList<Application>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_APPLICATION_LIST_BY_USER));
            statement.setInt(1, user.getUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application currentApplication = new Application();
                currentApplication.setApplicationId(resultSet.getInt(1));
                currentApplication.setIsPaid(resultSet.getBoolean(2));
                Speciality speciality = new Speciality();
                speciality.setSpecialityId(resultSet.getInt(3));
                speciality.setName(resultSet.getString(4));
                speciality.setIntake(resultSet.getInt(5));
                speciality.setIntakePaid(resultSet.getInt(6));
                speciality.setPassMark(resultSet.getInt(7));
                speciality.setPassMarkPaid(resultSet.getInt(8));
                currentApplication.setSpeciality(speciality);
                currentApplication.setFacultyName(resultSet.getString(9));
                currentApplication.setUniversityName(resultSet.getString(10));
                applicationList.add(currentApplication);
            }
        } finally {
            close();
        }
        return applicationList;
    }

    /**
     * This method gets all applications of the specislity
     * @param specialityId a id of speciality whose applications are received
     * @param isPaid a type of applications we receive (paid or budget)
     * @return a list of applications
     * @throws SQLException a SQLException
     */
    public ArrayList<Application> getApplicationListBySpeciality(int specialityId, boolean isPaid) throws SQLException {
        connector = new DBConnector();
        ArrayList<Application> applicationList = new ArrayList<Application>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_APPLICATION_LIST_BY_SPECIALITY));
            statement.setInt(1, specialityId);
            statement.setBoolean(2, isPaid);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                User user = new User();
                user.setTotalMark(resultSet.getInt(1));
                user.setUserId(resultSet.getInt(2));
                user.setFirstName(resultSet.getString(3));
                user.setSurname(resultSet.getString(4));
                application.setUser(user);
                application.setApplicationId(resultSet.getInt(5));
                applicationList.add(application);
            }
        } finally {
            close();
        }
        return applicationList;
    }

    /**
     * This method deletes an application by id
     * @param applicationId a deleting application
     * @throws SQLException a SQLException
     */
    public void deleteApplication(int applicationId) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_DELETE_APPLICATION));
            statement.setInt(1, applicationId);
            statement.executeUpdate();
        } finally {
            close();
        }
    }

    /**
     * This method removes all applications of the user except the specified application
     * @param applicationId an application is not removed
     * @param userId a id of user whose applications are deleting
     * @throws SQLException a SQLException
     */
    public void deleteOtherApplications(int applicationId, int userId) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_DELETE_OTHER_APPLICATIONS));
            statement.setInt(1, applicationId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } finally {
            close();
        }
    }

    /**
     * This method calls a stored procedure that automatically confirms
     * the request with the highest passing score for users who are
     * on two or more specialty
     * @throws SQLException a SQLException
     */
    public void autoConfirmApplications() throws SQLException {
        connector = new DBConnector();
        try {
            Statement statement = connector.getStatement();
            statement.executeUpdate(Resource.getStr(SQL_AUTOCONFIRM));
        } finally {
            close();
        }
    }
}
