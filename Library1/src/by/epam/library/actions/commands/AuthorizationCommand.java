package by.epam.library.actions.commands;

import by.epam.library.actions.ActionCommand;
import by.epam.library.database.dao.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class AuthorizationCommand implements ActionCommand {

    private static final String ATR_LOGIN = "login";
    private static final String ATR_PASSWORD = "password";
    private static final String ATR_ID = "ID";
    private static final String ATR_ERROR = "error";
    private static final String ATR_PREV_PAGE = "prevPage";
    private static final String MSG_WRONG_PASS_OR_LOGIN = "msgWrongPassOrLogin";
    private static final String MSG_INCORRECT_PASS = "msgIncorrectPass";


    private static final String STR_FOR_USER = "/WEB-INF/jsp/user_jsp/for_user.jsp";
    private static final String STR_FOR_ADMIN = "/WEB-INF/jsp/admin_jsp/for_admin.jsp";
    private static final String IS_ADMIN = "isAdmin";
    private static final String STR_AUTHORIZATION = "/WEB-INF/jsp/authorization_and_registration_jsp/authorization.jsp";
    private static final String STR_RUS_PROPERTIES = "resources/pagecontent_ru_RU";
    private static final String STR_ENG_PROPERTIES = "resources/pagecontent_en_US";

    private static final String STR_RU_LANG = "ru_RU";


    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,
                                LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd)
            throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        int clientID = -1;
        int adminID = -1;
        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle(STR_RUS_PROPERTIES);
        } else {
            resource = ResourceBundle.getBundle(STR_ENG_PROPERTIES);
        }
        String msgWrongPassOrLogin = resource.getString(MSG_WRONG_PASS_OR_LOGIN);

        String login = request.getParameter(ATR_LOGIN);
        HttpSession session = request.getSession(true);
        String password = request.getParameter(ATR_PASSWORD);
        if (ad.checkEnteredData(login, password) == true) {
            if (ad.isClient(login, password) == true) {
                clientID = ad.getClientID(login, password);
                session.setAttribute(ATR_ID, clientID);
                session.setAttribute(IS_ADMIN, 0);
                result.setPage(STR_FOR_USER);
                session1.setAttribute(ATR_PREV_PAGE, STR_FOR_USER);
            } else {
                adminID = ad.getAdminID(login, password);
                session.setAttribute(IS_ADMIN, 1);
                session.setAttribute(ATR_ID, adminID);
                result.setPage(STR_FOR_ADMIN);
                session1.setAttribute(ATR_PREV_PAGE, STR_FOR_ADMIN);
            }
        } else {
            request.setAttribute(ATR_ERROR, msgWrongPassOrLogin);
            result.setPage(STR_AUTHORIZATION);
            session1.setAttribute(ATR_PREV_PAGE, STR_AUTHORIZATION);
        }

        return result;
    }
}
