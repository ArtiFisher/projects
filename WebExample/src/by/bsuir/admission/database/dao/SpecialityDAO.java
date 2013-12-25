package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.Discipline;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.resource.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>Speciality</ code> with the database
 * @author AndreAY
 */
public class SpecialityDAO extends AbstractDAO {

    public static final String SQL_GET_ALL_SPECIALITIES = "sql.get.all.specialities";
    public static final String SQL_SET_PASS_MARK = "sql.set.pass.mark";
    public static final String SQL_GET_SPECIALITIES_BY_FACULTIES = "sql.get.specialities.by.faculty";
    public static final String SQL_GET_SPECIALITIES_BY_ID = "sql.get.specialities.by.id";
    public static final String SQL_GET_SPECIALITIES_ID_BY_USER = "sql.get.specialities.id.by.user";
    public static final String SQL_GET_DISCIPLINES_BY_SPECIALITY = "sql.get.disciplines.by.speciality";

    /**
     * This method returns a list of speciality by faculty
     * @param facultyId an id of faculty
     * @return a list of speciality by faculty
     * @throws SQLException a SQLException
     */
    public ArrayList<Speciality> getSpecialitiesList(int facultyId) throws SQLException {
        connector = new DBConnector();
        ArrayList<Speciality> specialitiesList = new ArrayList<Speciality>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_SPECIALITIES_BY_FACULTIES));
            statement.setInt(1, facultyId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Speciality speciality = buildSpeciality();
                specialitiesList.add(speciality);
            }
            setDiscipline(specialitiesList);
        } finally {
            close();
        }
        return specialitiesList;
    }

    /**
     * This method returns a list of specialties for which the user submitted an application
     * @param userId a id of user
     * @return a list of id specialities
     * @throws SQLException a SQLException
     */
    public ArrayList<Integer> getSpecialitiesIdByUser(int userId) throws SQLException {
        connector = new DBConnector();
        ArrayList<Integer> specialityIdList = new ArrayList<Integer>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_SPECIALITIES_ID_BY_USER));
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                specialityIdList.add(resultSet.getInt(1));
            }
        } finally {
            close();
        }
        return specialityIdList;
    }

    /**
     * This method returns a list of all specialities
     * @return a list of all specialities
     * @throws SQLException a SQLException
     */
    public ArrayList<Speciality> getAllSpecialities() throws SQLException {
        connector = new DBConnector();
        ArrayList<Speciality> specialitiesList = new ArrayList<Speciality>();
        try {
            Statement statement = connector.getStatement();
            resultSet = statement.executeQuery(Resource.getStr(SQL_GET_ALL_SPECIALITIES));
            while (resultSet.next()) {
                Speciality speciality = buildSpeciality();
                specialitiesList.add(speciality);
            }
        } finally {
            close();
        }
        return specialitiesList;
    }

    /**
     * This method returns a speciality by id
     * @param specialityId a id of speciality
     * @return a speciality
     * @throws SQLException a SQLException
     */
    public Speciality getSpecialityById(int specialityId) throws SQLException {
        connector = new DBConnector();
        Speciality speciality = null;
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_SPECIALITIES_BY_ID));
            statement.setInt(1, specialityId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                speciality = buildSpeciality();
                ArrayList<Speciality> specialityList = new ArrayList<Speciality>();
                specialityList.add(speciality);
                setDiscipline(specialityList);
            }
        } finally {
            close();
        }
        return speciality;
    }

    /**
     * This method sets a pass marks to speciality
     * @param speciality a speciality to which will some pass marks
     * @throws SQLException a SQLException
     */
    public void setPassMark(Speciality speciality) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_SET_PASS_MARK));
            statement.setInt(1, speciality.getPassMark());
            statement.setInt(2, speciality.getPassMarkPaid());
            statement.setInt(3, speciality.getSpecialityId());
            statement.executeUpdate();
        } finally {
            close();
        }
    }

    /**
     * This method gets a discipline list by speciality and adds the disciplines
     * to input instance of <code>Speciality</code>
     * @param specialitiesList a list of specialities
     * @throws SQLException a SQLException
     */
    private void setDiscipline(ArrayList<Speciality> specialitiesList) throws SQLException {
        connector = new DBConnector();
        PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_DISCIPLINES_BY_SPECIALITY));
        for (Speciality speciality : specialitiesList) {
            statement.setInt(1, speciality.getSpecialityId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Discipline discipline = new Discipline();
                discipline.setDisciplineId(resultSet.getInt(1));
                discipline.setName(resultSet.getString(2));
                discipline.setOptionalGroupe(resultSet.getInt(3));
                speciality.addDiscipline(discipline);
            }
        }
    }

    /**
     * This method builds a speciality
     * This method gets data from <code>resultSet</code> and
     * sets this data to a new speciality
     * @return a speciality
     * @throws SQLException a SQLException
     */
    private Speciality buildSpeciality() throws SQLException {
        Speciality speciality = new Speciality();
        speciality.setSpecialityId(resultSet.getInt(1));
        speciality.setName(resultSet.getString(2));
        speciality.setIntake(resultSet.getInt(3));
        speciality.setIntakePaid(resultSet.getInt(4));
        speciality.setPassMark(resultSet.getInt(5));
        speciality.setPassMarkPaid(resultSet.getInt(6));
        return speciality;
    }
}
