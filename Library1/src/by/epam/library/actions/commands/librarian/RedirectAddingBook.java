/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

public class RedirectAddingBook implements ActionCommand {
    public static final String paramISBN = "ISBN";
    public static final String paramYear = "year";
    public static final String paramCopiesNumber = "copiesNumber";
    public static final String paramTitle = "title";
    public static final String paramAuthor = "author";
    public static final String strshowBookAdded = "/Library/ServletController?method=showBookAdded";
    public static final String strAddBook2 = "//WEB-INF/jsp/admin_jsp/add_book_to_library.jsp";

    public static final String errorISBN = "errorISBN";
    public static final String errorYear = "errorYear";
    public static final String errorNumOfCopies = "errorNumOfCopies";

    public static String msgIncorrectISBN;
    public static String msgIncorrectYear;
    public static String msgIncorrectNumOfCopies;

    public ResultAnswer execute(HttpServletRequest request, HttpSession session1, LibrarianDAO adm, EntryDAO ad, BookDao bd, ReaderDAO cd) throws InterruptedException, SQLException, ServletException, IOException {
        ResultAnswer result = new ResultAnswer();

        ResourceBundle resource;
        if (Locale.getDefault().toString().equals("ru_ru") || Locale.getDefault().toString().equals("ru_RU")) {
            resource = ResourceBundle.getBundle("resources/pagecontent_ru_RU");
        } else {
            resource = ResourceBundle.getBundle("resources/pagecontent_en_US");
        }
        msgIncorrectISBN = resource.getString("msgIncorrectISBN");
        msgIncorrectYear = resource.getString("msgIncorrectYear");
        msgIncorrectNumOfCopies = resource.getString("msgIncorrectNumOfCopies");


        if (nv.validate(request, paramISBN) && nv.validate(request, paramYear) && nv.validate(request, paramCopiesNumber)) {
            int ISBN = Integer.parseInt(request.getParameter(paramISBN));
            String title = request.getParameter(paramTitle);
            String author = request.getParameter(paramAuthor);
            int year = Integer.parseInt(request.getParameter(paramYear));
            int copiesNumber = Integer.parseInt(request.getParameter(paramCopiesNumber));
            adm.addBookToLibrary(ISBN, title, author, year, copiesNumber);
            result.setIsForward(false);
            result.setPage(strshowBookAdded);
            //session1.setAttribute("prevPage","/WEB-INF/jsp/admin_jsp/for_admin.jsp");//
            //response.sendRedirect(strshowBookAdded);
        } else {
            if (nv.validate(request, paramISBN) == false) {
                request.setAttribute(errorISBN, msgIncorrectISBN);
            }
            if (nv.validate(request, paramYear) == false) {
                request.setAttribute(errorYear, msgIncorrectYear);
            }
            if (nv.validate(request, paramCopiesNumber) == false) {
                request.setAttribute(errorNumOfCopies, msgIncorrectNumOfCopies);
            }
            result.setPage(strAddBook2);

            //request.getRequestDispatcher(strAddBook2).forward(request, response);
        }
        session1.setAttribute("prevPage", "/WEB-INF/jsp/admin_jsp/for_admin.jsp");//
        return result;
    }

}
