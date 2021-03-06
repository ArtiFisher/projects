package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ErrorOutput;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.beans.Book;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.ReaderDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class Search implements ActionCommand {
    private static final String strTakeBook = "/WEB-INF/jsp/user_jsp/take_book.jsp";
    private static final String strBooks = "books";
    private static final String bookNumber = "bookNumber";
    private static final String search = "search";

    public ResultAnswer execute(HttpServletRequest request, HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        String pattern = request.getParameter(search);
        List<Book> books = new ArrayList<Book>();
        books = bookDAO.search(pattern);
        request.setAttribute(strBooks, books);
        result.setPage(strTakeBook);
        request.setAttribute(bookNumber, books.size());
        session.setAttribute("prevPage", "ServletController?method=take_book");
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return 0;
    }

}
