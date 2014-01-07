package by.epam.library.database.dao;

import by.epam.library.database.connectionpool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class EntryDAO implements AbstractDao{
    static  Logger logger = Logger.getLogger( EntryDAO.class);
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM entrydata WHERE login=?";
    private static final String SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM reader r, entrydata e WHERE r.entryid = e.id AND e.login = ? AND e.password = ?";
    private static final String SQL_SELECT_LIBRARIAN_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM librarian r, entrydata e WHERE r.entryid = e.id AND e.login = ? AND e.password = ?";
    private ConnectionPool connector;

    public EntryDAO() {

    }

    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }

    public boolean checkEnteredData(String login,int password) throws InterruptedException, SQLException{
        boolean result=false;        
        int realPassword = 0;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               realPassword  = rs.getInt(1);
            }
            if(realPassword == password){
                result = true;
            }

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return result;
    }
    public boolean  isClient(String login,int password) throws SQLException, InterruptedException{
        boolean result = false;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            int id = -1;
            ps = connection.prepareStatement(SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD);
            ps.setString(1, login);
            ps.setInt(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              id  = rs.getInt(1);
            }
            if(id != -1){
                result = true;
            }
        }
        catch (SQLException e) {
           logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return result;
    }
    private int getID(String login,int password,String SQLRequest) throws InterruptedException, SQLException{
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        int id = -1;
        try {
            ps = connection.prepareStatement(SQLRequest);
            ps.setString(1, login);
            ps.setInt(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              id  = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return id;
    }

    public int getClientID(String login,int password) throws InterruptedException, SQLException{
        int id = 0;
        id = getID(login,password,SQL_SELECT_READER_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }
    public int getAdminID(String login,int password)throws InterruptedException, SQLException{
        int id = 0;
        id = getID(login,password,SQL_SELECT_LIBRARIAN_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }
}
