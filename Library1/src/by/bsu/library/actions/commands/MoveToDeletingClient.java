package by.bsu.library.actions.commands;

import by.bsu.library.actions.ActionCommand;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import by.bsu.library.model.Client;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MoveToDeletingClient implements ActionCommand{

    private static final String strDeleteClients = "/WEB-INF/jsp/admin_jsp/delete_client.jsp";
    private static final String strClients = "clients";
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        List<Client> clients = new ArrayList<Client>();
        clients.addAll(adm.viewAllClients());
        request.setAttribute(strClients, clients);
        result.setPage(strDeleteClients);
        session1.setAttribute("prevPage", "ServletController?method=delete_client");

        return  result;
    }

}
