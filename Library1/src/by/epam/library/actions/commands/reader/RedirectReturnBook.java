package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;


public class RedirectReturnBook implements ActionCommand {

    private static final String atrId = "id";
    private static final String atrID = "ID";
    private static final String strBooks = "books";
    private final static String strReturnBookRef = "/ServletController?method=returnBook";

    public ResultAnswer execute(HttpServletRequest request, HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();

        Book selectedBook = new Book();
        int id = (Integer) session.getAttribute(atrID);
        int bookId = Integer.parseInt(request.getParameter(atrId));
        selectedBook = bookDAO.selectBookByID(bookId);
        readerDAO.returnBook(id, selectedBook);

        List<Book> books = new ArrayList<Book>();
        books = bookDAO.viewAllClientBooks(id);
        request.setAttribute(strBooks, books);
        result.setGoToPage(false);
        result.setPage(strReturnBookRef);

        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return 0;
    }

    ;

}
