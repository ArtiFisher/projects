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


public class MoveToRemovingBook implements ActionCommand{
    private static final String strRemoveBook = "/WEB-INF/jsp/admin_jsp/remove_book_from_library.jsp";
    private static final String strBooks = "books";
    private static final String bookNumber = "bookNumber" ;
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
       ResultAnswer result = new ResultAnswer();
       List<Book> books = new ArrayList<Book>();
       books = bd.viewAllBooks();
       request.setAttribute(strBooks, books);
       result.setPage(  strRemoveBook);
       session1.setAttribute("prevPage", "ServletController?method=remove_book_from_libarary");
       request.setAttribute(bookNumber,books.size());
       return result;
    }

}
