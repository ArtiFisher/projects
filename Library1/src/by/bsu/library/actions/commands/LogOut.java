package by.bsu.library.actions.commands;

import by.bsu.library.actions.ActionCommand;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LogOut implements ActionCommand{
    private static final String strAuthorization = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";
    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,
            AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd)
            throws InterruptedException, SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();
        session1.setAttribute("prevPage", strAuthorization);
        result.setPage(strAuthorization);
        return result;
    }

}
