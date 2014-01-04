/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package by.epam.library.database.dao;
import by.epam.library.beans.EntryData;
import by.epam.library.beans.Librarian;
import by.epam.library.database.connectionpool.ConnectionPool;
import by.epam.library.beans.Book;
import by.epam.library.beans.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class LibrarianDAO implements  AbstractDao{
    static  Logger logger = Logger.getLogger( LibrarianDAO.class);

    private static final String SQL_SELECT_ALL_LIBRARIANS = "SELECT * FROM librarian";
    private static final String SQL_SELECT_ALL_READERS = "SELECT * FROM reader";
    private static final String SQL_SELECT_ID_FROM_ENTRYDATA = "SELECT * FROM entrydata WHERE (login=?)";
    private static final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM book";
    private static final String SQL_SELECT_BOOK_BY_ID_FROM_READER_BOOK = "SELECT * FROM reader_book WHERE bookid=?";
    private static final String SQL_INSERT_READER = "INSERT INTO reader VALUES(?,?,?,?,?)";
    private static final String SQL_INSERT_ENTRYDATA = "INSERT INTO entrydata VALUES (?,?,?)";
    private static final String SQL_INSERT_BOOK =   "INSERT INTO book VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_READER = "DELETE FROM reader WHERE id=?";
    private static final String SQL_DELETE_ENTRYDATA = "DELETE FROM entrydata WHERE (id=?)";
    private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE (id=?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET quantity=? WHERE id=?";
   

    private ConnectionPool connector;
    public LibrarianDAO() {
    }
    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }

    public List<Librarian> viewAllAdmins() throws InterruptedException, SQLException{
        List<Librarian> librarians = new ArrayList<Librarian>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_LIBRARIANS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(2);
                String surname = rs.getString(3);
                int age = rs.getInt(4);
                Librarian librarian = new Librarian();
                librarian.setAge(age);
                librarian.setName(name);
                librarian.setSurname(surname);
                librarians.add(librarian);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return librarians;
    }
    public List<Client> viewAllClients() throws InterruptedException, SQLException{
        List<Client> clients = new ArrayList<Client>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_READERS);
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
    public void addClient(Client client,EntryData li) throws InterruptedException, SQLException{
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
       try{

            int clientId=0;
            ps = connection.prepareStatement(SQL_SELECT_ID_FROM_ENTRYDATA);
            ps.setString(1, li.getLogin());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientId = rs.getInt(1);
            }

           ps = connection.prepareStatement(SQL_INSERT_ENTRYDATA);
           ps.setInt(1,clientId);
           ps.setString(2,li.getLogin());
           ps.setInt(3, li.getPass());
           ps.executeUpdate();

            ps = connection.prepareStatement(SQL_INSERT_READER);
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
            ps = connection.prepareStatement(SQL_SELECT_ID_FROM_ENTRYDATA);
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
            ps = connection.prepareStatement(SQL_DELETE_READER);
            ps.setInt(1, clientID);
            ps.executeUpdate();

            ps = connection.prepareStatement(SQL_DELETE_ENTRYDATA);
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
            ps = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID_FROM_READER_BOOK);
            ps.setInt(1, book.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numberTakenBooks++;
            }
            if (numberTakenBooks == 0) {
                ps = connection.prepareStatement(SQL_DELETE_BOOK);
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

