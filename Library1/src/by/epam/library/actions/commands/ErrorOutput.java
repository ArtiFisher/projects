package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.servlet.ServletController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ErrorOutput implements ActionCommand {

    private final static String ERROR_LOC = "/WEB-INF/jsp/error.jsp";
    public static final String ERROR = "/ServletController?method=error";

    public static boolean error=false;
    public static String errorMessage="Unknown error";

    public ResultAnswer execute(HttpServletRequest request,
                                HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        request.setAttribute("error", ErrorOutput.errorMessage);
        result.setPage(ERROR_LOC);
        return result;
    }

    public int getPageRights(){
        return -1;
    };
}
