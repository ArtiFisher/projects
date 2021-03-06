package by.epam.library.database.dao;

import by.epam.library.database.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.library.servlet.ServletController;
import org.apache.log4j.Logger;

import java.util.Date;

import by.epam.library.actions.commands.ErrorOutput;


public class EntryDAO implements AbstractDao {
    static Logger logger = Logger.getLogger(EntryDAO.class);
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM entrydata WHERE login=?";
    private static final String SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM reader r, entrydata e WHERE r.entryid = e.id AND e.login = ? AND e.password = ?";
    private static final String SQL_SELECT_LIBRARIAN_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM librarian r, entrydata e WHERE r.entryid = e.id AND e.login = ? AND e.password = ?";
    private ConnectionPool connector;

    public EntryDAO() {

    }

    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }

    public boolean checkEnteredData(String login, String password) throws InterruptedException, SQLException {
        boolean result = false;
        String realPassword = "z";
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN);
            ps.setString(1, login);
            logger.info(new Date() + " - " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                realPassword = rs.getString(1);
            }
            if (realPassword.equals(password)) {
                result = true;
            }

        } catch (SQLException e) {
            ErrorOutput.error = true;
            ErrorOutput.errorMessage = e.toString();
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return result;
    }

    public boolean isClient(String login, String password) throws SQLException, InterruptedException {
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            int id = -1;
            ps = connection.prepareStatement(SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD);
            ps.setString(1, login);
            ps.setString(2, password);
            logger.info(new Date() + " - " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            if (id != -1) {
                result = true;
            }
        } catch (SQLException e) {
            ErrorOutput.error = true;
            ErrorOutput.errorMessage = e.toString();
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return result;
    }

    private int getID(String login, String password, String SQLRequest) throws InterruptedException, SQLException {
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        int id = -1;
        try {
            ps = connection.prepareStatement(SQLRequest);
            ps.setString(1, login);
            ps.setString(2, password);
            logger.info(new Date() + " - " + ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            ErrorOutput.error = true;
            ErrorOutput.errorMessage = e.toString();
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return id;
    }

    public int getClientID(String login, String password) throws InterruptedException, SQLException {
        int id = 0;
        id = getID(login, password, SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }

    public int getAdminID(String login, String password) throws InterruptedException, SQLException {
        int id = 0;
        id = getID(login, password, SQL_SELECT_LIBRARIAN_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }
}
