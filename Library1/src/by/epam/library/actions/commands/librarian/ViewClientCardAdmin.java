package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.Book;
import by.epam.library.servlet.ServletController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;


public class ViewClientCardAdmin implements ActionCommand {

    private static final String strViewClientCard = "/WEB-INF/jsp/admin_jsp/client_card.jsp";
    private static final String strBooks = "books";
    private static final String atrIdCl = "idCl";
    private static final String bookNumber = "bookNumber";

    public ResultAnswer execute(HttpServletRequest request, HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();
        int idCl;
        idCl = Integer.parseInt(request.getParameter(atrIdCl));
        session.setAttribute(atrIdCl, idCl);

        List<Book> books = new ArrayList<Book>();
        books.addAll(bookDAO.viewAllClientBooks(idCl));

        request.setAttribute("rows", books.size());


        request.setAttribute(strBooks, books);
        result.setPage(strViewClientCard);
        session.setAttribute("prevPage", "ServletController?method=admin_view_client_card");
        request.setAttribute(bookNumber, books.size());
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    public int getPageRights() {
        return 1;
    }

    ;

}
