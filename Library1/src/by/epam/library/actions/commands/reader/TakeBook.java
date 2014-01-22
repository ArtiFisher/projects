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
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;


public class TakeBook implements ActionCommand {
    private static final String strTakeBook = "/WEB-INF/jsp/user_jsp/take_book.jsp";

    private static final String strBooks = "books";
    private static final String atrID = "ID";
    private static final String atrId = "id";
    private static final String atrError = "error";
    private static final String atrError2 = "error2";
    private static final String STR_RU_LANG = "ru_RU";

    private static String msgAlreadyHaveBook = "You already have this book";
    private static String msgNoMoreBooks = "There is no more copies of such book";
    private static final String bookNumber = "bookNumber";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        } else {
            resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgAlreadyHaveBook = resource.getString("msgAlreadyHaveBook");
        Book selectedBook = new Book();
        HttpSession session = request.getSession(true);
        int idCl = (Integer) session.getAttribute(atrID);
        int bookId = Integer.parseInt(request.getParameter(atrId));
        selectedBook = bookDAO.selectBookByID(bookId);
        List<Book> books = new ArrayList<Book>();
        if (readerDAO.checkBookAvailability(bookId, idCl) == false) {
            if (selectedBook.getNumberOfCopies() != 0) {
                readerDAO.takeBook(idCl, selectedBook);
                books = bookDAO.viewAllBooks();
                request.setAttribute(strBooks, books);
                result.setPage(strTakeBook);

            } else {
                books = bookDAO.viewAllBooks();
                request.setAttribute(strBooks, books);
                request.setAttribute(atrError2, msgNoMoreBooks);
                result.setPage(strTakeBook);

            }
        } else {
            books = bookDAO.viewAllBooks();
            request.setAttribute(strBooks, books);
            request.setAttribute(atrError, msgAlreadyHaveBook);
            result.setPage(strTakeBook);
        }
        request.setAttribute(bookNumber, books.size());
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
