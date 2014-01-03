package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
import by.epam.library.model.Book;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class MoveToReturningBook implements ActionCommand{

    private static final String atrID = "ID";
    private static final String strReturnBook = "/WEB-INF/jsp/user_jsp/return_book.jsp";
    private static final String strBooks = "books";
    private static final String bookNumber = "bookNumber" ;


    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        List<Book> books = new ArrayList<Book>();
        HttpSession session = request.getSession(true);
        int id = (Integer) session.getAttribute(atrID);
        books.addAll(bd.viewAllClientBooks(id));
        request.setAttribute(strBooks, books);
        request.setAttribute(bookNumber,books.size());
        result.setPage(strReturnBook);
        
        session1.setAttribute("prevPage", "ServletController?method=return_book");
        return result;
    }

}