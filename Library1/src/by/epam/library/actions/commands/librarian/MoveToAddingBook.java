package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
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

import by.epam.library.actions.commands.ErrorOutput;


public class MoveToAddingBook implements ActionCommand {

    private static final String strAddBook = "/WEB-INF/jsp/admin_jsp/add_book_to_library.jsp";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        ResultAnswer result = new ResultAnswer();
        result.setPage(strAddBook);
        session.setAttribute("prevPage", "ServletController?method=add_book");
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return 1;
    }

    ;

}
