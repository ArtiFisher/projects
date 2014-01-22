package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;

public class Registration implements ActionCommand {

    private static final String strAuthorization = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";
    private static String msgCongratulations = "Congratulations!You have just been registered!";
    private static final String atrSuccessfulRegistration = "successfulRegistration";
    private static final String STR_RUS_PROPERTIES = "resources/pagecontent_ru_RU";
    private static final String STR_ENG_PROPERTIES = "resources/pagecontent_en_US";
    private static final String STR_RU_LANG = "ru_RU";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle(STR_RUS_PROPERTIES);
        } else {
            resource = ResourceBundle.getBundle(STR_ENG_PROPERTIES);
        }
        msgCongratulations = resource.getString("msgCongratulations");
        request.setAttribute(atrSuccessfulRegistration, msgCongratulations);
        result.setPage(strAuthorization);
        session.setAttribute("prevPage", strAuthorization);
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
