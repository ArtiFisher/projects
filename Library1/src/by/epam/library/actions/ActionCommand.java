package by.epam.library.actions;

import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
import by.epam.library.validation.NumberValidator;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ActionCommand {
    public NumberValidator nv = new NumberValidator();
    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,
            AdminDao adm,AuthentificationDao ad,BookDao bd,ClientDao cd)
    throws InterruptedException, SQLException, ServletException, IOException ;
}
