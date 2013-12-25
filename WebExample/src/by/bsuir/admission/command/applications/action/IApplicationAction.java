package by.bsuir.admission.command.applications.action;

import by.bsuir.admission.util.MessageManager;
import by.bsuir.admission.xml.NoPropertyException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 * This interface contains methods for editing applications
 * @author AndreAY
 */
public interface IApplicationAction {

    /**
     * This method edits applications
     * @param request a HttpServletRequest
     * @param messages a object which keeps a list of masseges
     * @throws SQLException a SQLException
     * @throws NoPropertyException a exception if some property not found
     */
    void execute(HttpServletRequest request, MessageManager messages) throws SQLException, NoPropertyException;
}
