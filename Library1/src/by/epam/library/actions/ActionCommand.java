package by.epam.library.actions;

import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.validation.NumberValidator;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ActionCommand {
    public NumberValidator nv = new NumberValidator();

    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException;
}
