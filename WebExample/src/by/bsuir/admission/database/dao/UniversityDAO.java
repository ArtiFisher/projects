package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.University;
import by.bsuir.admission.resource.Resource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>University</ code> with the database
 * @author AndreAY
 */
public class UniversityDAO extends AbstractDAO {

    public static final String SQL_GET_UNIVSRSITIES = "sql.get.universities";

    /**
     * This method gets a list of all universities
     * @return a list of all universities
     * @throws SQLException a SQLException
     */
    public ArrayList<University> getUniversitiesList() throws SQLException {
        connector = new DBConnector();
        ArrayList<University> universitiesList = new ArrayList<University>();
        try {
            Statement statement = connector.getStatement();
            resultSet = statement.executeQuery(Resource.getStr(SQL_GET_UNIVSRSITIES));
            while (resultSet.next()) {
                University university = new University();
                university.setUniversityId(resultSet.getInt(1));
                university.setName(resultSet.getString(2));
                universitiesList.add(university);
            }
        } finally {
            close();
        }
        return universitiesList;
    }
}
