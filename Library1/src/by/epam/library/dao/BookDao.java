package by.epam.library.dao;

import by.epam.library.connectionpool.ConnectionPool;

import by.epam.library.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;



public class BookDao implements AbstractDao{
    static  Logger logger = Logger.getLogger( BookDao.class);

    private static final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM book";
    private static final String SQL_SELECT_BOOK_BY_TITLE = "SELECT id_Book,ISBN,Title,Author,Year,Number FROM book WHERE Title=?";
    private static final String SQL_SELECT_BOOK_BY_ID = "SELECT id_Book,ISBN,Title,Author,Year,Number FROM book WHERE id_Book=?";
    private static final String SQL_SELECT_BOOKS_ID_FROM_CLIENT_CARD = "SELECT * FROM client_book WHERE id_Client = ? ";
    private ConnectionPool connector;

    public BookDao(){
     
    }
    public void setConnector(ConnectionPool connector) {
        this.connector = connector;
    }
    public List<Book> viewAllBooks() throws InterruptedException, SQLException{
        List<Book> books = new ArrayList<Book>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();

        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_BOOKS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int ISBN = rs.getInt(2);
                String title = rs.getString(3);
                String author = rs.getString(4);
                int year = rs.getInt(5);
                int numberOfCopies = rs.getInt(6);
                Book book = new Book(id, ISBN, title, author, year, numberOfCopies);
                if(numberOfCopies!=0){
                    books.add(book);
                }
            }
            
        } catch (SQLException e) {
           logger.error(e);
        }
        finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return books;
    }
    public Book selectBookByTitle(String title) throws InterruptedException, SQLException   {
        Book book = new Book();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOK_BY_TITLE);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int ISBN = rs.getInt(2);
                String author = rs.getString(4);
                int year = rs.getInt(5);
                int numberOfCopies = rs.getInt(6);
                book = new Book(id, ISBN, title, author, year, numberOfCopies);
            }
            
        } catch (SQLException e) {
            logger.error(e);
        }
        finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return book;
    }
    public Book selectBookByID(int id) throws InterruptedException, SQLException   {
        Book book = new Book();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOK_BY_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //int id = rs.getInt(1);
                int ISBN = rs.getInt(2);
                String title = rs.getString(3);
                String author = rs.getString(4);
                int year = rs.getInt(5);
                int numberOfCopies = rs.getInt(6);
                book = new Book(id, ISBN, title, author, year, numberOfCopies);
            }

        } catch (SQLException e) {
            logger.error(e);
        }
        finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return book;
    }
    public List<Book> viewAllClientBooks(int clientID) throws InterruptedException, SQLException{
        List<Book> books = new ArrayList<Book>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();

        try {
            ps = connection.prepareStatement(SQL_SELECT_BOOKS_ID_FROM_CLIENT_CARD);
            ArrayList <Integer> booksID = new  ArrayList<Integer>();
            int bookID;
            Book book = new Book();
            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookID = rs.getInt(2);
                booksID.add(bookID);
            }

            for (int i = 0; i < booksID.size(); i++) {
                book = selectBookByID(booksID.get(i));
                books.add(book);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return books;
    }
}
