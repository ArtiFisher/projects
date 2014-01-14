package by.epam.library.servlet;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.ActionFactory;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.connectionpool.ConnectionPool;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class ServletController extends HttpServlet {
    private static final String LOG4J_PATH = "C:\\Users\\Artem\\IdeaProjects\\Library1\\src\\log4j.xml";

    static {
        new DOMConfigurator().doConfigure(LOG4J_PATH, LogManager.getLoggerRepository());
    }

    static Logger logger = Logger.getLogger(ServletController.class);

    private static final String FLAG = "flag";
    private static final String PREV_PAGE = "prevPage";
    private static final String LOCALE = "loc";
    private static final String LOCALE_EN = "en_US";
    private static final String LOCALE_RU = "ru_RU";
    private static final String CONTEXT_TYPE = "text/html";
    private static final String AUTHORIZATION_JSP = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";
    private static final String REGISTRATION_JSP = "/WEB-INF/jsp/authorization_and_registration_jsp/registration.jsp";
    private static final String strShowReg = "/ServletController?method=showReg";
    private static final String STR_FOR_USER = "/WEB-INF/jsp/user_jsp/for_user.jsp";
    private static final String STR_FOR_ADMIN = "/WEB-INF/jsp/admin_jsp/for_admin.jsp";
    private static final String IS_ADMIN = "isAdmin";
    private ConnectionPool connector;
    private LibrarianDAO adm;
    private EntryDAO ad;
    private BookDao bd;
    private ReaderDAO cd;
    private int poolSize = 20;

    public ServletController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            connector = new ConnectionPool(poolSize);
            adm = new LibrarianDAO();
            adm.setConnector(connector);
            ad = new EntryDAO();
            ad.setConnector(connector);
            bd = new BookDao();
            bd.setConnector(connector);
            cd = new ReaderDAO();
            cd.setConnector(connector);
        } catch (SQLException ex) {
            logger.error(ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
        }
    }

    /**
     * handles requests, sends forward to necessary page
     */
    private void requestProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InterruptedException, SQLException {
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);     //defines command from parameter 'method' of request with help of CommandEnum.java
        ResultAnswer result;                                       //container for address of next page
        HttpSession session = request.getSession();
        try {
            Integer.parseInt(session.getAttribute(FLAG).toString());      //exception is thrown if user just entered, because flag is not set
        } catch (NullPointerException e) {                                //setup of default attributes for session
            session.removeAttribute(PREV_PAGE);
            session.setAttribute(FLAG, 1);
            session.setAttribute(LOCALE, LOCALE_RU);
            Locale.setDefault(new Locale("ru_RU"));
        }
        try {
            result = command.execute(request, session, adm, ad, bd, cd);    //executes current command
            if (result.isGoToPage()) {
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
