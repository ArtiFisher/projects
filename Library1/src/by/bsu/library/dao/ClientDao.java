package by.bsu.library.dao;

import by.bsu.library.connectionpool.ConnectionPool;
import by.bsu.library.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ClientDao implements AbstractDao{
    static  Logger logger = Logger.getLogger( ClientDao.class);
    private static final String SQL_SELECT_CLIENT_BY_SURNAME = "SELECT id_Client,Name,Surname,Age FROM Client WHERE Surname=?";
    private static final String SQL_SELECT_BOOK_FROM_CLIENT_BOOK = "SELECT * FROM client_book li WHERE (id_Book=?) AND (id_Client=?)";
    private static final String SQL_INSERT_INFO_TO_CLIENT_BOOK = "INSERT INTO client_book VALUES(?,?,?)";
    private static final String SQL_UPDATE_BOOK_INFO = "UPDATE book SET Number=? WHERE title=?";
    private static final String SQL_DELETE_INFO_FROM_CLIENT_BOOK = "DELETE FROM client_book WHERE (id_Book=?)AND(id_Client=?)";

    private ConnectionPool connector;
    
    public ClientDao(){
     
    }
    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }

    public Book takeBook(String surname,Book book) throws InterruptedException, SQLException{
       int clientID = 0;
       int bookID = 0;      
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
       try{
            ps = connection.prepareStatement(SQL_SELECT_CLIENT_BY_SURNAME);
            ps.setString(1, surname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientID = rs.getInt(1);
            }        
            
            book.setNumberOfCopies(book.getNumberOfCopies()-1);
            if (book.getNumberOfCopies()+1 > 0) {
               bookID = book.getId();            

               ps = connection.prepareStatement(SQL_INSERT_INFO_TO_CLIENT_BOOK);
               ps.setInt(1, 0);
               ps.setInt(2, bookID);
               ps.setInt(3, clientID);
               ps.executeUpdate();

               ps = connection.prepareStatement(SQL_UPDATE_BOOK_INFO);
               ps.setInt(1, book.getNumberOfCopies());
               ps.setString(2, book.getTitle());
               ps.executeUpdate();               
           }
       }
       catch (SQLException e) {
            logger.error(e);
       }
       finally {
           connector.closeConnection(connection);
           ps.close();
           return book;
        }
   }
    public void takeBook(int ID,Book book) throws InterruptedException, SQLException{

       int bookID = 0;
       Connection connection = connector.getConnection();
       PreparedStatement ps = null ;
       try{
            book.setNumberOfCopies(book.getNumberOfCopies()-1);
            if (book.getNumberOfCopies()+1 > 0) {
               bookID = book.getId();               

               ps = connection.prepareStatement(SQL_INSERT_INFO_TO_CLIENT_BOOK);
               ps.setInt(1, 0);
               ps.setInt(2, bookID);
               ps.setInt(3, ID);
               ps.executeUpdate();
               
               ps = connection.prepareStatement(SQL_UPDATE_BOOK_INFO);
               ps.setInt(1, book.getNumberOfCopies());
               ps.setString(2, book.getTitle());
               ps.executeUpdate();
           }
       }
       catch (SQLException e) {
            logger.error(e);
       }
       finally {
           connector.closeConnection(connection);
           ps.close();           
        }
   }
    public void returnBook(String surname, String title,Book book) throws InterruptedException, SQLException {
        int clientID = 0;
        int bookID = book.getId();
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        try {           
            ps = connection.prepareStatement(SQL_SELECT_CLIENT_BY_SURNAME);
            ps.setString(1, surname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientID = rs.getInt(1);
            }

            ps = connection.prepareStatement(SQL_DELETE_INFO_FROM_CLIENT_BOOK);
            ps.setInt(1,bookID);
            ps.setInt(2,clientID);
            ps.executeUpdate();

            ps = connection.prepareStatement(SQL_UPDATE_BOOK_INFO);//decreasing number of books
            book.setNumberOfCopies(book.getNumberOfCopies()+1);
            ps.setInt(1, book.getNumberOfCopies());
            ps.setString(2,book.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();

        }
    }
    public void returnBook(int ID,Book book) throws InterruptedException, SQLException {

        int bookID = book.getId();
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_INFO_FROM_CLIENT_BOOK);
            ps.setInt(1,bookID);
            ps.setInt(2,ID);
            ps.executeUpdate();

            ps = connection.prepareStatement(SQL_UPDATE_BOOK_INFO);
            book.setNumberOfCopies(book.getNumberOfCopies()+1);
            ps.setInt(1, book.getNumberOfCopies());
            ps.setString(2,book.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();

        }
    }
    public boolean checkBookAvailability(int idBook,int idClient) throws InterruptedException, SQLException {
        boolean avaiability = false;
        int idOrder=0;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOK_FROM_CLIENT_BOOK);
            ps.setInt(1, idBook);
            ps.setInt(2, idClient);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idOrder = rs.getInt(1);
            }
            if (idOrder != 0) {
                 avaiability = true;
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return  avaiability;
    }
}
