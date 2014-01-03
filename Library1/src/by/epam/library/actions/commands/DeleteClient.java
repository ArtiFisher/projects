package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
import by.epam.library.model.Book;
import by.epam.library.model.Client;
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
        AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd)
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

        List<Client> clients = new ArrayList<Client>();
        clients.addAll(adm.viewAllClients());
        request.setAttribute(strClients, clients);
        result.setPage(strDeleteClients);
        return result;
    }

}
