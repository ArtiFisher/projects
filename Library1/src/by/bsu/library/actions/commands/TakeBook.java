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
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class TakeBook implements ActionCommand{
    private static final String strTakeBook="/WEB-INF/jsp/user_jsp/take_book.jsp";

    private static final String strBooks = "books";
    private static final String atrID = "ID";
    private static final String atrId = "id";
    private static final String atrError = "error";
    private static final String atrError2 = "error2";
    //messages
    private static String msgAlreadyHaveBook ="You already have this book";
    private static String msgNoMoreBooks = "There is no more copies of such book";
    private static final String bookNumber = "bookNumber" ;

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1,
            AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd)
            throws InterruptedException, SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();
        ResourceBundle resource;
        if(Locale.getDefault().toString().equals("ru_ru") ||Locale.getDefault().toString().equals("ru_RU")){
             resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        }else{
             resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgAlreadyHaveBook = resource.getString("msgAlreadyHaveBook");
        Book selectedBook = new Book();
        HttpSession session = request.getSession(true);
        int idCl = (Integer) session.getAttribute(atrID);
        int bookId = Integer.parseInt(request.getParameter(atrId));
        selectedBook = bd.selectBookByID(bookId);
        List<Book> books = new ArrayList<Book>();
        if (cd.checkBookAvailability(bookId, idCl) == false) {
            if (selectedBook.getNumberOfCopies() != 0) {
                cd.takeBook(idCl, selectedBook);
                books = bd.viewAllBooks();
                request.setAttribute(strBooks, books);
                result.setPage(strTakeBook);
                
            } else {                
                books = bd.viewAllBooks();
                request.setAttribute(strBooks, books);                
                request.setAttribute(atrError2, msgNoMoreBooks);
                result.setPage(strTakeBook);
                
            }
        } else {
            books = bd.viewAllBooks();
            request.setAttribute(strBooks, books);            
            request.setAttribute(atrError, msgAlreadyHaveBook);
            result.setPage(strTakeBook);            
        }
        request.setAttribute(bookNumber,books.size());        
        return result;
    }
}
