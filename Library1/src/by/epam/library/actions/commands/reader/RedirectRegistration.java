package by.epam.library.actions.commands.reader;

import by.epam.library.actions.ActionCommand;
import by.epam.library.actions.commands.ResultAnswer;
import by.epam.library.database.dao.EntryDAO;
import by.epam.library.database.dao.LibrarianDAO;
import by.epam.library.database.dao.BookDao;
import by.epam.library.database.dao.ReaderDAO;
import by.epam.library.beans.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class RedirectRegistration implements ActionCommand {
    private static final String strRegistration = "/WEB-INF/jsp/authorization_and_registration_jsp/registration.jsp";
    private static final String strShowReg = "/Library/ServletController?method=showReg";
    //attributes
    private static final String atrLogin = "login";
    private static final String atrPassword = "password";
    private static final String atrID = "ID";
    private static final String atrName = "name";
    private static final String atrSurname = "surname";
    private static final String atrErrorLogin = "errorLogin";
    private static final String atrErrorPassword = "errorPassword";
    private static final String errorAge = "errorAge";
    //messages
    private static String msgIncorrectPass = "You entered incorrect password: password can contain only figures";
    private static String msgLoginExist = "Such login is exist";

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        String page = "";

        ResourceBundle resource;
        if(Locale.getDefault().toString().equals("ru_ru") ||Locale.getDefault().toString().equals("ru_RU")){
             resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        }else{
             resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgIncorrectPass = resource.getString("msgIncorrectPass");
        msgLoginExist = resource.getString("msgLoginExist");



            String login = request.getParameter(atrLogin);
            String password = request.getParameter(atrPassword);
            String name = request.getParameter(atrName);
            String surname = request.getParameter(atrSurname);
            Reader reader = new Reader();
            reader.setName(name);
            reader.setSurname(surname);
            EntryData li = new EntryData();
            li.setLogin(login);
            li.setPass(password);
            if (adm.isLoginUnique(login) == true) {
                adm.addClient(reader, li);
                page = strShowReg;
                result.setPage(page);
                result.setIsForward(false);
            } else {
                request.setAttribute(atrErrorLogin, msgLoginExist);
                page = strRegistration;
                result.setPage(page);
                
            }

        session1.setAttribute("prevPage", strRegistration);
        return result;
    }
}
