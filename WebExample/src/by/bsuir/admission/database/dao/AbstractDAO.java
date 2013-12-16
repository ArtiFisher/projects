package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * This abstract class holds objects needed to work with database
 * Also contains a method of closing the connection
 * @author AndreAY
 */
public abstract class AbstractDAO {

    public static final String CHARSET = "UTF-8";
    /**
     * This object stores data obtained from the database
     */
    protected ResultSet resultSet;
    /**
     * This is logger which print some messages to log file
     */
    protected Logger logger = Logger.getLogger(UserDAO.class);
    /**
     * This object  creates and closes a connection with database
     */
    protected DBConnector connector;

    /**
     * This method tries to close snachalf <code>resultSet</code> but
     * afterwards and connection
     */
    public void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        connector.close();
    }
}
