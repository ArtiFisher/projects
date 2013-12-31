package by.bsu.library.actions.commands;

import by.bsu.library.actions.ActionCommand;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import by.bsu.library.model.Book;
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
    private final static String strReturnBookRefr = "/Library/ServletController?method=returnBook";

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
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
        result.setPage(strReturnBookRefr);

        return result;
    }

}
