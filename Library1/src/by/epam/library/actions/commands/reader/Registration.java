package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;

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
            LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException
    {
        ResultAnswer result = new ResultAnswer();
        ResourceBundle resource;
        if(Locale.getDefault().toString().equals("ru_ru") ||Locale.getDefault().toString().equals("ru_RU")){
             resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU.properties");
        }else{
             resource = ResourceBundle.getBundle("/resources/pagecontent_en_US.properties");
        }
        msgCongratulations = resource.getString("msgCongratulations");
        request.setAttribute(atrSuccessfulRegistration, msgCongratulations);
        result.setPage(strAuthorization);
        session1.setAttribute("prevPage",strAuthorization);
        return result;
    }
}
