package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class MoveToRegistration implements ActionCommand {
    private static final String strRegistration = "/WEB-INF/jsp/authorization_and_registration_jsp/registration.jsp";

    public ResultAnswer execute(HttpServletRequest request, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        result.setPage(strRegistration);
        session.setAttribute("prevPage", strRegistration);
        return result;
    }

    public int getPageRights(){
        return -1;
    };

}
