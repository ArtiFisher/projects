package by.bsuir.admission.database;

import by.bsuir.admission.resource.Resource;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
/**
 * This class creates and closes a connection with database
 * @author AndreAY
 */
public class DBConnector {

    public static final String JDBS_CONTEXT_PATH = "jdbc.context.path";
    public static final String MSG_CONNECTION_NULL = "error.connection.null";
    public static final String MSG_STATEMENT_NULL = "error.statement.null";
    /**
     * This is logger which print some messages to log file
     */
    private Logger logger = Logger.getLogger(DBConnector.class);
    /**
     * This is connection with database
     */
    private Connection connection;
    /**
     * This is statement
     */
    private Statement statement;
    /**
     * This is prepared statement
     */
    private PreparedStatement preparedStatement;

    /**
     * This method gets a connection from connection pool
     */
    public DBConnector() {
        try {
            Context initCtx = (Context) new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup(Resource.getStr(JDBS_CONTEXT_PATH));
            connection = ds.getConnection();
            statement = connection.createStatement();
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (NamingException ex) {
            logger.error(ex);
        }
    }

    /**
     * This method returns a connection
     * @return a connection
     * @throws SQLException a exception if connection is null
     */
    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        } else {
            throw new SQLException(Resource.getStr(MSG_CONNECTION_NULL));
        }
    }

    /**
     * This method returns a prepared statement
     * @return a prepared statement
     * @throws SQLException a exception if prepared statement is null
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        if (connection != null) {
            preparedStatement = connection.prepareStatement(sql);
            if (preparedStatement != null) {
                return preparedStatement;
            }
        }
        throw new SQLException(Resource.getStr(MSG_STATEMENT_NULL));
    }

    /**
     * This method returns a statement
     * @return a statement
     * @throws SQLException a exception if statement is null
     */
    public Statement getStatement() throws SQLException {
        if (connection != null) {
            statement = connection.createStatement();
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException(Resource.getStr(MSG_STATEMENT_NULL));
    }

    /**
     * This method tries to close snachalf statement and prapered statement but
     * afterwards and connection
     */
    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
    }
}
