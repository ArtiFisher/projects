package com.fisher.database.dao;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.fisher.database.DBConnector;
import com.fisher.beans.Librarian;
import com.fisher.database.resource.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LibrarianDAO extends AbstractDAO {

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
     * @throws java.sql.SQLException a SQLException
     */
    public Librarian getUserByLoginPassword(String login, String password) throws SQLException {
        connector = new DBConnector();
        Librarian user = null;
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
    public Librarian getUserByApplication(int applicationId) throws SQLException {
        connector = new DBConnector();
        Librarian user = null;
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
    public ArrayList<Librarian> getUserListByMorePass() throws SQLException {
        connector = new DBConnector();
        ArrayList<Librarian> userList = new ArrayList<Librarian>();
        try {
            Librarian user = null;
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
        Librarian user = null;
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
    public void createUser(Librarian user) throws SQLException {
        connector = new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_CREATE_USER));
            statement.setBytes(1, user.getName().getBytes(CHARSET));
            statement.setBytes(2, user.getSurname().getBytes(CHARSET));
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
    private Librarian buildUser() throws SQLException {
        Librarian user = new Librarian();
        user.setLibrarianId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setSurname(resultSet.getString(3));
        return user;
    }
}
