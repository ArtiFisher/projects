package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
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
import javax.servlet.http.HttpSession;


public class RedirectReturnBook implements ActionCommand{

    private static final String atrId = "id";
    private static final String atrID = "ID";
    private static final String strBooks = "books";
    private final static String strReturnBookRef = "/Library/ServletController?method=returnBook";

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        Book selectedBook = new Book();
        HttpSession session = request.getSession(true);
        int id = (Integer) session.getAttribute(atrID);
        int bookId = Integer.parseInt(request.getParameter(atrId));
        selectedBook = bd.selectBookByID(bookId);
        cd.returnBook(id, selectedBook);

        List<Book> books = new ArrayList<Book>();
        books = bd.viewAllClientBooks(id);
        request.setAttribute(strBooks, books);
        result.setIsForward(false);
        result.setPage(strReturnBookRef);

        return result;
    }

}
