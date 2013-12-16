package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.Faculty;
import by.bsuir.admission.resource.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>Faculty</ code> with the database
 * @author AndreAY
 */
public class FacultiesDAO extends AbstractDAO{

    public static final String SQL_GET_FACULTIES="sql.get.faculties.by.university";
/**
 * This method returns a list of faculties by university
 * @param universityId an id of university
 * @return a list of faculties
 * @throws SQLException a SQLException
 */
    public ArrayList<Faculty> getFacultiesList(int universityId) throws SQLException{
        connector=new DBConnector();
        ArrayList<Faculty> facultiesList=new ArrayList<Faculty>();
        try{
            PreparedStatement statement=connector.getPreparedStatement(Resource.getStr(SQL_GET_FACULTIES));
            statement.setInt(1, universityId);
            resultSet=statement.executeQuery();
            while(resultSet.next()){
                Faculty faculty=new Faculty();
                faculty.setFacultyId(resultSet.getInt(1));
                faculty.setName(resultSet.getString(2));
                facultiesList.add(faculty);
            }
        }finally{
            close();
        }
        return facultiesList;
    }

}
