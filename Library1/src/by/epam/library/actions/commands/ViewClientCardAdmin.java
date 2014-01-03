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


public class ViewClientCardAdmin implements ActionCommand{

    private static final String strViewClientCard = "/WEB-INF/jsp/admin_jsp/client_card.jsp";
    private static final String strBooks = "books";
    private static final String atrIdCl = "idCl";
    private static final String bookNumber = "bookNumber" ;

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        int idCl;
        try{
            idCl = Integer.parseInt(request.getParameter(atrIdCl));
            session1.setAttribute(atrIdCl, idCl);
        }catch(NumberFormatException e){
            idCl = Integer.parseInt(session1.getAttribute(atrIdCl).toString());
        }
        
        List<Book> books = new ArrayList<Book>();
        books.addAll(bd.viewAllClientBooks(idCl));

        request.setAttribute("rows",books.size());
        

        request.setAttribute(strBooks, books);
        result.setPage(strViewClientCard);
        session1.setAttribute("prevPage","ServletController?method=admin_view_client_card");
        request.setAttribute(bookNumber,books.size());
        return result;
    }

}
