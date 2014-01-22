package by.epam.library.actions.commands.librarian;

import by.epam.library.actions.ActionCommand;
import by.epam.library.servlet.ServletController;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.library.actions.commands.ErrorOutput;

public class RedirectAddingBook implements ActionCommand {
    public static final String paramISBN = "ISBN";
    public static final String paramYear = "year";
    public static final String paramCopiesNumber = "copiesNumber";
    public static final String paramTitle = "title";
    public static final String paramAuthor = "author";
    public static final String strshowBookAdded = "/ServletController?method=showBookAdded";
    public static final String strAddBook2 = "//WEB-INF/jsp/admin_jsp/add_book_to_library.jsp";
    private static final String STR_RU_LANG = "ru_RU";

    public static final String errorISBN = "errorISBN";
    public static final String errorYear = "errorYear";
    public static final String errorNumOfCopies = "errorNumOfCopies";

    public static String msgIncorrectISBN;
    public static String msgIncorrectYear;
    public static String msgIncorrectNumOfCopies;

    public ResultAnswer execute(HttpServletRequest request, HttpServletResponse response, LibrarianDAO libDAO, EntryDAO entryDAO, BookDao bookDAO, ReaderDAO readerDAO) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();
        HttpSession session = request.getSession();

        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        } else {
            resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgIncorrectISBN = resource.getString("msgIncorrectISBN");
        msgIncorrectYear = resource.getString("msgIncorrectYear");
        msgIncorrectNumOfCopies = resource.getString("msgIncorrectNumOfCopies");


        if (validate(request, paramISBN) && validate(request, paramYear) && validate(request, paramCopiesNumber)) {
            int ISBN = Integer.parseInt(request.getParameter(paramISBN));
            String title = request.getParameter(paramTitle);
            String author = request.getParameter(paramAuthor);
            int year = Integer.parseInt(request.getParameter(paramYear));
            int copiesNumber = Integer.parseInt(request.getParameter(paramCopiesNumber));
            libDAO.addBookToLibrary(ISBN, title, author, year, copiesNumber);
            result.setGoToPage(false);
            result.setPage(strshowBookAdded);
            //session1.setAttribute("prevPage","/WEB-INF/jsp/admin_jsp/for_admin.jsp");//
            //response.sendRedirect(strshowBookAdded);
        } else {
            if (validate(request, paramISBN) == false) {
                request.setAttribute(errorISBN, msgIncorrectISBN);
            }
            if (validate(request, paramYear) == false) {
                request.setAttribute(errorYear, msgIncorrectYear);
            }
            if (validate(request, paramCopiesNumber) == false) {
                request.setAttribute(errorNumOfCopies, msgIncorrectNumOfCopies);
            }
            result.setPage(strAddBook2);

            //request.getRequestDispatcher(strAddBook2).forward(request, response);
        }
        session.setAttribute("prevPage", "/WEB-INF/jsp/admin_jsp/for_admin.jsp");//
        if (ErrorOutput.error) {

            ErrorOutput.error = false;
            result.setPage(ErrorOutput.ERROR);
        }
        return result;
    }

    private boolean validate(HttpServletRequest request, String paramForValidation) {
        boolean result = true;
        try {
            Integer.parseInt(request.getParameter(paramForValidation));
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        } finally {
            return result;
        }
    }

    public int getPageRights() {
        return 1;
    }

    ;

}
