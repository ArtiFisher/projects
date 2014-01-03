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


public class MoveToTakingBook implements ActionCommand{

    private static final String strTakeBook="/WEB-INF/jsp/user_jsp/take_book.jsp";
    private static final String strBooks = "books";
    private static final String bookNumber = "bookNumber" ;

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
       ResultAnswer result = new ResultAnswer();

        List<Book> books = new ArrayList<Book>();
        books = bd.viewAllBooks();
        request.setAttribute(strBooks, books);
        result.setPage(strTakeBook);
        request.setAttribute(bookNumber,books.size());
        session1.setAttribute("prevPage", "ServletController?method=take_book");
        return result;
    }
}
