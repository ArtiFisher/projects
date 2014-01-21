package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.database.dao.*;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class EmptyCommand implements ActionCommand {

    private final static String NO_SUCH_COMMAND = "/WEB-INF/jsp/no_such_command.jsp";

    public ResultAnswer execute(HttpServletRequest request,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        result.setPage(NO_SUCH_COMMAND);
        return result;
    }

}
