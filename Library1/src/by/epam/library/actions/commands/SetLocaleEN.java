package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SetLocaleEN implements ActionCommand {

    public ResultAnswer execute(HttpServletRequest request,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession(true);
        Locale.setDefault(new Locale("en_US"));
        session.setAttribute("loc", "en_US");
        result.setPage(session.getAttribute("prevPage").toString());
        return result;
    }

    public int getPageRights(){
        return -1;
    };

}
