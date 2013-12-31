/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsu.library.servlet;

import by.bsu.library.actions.ActionCommand;
import by.bsu.library.actions.ActionFactory;
import by.bsu.library.actions.commands.ResultAnswer;
import by.bsu.library.connectionpool.ConnectionPool;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Pasha
 */
public class ServletController extends HttpServlet {
    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }
    static Logger logger = Logger.getLogger(ServletController.class);

    private static final String FLAG = "flag";
    private static final String PREV_PAGE = "prevPage";
    private static final String LOCALE = "loc";
    private static final String LOCALE_RUS= "ru_RU";
    private static final String CONTEXT_TYPE = "text/html";
    private static final String AUTHORIZATION_JSP = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";

    private ConnectionPool connector;
    private AdminDao adm;
    private AuthentificationDao ad;
    private BookDao bd;
    private ClientDao cd;
    private int poolSize = 20;
    public ServletController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            connector = new ConnectionPool(poolSize);
            adm = new AdminDao();
            adm.setConnector(connector);
            ad = new AuthentificationDao();
            ad.setConnector(connector);
            bd = new BookDao();
            bd.setConnector(connector);
            cd = new ClientDao();
            cd.setConnector(connector);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        }
    }

  

    private void requestProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InterruptedException, SQLException {
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        try{
            Integer.parseInt(session.getAttribute(FLAG).toString());
        }catch(NullPointerException e){
            session.removeAttribute(PREV_PAGE);
            session.setAttribute(FLAG,1);
            session.setAttribute(LOCALE, LOCALE_RUS);
        }     
        try {
            result = command.execute(request, session, adm, ad, bd, cd);
            if (result.isIsForward()) {               
                response.setContentType(CONTEXT_TYPE);
                request.getRequestDispatcher(result.getPage()).forward(request, response);
            } else {
                response.sendRedirect(result.getPage());
            }
        } catch (NullPointerException e) {
            request.getRequestDispatcher(AUTHORIZATION_JSP).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {            
            requestProcessing(request, response);
        } catch (InterruptedException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            requestProcessing(request, response);

        } catch (InterruptedException ex) {
            logger.error(ex);
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
