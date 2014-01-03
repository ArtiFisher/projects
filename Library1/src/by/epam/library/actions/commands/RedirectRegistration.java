package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.dao.AdminDao;
import by.epam.library.dao.AuthentificationDao;
import by.epam.library.dao.BookDao;
import by.epam.library.dao.ClientDao;
import by.epam.library.model.*;
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
    private static final String atrAge = "age";
    private static final String atrName = "name";
    private static final String atrSurname = "surname";
    private static final String atrErrorLogin = "errorLogin";
    private static final String atrErrorPassword = "errorPassword";
    private static final String errorAge = "errorAge";
    //messages
    private static String msgIncorrectPass = "You entered incorrect password: password can contain only figures";
    private static String msgLoginExist = "Such login is exist";
    private static String msgIncorrectAge = "You entered incorrect age: this field can contain only figures";

    public ResultAnswer execute(HttpServletRequest request,HttpSession session1, AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
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
        msgIncorrectAge = resource.getString("msgIncorrectAge");



        if (nv.validate(request, atrPassword) && nv.validate(request, atrAge)) {
            String login = request.getParameter(atrLogin);
            int password = Integer.parseInt(request.getParameter(atrPassword));
            String name = request.getParameter(atrName);
            String surname = request.getParameter(atrSurname);
            int age = Integer.parseInt(request.getParameter(atrAge));
            Client client = new Client();
            client.setAge(age);
            client.setName(name);
            client.setSurname(surname);
            LoginInfo li = new LoginInfo();
            li.setLogin(login);
            li.setPass(password);
            if (adm.isLoginUnique(login) == true) {
                adm.addClient(client, li);
                page = strShowReg;
                result.setPage(page);
                result.setIsForward(false);
            } else {
                request.setAttribute(atrErrorLogin, msgLoginExist);
                page = strRegistration;
                result.setPage(page);
                
            }
        } else {
            if (nv.validate(request, atrPassword) == false) {
                request.setAttribute(atrErrorPassword, msgIncorrectPass);
            }
            if (nv.validate(request, atrAge) == false) {
                request.setAttribute(errorAge, msgIncorrectAge);
            }
            page = strRegistration;
            result.setPage(page);            
        }
        session1.setAttribute("prevPage", strRegistration);
        return result;
    }
}