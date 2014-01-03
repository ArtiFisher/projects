package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
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
             resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU.properties");
        }else{
             resource = ResourceBundle.getBundle("/resources/pagecontent_en_US.properties");
        }
        //msgCongratulations = resource.getString("msgCongratulations");
        //System.out.println(msgCongratulations);
        request.setAttribute(atrSuccessfulRegistration, msgCongratulations);
        result.setPage(strAuthorization);
        session1.setAttribute("prevPage",strAuthorization);
        return result;
    }
}
