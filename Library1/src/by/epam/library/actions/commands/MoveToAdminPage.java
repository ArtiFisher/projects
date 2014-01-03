package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class MoveToAdminPage implements ActionCommand {

    private static final String strToAdminJsp = "/WEB-INF/jsp/admin_jsp/for_admin.jsp";

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        result.setPage(strToAdminJsp);
        session1.setAttribute("prevPage", strToAdminJsp);
        return result;
    }
}
