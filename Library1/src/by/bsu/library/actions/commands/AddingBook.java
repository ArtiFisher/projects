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


public class AddingBook implements ActionCommand{

    private static final String strBookAdded = "bookAdded";
    private static  String msgBookAdded = "The book just have been added to store.";
    private static final String strToAdminJsp = "/WEB-INF/jsp/admin_jsp/for_admin.jsp";
    private static final String STR_RUS_PROPERTIES = "resources/pagecontent_ru_RU";
    private static final String STR_ENG_PROPERTIES = "resources/pagecontent_en_US";

    private static final String STR_RU_LANG1 = "ru_ru";
    private static final String STR_RU_LANG2 = "ru_RU";

    public ResultAnswer execute(HttpServletRequest request, HttpSession session1,AdminDao adm, AuthentificationDao ad, BookDao bd, ClientDao cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        ResourceBundle resource;
        if (Locale.getDefault().toString().equals(STR_RU_LANG1) || Locale.getDefault().toString().equals(STR_RU_LANG2)) {
            resource = ResourceBundle.getBundle(STR_RUS_PROPERTIES);
        } else {
            resource = ResourceBundle.getBundle(STR_ENG_PROPERTIES);
        }
        msgBookAdded = resource.getString("msgBookAdded");

        request.setAttribute(strBookAdded, msgBookAdded);
        result.setPage(strToAdminJsp);
        return result;
    }
}
