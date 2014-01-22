package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogOut implements ActionCommand {
    private static final String strAuthorization = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        session.setAttribute("prevPage", strAuthorization);
        result.setPage(strAuthorization);
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
