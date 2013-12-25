package by.bsuir.admission.controller;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.command.CommandFactory;
import by.bsuir.admission.model.action.PassMarkSetter;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class implements the pattern MVC
 * This is Servlet which handles requests
 * @author AndreAY
 */
public class ServletController extends HttpServlet {

    public static final String FORWARD_SERVLET_ERROR = "forward.servlet.error";
    public static final String RESPONSE_CONTEXT = "responce.context";
    public static final String PARAM_SYSTEM_STATE = "state";
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(ServletController.class);

    /**
     * This method inits a class <code>XMLManager</code> which can receive system settings
     * from XML file. Then method gets a system state from XML and adds state-attribute to session
     * @throws ServletException a ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        XMLManager.parse(getServletContext().getRealPath(""));
        try {
            String param = XMLManager.getFirstElement(PARAM_SYSTEM_STATE);
            getServletContext().setAttribute(PARAM_SYSTEM_STATE, param);
            new PassMarkSetter().setToAllSpecialities();
        } catch (NoPropertyException ex) {
            logger.error(ex);
        }
        catch (SQLException ex) {
            logger.error(ex);
        }
    }

    /**
     * This method handles requests
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method handles requests
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method gets a instance of <code>Command</code> from <code>CommandFactory</code>
     * by request and execute this command. Then it gos to next jsp.
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        response.setContentType(Resource.getStr(RESPONSE_CONTEXT));
        try {
            command.processRequest(request, response);
            command.getMessages().saveMessages(request);
            request.getRequestDispatcher(command.getForward()).forward(request, response);
        } catch (IOException ex) {
            logger.error(ex);
            request.getRequestDispatcher(Resource.getStr(FORWARD_SERVLET_ERROR)).forward(request, response);
        } 
    }
}
