package by.bsu.library.actions.commands;

import by.bsu.library.actions.ActionCommand;
import by.bsu.library.dao.AdminDao;
import by.bsu.library.dao.AuthentificationDao;
import by.bsu.library.dao.BookDao;
import by.bsu.library.dao.ClientDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Registration implements ActionCommand{

    private static final String strAuthorization = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";
    private static String msgCongratulations = "Congratulations!You have just been registered!";
    private static final String atrSuccessfulRegistration = "successfulRegistration";
    public ResultAnswer execute(HttpServletRequest request,HttpSession session1,
            AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd)
            throws InterruptedException, SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();
        ResourceBundle resource;
        if(Locale.getDefault().toString().equals("ru_ru") ||Locale.getDefault().toString().equals("ru_RU")){
             resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        }else{
             resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgCongratulations = resource.getString("msgCongratulations");

        request.setAttribute(atrSuccessfulRegistration, msgCongratulations);
        result.setPage(strAuthorization);
        session1.setAttribute("prevPage",strAuthorization);
        return result;
    }
}
