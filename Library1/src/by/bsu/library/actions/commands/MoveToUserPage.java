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


public class MoveToUserPage implements ActionCommand{

    private static final String strForUser= "/WEB-INF/jsp/user_jsp/for_user.jsp";
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        result.setPage(strForUser);
        session1.setAttribute("prevPage", strForUser);
        return result;        
    }

}
