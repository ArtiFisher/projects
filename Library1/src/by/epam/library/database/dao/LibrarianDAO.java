package by.epam.library.database.dao;

import by.epam.library.beans.EntryData;
import by.epam.library.beans.Librarian;
import by.epam.library.database.connectionpool.ConnectionPool;
import by.epam.library.beans.Book;
import by.epam.library.beans.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import java.util.Date;

public class LibrarianDAO implements AbstractDao {
    static Logger logger = Logger.getLogger(LibrarianDAO.class);

    private static final String SQL_SELECT_ALL_LIBRARIANS = "SELECT * FROM librarian";
    private static final String SQL_SELECT_ALL_READERS = "SELECT * FROM reader";
    private static final String SQL_SELECT_BY_LOGIN_FROM_ENTRYDATA = "SELECT * FROM entrydata WHERE (login=?)";
    private static final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM book";
    private static final String SQL_SELECT_BOOK_BY_ID_FROM_READER_BOOK = "SELECT * FROM reader_book WHERE bookid=?";
    private static final String SQL_INSERT_READER = "INSERT INTO reader(name,surname,entryid) VALUES(?,?,?)";
    private static final String SQL_INSERT_ENTRYDATA = "INSERT INTO entrydata(login, password) VALUES (?,?)";
    private static final String SQL_INSERT_BOOK = "INSERT INTO book VALUES(?,?,?,?,?,?)";
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

    public List<Librarian> viewAllAdmins() throws InterruptedException, SQLException {
        List<Librarian> librarians = new ArrayList<Librarian>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_LIBRARIANS);
            logger.info(new Date() + " - " + ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString(2);
                String surname = rs.getString(3);
                Librarian librarian = new Librarian();
                librarian.setName(name);
                librarian.setSurname(surname);
                librarians.add(librarian);
            }
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return librarians;
    }

    public List<Reader> viewAllClients() throws InterruptedException, SQLException {
        List<Reader> readers = new ArrayList<Reader>();
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_ALL_READERS);
            logger.info(new Date()+" - "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                Reader reader = new Reader();
                reader.setId(id);
                reader.setName(name);
                reader.setSurname(surname);
                readers.add(reader);
            }
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return readers;
    }

    public void addClient(Reader reader, EntryData li) throws InterruptedException, SQLException {
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(SQL_INSERT_ENTRYDATA);
            ps.setString(1, li.getLogin());
            ps.setString(2, li.getPass());
            logger.info(new Date()+" - "+ps.toString());
            ps.executeUpdate();

            int entryID = 0;
            ps = connection.prepareStatement(SQL_SELECT_BY_LOGIN_FROM_ENTRYDATA);
            ps.setString(1, li.getLogin());
            logger.info(new Date()+" - "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                entryID = rs.getInt(1);
            }

            ps = connection.prepareStatement(SQL_INSERT_READER);
            ps.setString(1, reader.getName());
            ps.setString(2, reader.getSurname());
            ps.setInt(3, entryID);
            logger.info(new Date()+" - "+ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
    }

    public boolean isLoginUnique(String login) throws SQLException, InterruptedException {
        boolean uniqueLogin = true;
        int idLoginInfo = -1;
        PreparedStatement ps = null;
        Connection connection = connector.getConnection();
        try {
            ps = connection.prepareStatement(SQL_SELECT_BY_LOGIN_FROM_ENTRYDATA);
            ps.setString(1, login);
            logger.info(new Date()+" - "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                idLoginInfo = rs.getInt(1);
            }
            if (idLoginInfo != -1) {
                uniqueLogin = false;
            }
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
        return uniqueLogin;
    }

    public void deleteClient(int clientID) throws InterruptedException, SQLException {
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        int idLogin_info = clientID;
        try {
            ps = connection.prepareStatement(SQL_DELETE_READER);
            ps.setInt(1, clientID);
            logger.info(new Date()+" - "+ps.toString());
            ps.executeUpdate();

            ps = connection.prepareStatement(SQL_DELETE_ENTRYDATA);
            ps.setInt(1, idLogin_info);
            logger.info(new Date()+" - "+ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
    }

    public void addBookToLibrary(int ISBN, String title, String author, int year, int number) throws InterruptedException, SQLException {
        Connection connection = connector.getConnection();
        PreparedStatement ps = null;
        try {

            ps = connection.prepareStatement(SQL_SELECT_ALL_BOOKS);
            logger.info(new Date()+" - "+ps.toString());
            ResultSet rs = ps.executeQuery();
            int idTemp = -1;
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
            logger.info(new Date()+" - "+ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
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
            logger.info(new Date()+" - "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numberTakenBooks++;
            }
            if (numberTakenBooks == 0) {
                ps = connection.prepareStatement(SQL_DELETE_BOOK);
                ps.setInt(1, book.getId());
                logger.info(new Date()+" - "+ps.toString());
                ps.executeUpdate();
            } else {
                ps = connection.prepareStatement(SQL_UPDATE_BOOK);
                ps.setInt(1, 0);
                ps.setInt(2, book.getId());
                logger.info(new Date()+" - "+ps.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(new Date() + " - " + e);
        } finally {
            connector.closeConnection(connection);
            ps.close();
        }
    }
}


