package by.epam.library.actions.commands.librarian;

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


public class MoveToAddingBook implements ActionCommand{

    private static final String strAddBook = "/WEB-INF/jsp/admin_jsp/add_book_to_library.jsp";
    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,
            LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();
        result.setPage(strAddBook);
        session1.setAttribute("prevPage", "ServletController?method=add_book");
        return result;
    }

}