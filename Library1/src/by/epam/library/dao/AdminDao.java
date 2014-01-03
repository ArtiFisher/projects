/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.library.dao;
import by.epam.library.connectionpool.ConnectionPool;
import by.epam.library.model.Admin;
import by.epam.library.model.Book;
import by.epam.library.model.Client;
import by.epam.library.model.LoginInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
/**
 *
 * @author Pasha
 */
public class AdminDao implements  AbstractDao{
    static  Logger logger = Logger.getLogger( AdminDao.class);

    private static final String SQL_SELECT_ALL_ADMINS = "SELECT * FROM admin";
    private static final String SQL_SELECT_ALL_CLIENTS = "SELECT * FROM client";
    private static final String SQL_SELECT_ID_FROM_LOGIN_INFO = "SELECT * FROM login_info WHERE (Login=?)";
    private static final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM book";
    private static final String SQL_SELECT_BOOK_BY_ID_FROM_CLIENT_CARD = "SELECT * FROM client_book WHERE id_Book=?";
    private static final String SQL_INSERT_CLIENT = "INSERT INTO client VALUES(?,?,?,?,?)";
    private static final String SQL_INSERT_LOGIN_INFO = "INSERT INTO login_info VALUES (?,?,?)";
    private static final String SQL_INSERT_BOOK =   "INSERT INTO book   VALUES(?,?,?,?,?,?)"; 
    private static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE  id_Client=?";
    private static final String SQL_DELETE_LOGININFO = "DELETE FROM login_info WHERE (id_Login_info=?)";
    private static final String SQL_DELETE_BOOK_FROM_LIBRARY = "DELETE FROM book WHERE (id_Book=?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET Number=? WHERE id_Book=?";
   

    private ConnectionPool connector;
    public AdminDao() {
    }
    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }

    public List<Admin> viewAllAdmins() throws InterruptedException, SQLException{
        List<Admin> admins = new ArrayList<Admin>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_ADMINS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(2);
                String surname = rs.getString(3);
                int age = rs.getInt(4);
                Admin admin = new Admin();
                admin.setAge(age);
                admin.setName(name);
                admin.setSurname(surname);
                admins.add(admin);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return admins;
    }
    public List<Client> viewAllClients() throws InterruptedException, SQLException{
        List<Client> clients = new ArrayList<Client>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_CLIENTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                int age = rs.getInt(4);
                Client client = new Client();
                client.setId(id);
                client.setAge(age);
                client.setName(name);
                client.setSurname(surname);
                clients.add(client);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return clients;
    }    
    public void addClient(Client client,LoginInfo li) throws InterruptedException, SQLException{
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
       try{

            int clientId=0;
            ps = connection.prepareStatement(SQL_SELECT_ID_FROM_LOGIN_INFO);
            ps.setString(1, li.getLogin());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientId = rs.getInt(1);
            }

           ps = connection.prepareStatement(SQL_INSERT_LOGIN_INFO);
           ps.setInt(1,clientId);
           ps.setString(2,li.getLogin());
           ps.setInt(3, li.getPass());
           ps.executeUpdate();

            ps = connection.prepareStatement(SQL_INSERT_CLIENT);
            ps.setInt(1, clientId);
            ps.setString(2,client.getName());
            ps.setString(3,client.getSurname());
            ps.setInt(4,client.getAge());
            ps.setInt(5, clientId);
            ps.executeUpdate();
       }
       catch (SQLException e) {
            logger.error(e);
       }
       finally {
           connector.closeConnection(connection);
           ps.close();
        }
    }
    public boolean isLoginUnique(String login) throws SQLException, InterruptedException{
        boolean uniqueLogin = true;
        int idLoginInfo = -1;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ID_FROM_LOGIN_INFO);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idLoginInfo = rs.getInt(1);
            }
            if (idLoginInfo != -1) {
                  uniqueLogin = false;
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return uniqueLogin;
    }
    public void deleteClient(int clientID) throws InterruptedException, SQLException{
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
       int idLogin_info = clientID;
        try {
            ps = connection.prepareStatement(SQL_DELETE_CLIENT);
            ps.setInt(1, clientID);
            ps.executeUpdate();

            ps = connection.prepareStatement(SQL_DELETE_LOGININFO);
            ps.setInt(1, idLogin_info);
            ps.executeUpdate();
        }
       catch (SQLException e) {
            logger.error(e);
       }
       finally {
           connector.closeConnection(connection);
           ps.close();
        }
    }
    public void addBookToLibrary(int ISBN,String title,String author,int year,int number) throws InterruptedException, SQLException{
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
        try {

            ps = connection.prepareStatement(SQL_SELECT_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();
            int idTemp =-1;
            while (rs.next()) {
                idTemp = rs.getInt(1);
            }
            idTemp += 1;

            ps = connection.prepareStatement(SQL_INSERT_BOOK);
            ps.setInt(1, idTemp);
            ps.setInt(2, ISBN);
            ps.setString(3, title);
            ps.setString(4, author);
            ps.setInt(5, year);
            ps.setInt(6, number);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
       }
       finally {
           connector.closeConnection(connection);
           ps.close();

       }
    }
    public void removeBookFromLibrary(Book book) throws InterruptedException, SQLException {
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        try {
            int numberTakenBooks = 0;
            ps = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID_FROM_CLIENT_CARD);
            ps.setInt(1, book.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numberTakenBooks++;
            }
            if (numberTakenBooks == 0) {
                ps = connection.prepareStatement(SQL_DELETE_BOOK_FROM_LIBRARY);
                ps.setInt(1, book.getId());
                ps.executeUpdate();
            }else{
                ps = connection.prepareStatement(SQL_UPDATE_BOOK);
                ps.setInt(1,0);
                ps.setInt(2, book.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
    }
}


