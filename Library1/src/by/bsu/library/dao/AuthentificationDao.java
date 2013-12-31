package by.bsu.library.dao;

import by.bsu.library.connectionpool.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class AuthentificationDao implements AbstractDao{
    static  Logger logger = Logger.getLogger( AuthentificationDao.class);
    private static final String SQL_SELECT_INFO_BY_LOGIN = "SELECT Pass FROM login_info WHERE Login=?";
    private static final String SQL_SELECT_CLIENT_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM library_database.client cl, library_database.login_info li WHERE cl.id_Login_info = li.id_Login_Info AND li.Login = ? AND li.Pass = ?";
    private static final String SQL_SELECT_ADMIN_INFO_BY_LOGIN_AND_PASSWORD = "SELECT * FROM library_database.admin ad, library_database.login_info li WHERE ad.id_Login_info = li.id_Login_Info AND li.Login = ? AND li.Pass = ?";
    private ConnectionPool connector;

    public AuthentificationDao() {

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
            ps = connection.prepareStatement(SQL_SELECT_INFO_BY_LOGIN);
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
            ps = connection.prepareStatement(SQL_SELECT_CLIENT_INFO_BY_LOGIN_AND_PASSWORD);
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
        id = getID(login,password,SQL_SELECT_CLIENT_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }
    public int getAdminID(String login,int password)throws InterruptedException, SQLException{
        int id = 0;
        id = getID(login,password,SQL_SELECT_ADMIN_INFO_BY_LOGIN_AND_PASSWORD);
        return id;
    }
}
