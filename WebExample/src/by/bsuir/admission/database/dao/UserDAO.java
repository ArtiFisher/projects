package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>User</ code> with the database
 * @author AndreAY
 */
public class UserDAO extends AbstractDAO {

    public static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "sql.find.user.by.login.password";
    public static final String SQL_FIND_USER_BY_APPLICATION = "sql.get.user.by.application";
    public static final String SQL_FIND_USER_BY_LOGIN = "sql.find.user.by.login";
    public static final String SQL_GET_USER_BY_MORE_PASS = "sql.get.user.list.by.more.pass";
    public static final String SQL_CREATE_USER = "sql.create.user";

    /**
     * This method finds a user by login and password
     * @param login a user login
     * @param password a user password
     * @return a user if he exists, otherwise null
     * @throws SQLException a SQLException
     */
    public User getUserByLoginPassword(String login, String password) throws SQLException {
        connector = new DBConnector();
        User user = null;
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_FIND_USER_BY_LOGIN_PASSWORD));
            statement.setBytes(1, login.getBytes(CHARSET));
            statement.setBytes(2, password.getBytes(CHARSET));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = buildUser();
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method returns a user crown gave the application
     * @param applicationId a id of application
     * @return a user crown gave the application
     * @throws SQLException a SQLException
     */
    public User getUserByApplication(int applicationId) throws SQLException {
        connector = new DBConnector();
        User user = null;
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_FIND_USER_BY_APPLICATION));
            statement.setInt(1, applicationId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = buildUser();
            }
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method gets list of the users which pass on two and more specialities
     * @return a list of users
     * @throws SQLException a SQLException
     */
    public ArrayList<User> getUserListByMorePass() throws SQLException {
        connector = new DBConnector();
        ArrayList<User> userList = new ArrayList<User>();
        try {
            User user = null;
            Statement statement = connector.getStatement();
            resultSet = statement.executeQuery(Resource.getStr(SQL_GET_USER_BY_MORE_PASS));
            while (resultSet.next()) {
                userList.add(buildUser());
            }
        } finally {
            close();
        }
        return userList;
    }

    /**
     * This method checks exists user with such login
     * @param login a checked login
     * @return true if this login already exist
     * @throws SQLException a SQLException
     */
    public boolean isUserExist(String login) throws SQLException {
        connector = new DBConnector();
        User user = null;
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_FIND_USER_BY_LOGIN));
            statement.setBytes(1, login.getBytes(CHARSET));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        } finally {
            close();
        }
        return false;
    }

    /**
     * This method add new user to database
     * @param user a new user
     * @throws SQLException a SQLException
     */
    public void createUser(User user) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_CREATE_USER));
            statement.setBytes(1, user.getLogin().getBytes(CHARSET));
            statement.setBytes(2, user.getPassword().getBytes(CHARSET));
            statement.setBytes(3, user.getFirstName().getBytes(CHARSET));
            statement.setBytes(4, user.getSurname().getBytes(CHARSET));
            statement.setBytes(5, user.getMail().getBytes(CHARSET));
            statement.executeUpdate();
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        } finally {
            close();
        }
    }

    /**
     * This method builds a user
     * This method gets data from <code>resultSet</code> and
     * sets this data to a new user
     * @return a user
     * @throws SQLException a SQLException
     */
    private User buildUser() throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt(1));
        user.setLogin(resultSet.getString(2));
        user.setFirstName(resultSet.getString(3));
        user.setSurname(resultSet.getString(4));
        user.setIsAdmin(resultSet.getBoolean(5));
        user.setMail(resultSet.getString(6));
        return user;
    }
}
