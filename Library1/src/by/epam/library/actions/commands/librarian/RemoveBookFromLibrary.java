package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.library.actions.commands.ErrorOutput;


public class RemoveBookFromLibrary implements ActionCommand {

    private static final String strRemoveBook = "/WEB-INF/jsp/admin_jsp/remove_book_from_library.jsp";
    private static final String strBooks = "books";
    private static final String atrId = "id";
    private static final String bookNumber = "bookNumber";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        Book selectedBook = new Book();
        int bookId = Integer.parseInt(request.getParameter(atrId));
        selectedBook = bookDAO.selectBookByID(bookId);
        libDAO.removeBookFromLibrary(selectedBook);

        List<Book> books = new ArrayList<Book>();
        books = bookDAO.viewAllBooks();
        request.setAttribute(strBooks, books);
        result.setPage(strRemoveBook);
        request.setAttribute(bookNumber, books.size());
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
