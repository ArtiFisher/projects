package by.bsu.library.actions;

import by.bsu.library.actions.commands.ResultAnswer;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import by.bsu.library.validation.NumberValidator;
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
