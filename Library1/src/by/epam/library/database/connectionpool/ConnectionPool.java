package by.epam.library.database.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import by.epam.library.servlet.ServletController;
import org.apache.log4j.Logger;
import by.epam.library.actions.commands.ErrorOutput;

public class ConnectionPool {


    private BlockingQueue<Connection> connectionQueue;
    private Logger log = Logger.getLogger(ConnectionPool.class);
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String RESOURCE_DB = "resources/database";
    private static final String CHARACTER_ENCODING = "characterEncoding";
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String UTF_8 = "UTF-8";
    private static final String WARN1 = "Database access error";
    private static final String WARN2 = "Returning connection not added. Possible `leakage` of connections";
    private static final String WARN3 = "Trying to return closed connection. Possible `leakage` of connections";
    private static final String WARN4 = "SQL exception.";


    public ConnectionPool(int poolSize) throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_NAME);
        connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_DB);
        String url = resource.getString(URL);
        String user = resource.getString(USER);
        String pass = resource.getString(PASSWORD);

        Properties prop = new Properties();
        prop.put(USER, user);
        prop.put(PASSWORD, pass);
        prop.put(CHARACTER_ENCODING, UTF_8);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = DriverManager.getConnection(url, prop);
            connectionQueue.offer(connection);
        }
    }


    public Connection getConnection() throws InterruptedException {
        Connection connection = null;
        connection = connectionQueue.take();
        return connection;
    }


    public void closeConnection(Connection connection) {
        boolean closed = true;
        try {
            closed = connection.isClosed();
        } catch (SQLException e) {
            ErrorOutput.error=true;
            ErrorOutput.errorMessage=e.toString();
            log.warn(WARN1, e);
        }
        if (!closed) {
            if (!connectionQueue.offer(connection)) {
                log.warn(WARN2);
            }
        } else {
            log.warn(WARN3);
        }
    }

    public void dispose() {
        Connection connection;
        while ((connection = connectionQueue.poll()) != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            } catch (SQLException e) {
                ErrorOutput.error=true;
                ErrorOutput.errorMessage=e.toString();
                log.warn(WARN4, e);
            }
        }
    }
} 