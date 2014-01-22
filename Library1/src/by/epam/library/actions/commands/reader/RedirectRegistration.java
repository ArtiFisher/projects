package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;


public class RedirectRegistration implements ActionCommand {
    private static final String strRegistration = "/WEB-INF/jsp/authorization_and_registration_jsp/registration.jsp";
    private static final String strShowReg = "/ServletController?method=showReg";
    //attributes
    private static final String atrLogin = "login";
    private static final String atrPassword = "password";
    private static final String atrID = "ID";
    private static final String atrName = "name";
    private static final String atrSurname = "surname";
    private static final String atrErrorLogin = "errorLogin";
    private static final String IS_ADMIN = "isAdmin";
    private static final String STR_RU_LANG = "ru_RU";

    private static String msgLoginExist = "Such login is exist";

    public ResultAnswer execute(HttpServletRequest request, HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        String page = "";

        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        } else {
            resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgLoginExist = resource.getString("msgLoginExist");


        String login = request.getParameter(atrLogin);
        String password = request.getParameter(atrPassword);
        String name = request.getParameter(atrName);
        String surname = request.getParameter(atrSurname);
        Reader reader = new Reader();
        reader.setName(name);
        reader.setSurname(surname);
        EntryData entryData = new EntryData();
        entryData.setLogin(login);
        entryData.setPass(password);
        if (libDAO.isLoginUnique(login) == true) {
            libDAO.addClient(reader, entryData);
            page = strShowReg;
            session.setAttribute(IS_ADMIN, -1);
            result.setPage(page);
            result.setGoToPage(false);
        } else {
            request.setAttribute(atrErrorLogin, msgLoginExist);
            page = strRegistration;
            result.setPage(page);
            session.setAttribute(IS_ADMIN, -2);

        }

        session.setAttribute("prevPage", strRegistration);
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return -1;
    }

    ;

}
