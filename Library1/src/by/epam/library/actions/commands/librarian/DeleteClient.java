package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.Book;
import by.epam.library.beans.Reader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class DeleteClient implements ActionCommand{

    public static final String strDeleteClients = "/WEB-INF/jsp/admin_jsp/delete_client.jsp";
    public static final String strClients = "clients";
    public static final String atrIdCl = "idCl";
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1,
        LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException,SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();

        int idCl = Integer.parseInt(request.getParameter(atrIdCl));
        List<Book> books = new ArrayList<Book>();
        books.addAll(bd.viewAllClientBooks(idCl));
        for (int i = 0; i < books.size(); i++) {
            cd.returnBook(idCl, books.get(i));
        }
        adm.deleteClient(idCl);

        List<Reader> readers = new ArrayList<Reader>();
        readers.addAll(adm.viewAllClients());
        request.setAttribute(strClients, readers);
        result.setPage(strDeleteClients);
        return result;
    }

}
