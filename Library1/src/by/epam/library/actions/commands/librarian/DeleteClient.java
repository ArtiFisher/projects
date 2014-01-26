package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
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
import javax.servlet.http.HttpServletResponse;

import by.epam.library.actions.commands.ErrorOutput;


public class DeleteClient implements ActionCommand {

    public static final String strViewClients = "/WEB-INF/jsp/admin_jsp/view_clients.jsp";
    public static final String strClients = "readers";
    public static final String atrIdCl = "idCl";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        int idCl = Integer.parseInt(request.getParameter(atrIdCl));
        List<Book> books = new ArrayList<Book>();
        books.addAll(bookDAO.viewAllClientBooks(idCl));
        for (int i = 0; i < books.size(); i++) {
            readerDAO.returnBook(idCl, books.get(i));
        }
        libDAO.deleteClient(idCl);

        List<Reader> readers = new ArrayList<Reader>();
        readers.addAll(libDAO.viewAllClients());
        request.setAttribute(strClients, readers);
        result.setPage(strViewClients);
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return 1;
    }


}
