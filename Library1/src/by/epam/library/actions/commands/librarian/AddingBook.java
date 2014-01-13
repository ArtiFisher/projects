package by.epam.library.actions.commands.librarian;

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


public class AddingBook implements ActionCommand {

    private static final String strBookAdded = "bookAdded";
    private static String msgBookAdded = "The book just have been added to store.";
    private static final String strToAdminJsp = "/WEB-INF/jsp/admin_jsp/for_admin.jsp";
    private static final String STR_RUS_PROPERTIES = "resources/pagecontent_ru_RU";
    private static final String STR_ENG_PROPERTIES = "resources/pagecontent_en_US";

    private static final String STR_RU_LANG = "ru_RU";

    public ResultAnswer execute(HttpServletRequest request, HttpSession session1, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
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
