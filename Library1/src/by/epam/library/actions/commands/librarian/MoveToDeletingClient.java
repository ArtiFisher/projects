package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.Reader;

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
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        List<Reader> readers = new ArrayList<Reader>();
        readers.addAll(adm.viewAllClients());
        request.setAttribute(strClients, readers);
        result.setPage(strDeleteClients);
        session1.setAttribute("prevPage", "ServletController?method=delete_client");

        return  result;
    }

}
