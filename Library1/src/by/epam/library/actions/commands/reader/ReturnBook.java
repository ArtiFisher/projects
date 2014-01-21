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

public class ReturnBook implements ActionCommand {

    private static final String strReturnBook = "/WEB-INF/jsp/user_jsp/return_book.jsp";
    private static final String atrID = "ID";
    private static final String strBooks = "books";
    private static final String bookNumber = "bookNumber";

    public ResultAnswer execute(HttpServletRequest request,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute(atrID);
        List<Book> books = new ArrayList<Book>();
        books = bd.viewAllClientBooks(id);
        request.setAttribute(strBooks, books);
        result.setPage(strReturnBook);
        request.setAttribute(bookNumber, books.size());
        return result;
    }

    public int getPageRights(){
        return 0;
    };

}
