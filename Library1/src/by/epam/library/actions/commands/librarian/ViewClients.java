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


public class ViewClients implements ActionCommand {

    private static final String strViewClients = "/WEB-INF/jsp/admin_jsp/view_clients.jsp";
    private static final String strClients = "readers";

    public ResultAnswer execute(HttpServletRequest request,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        List<Reader> readers = new ArrayList<Reader>();
        readers.addAll(adm.viewAllClients());
        request.setAttribute(strClients, readers);
        result.setPage(strViewClients);
        session.setAttribute("prevPage", "ServletController?method=view_clients");
        return result;
    }

    public int getPageRights(){
        return 1;
    };

}
