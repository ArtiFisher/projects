package by.epam.library.tags;

import by.epam.library.beans.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ClientCardTag extends TagSupport {

    private ArrayList<Book> books;
    private static final String STR_RUS_PROPERTIES = "resources/pagecontent_ru_RU";
    private static final String STR_ENG_PROPERTIES = "resources/pagecontent_en_US";
    private static final String STR_RU_LANG = "ru_RU";


    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public int doStartTag() throws JspException {

        ResourceBundle resource;
        if (Locale.getDefault().toString().equalsIgnoreCase(STR_RU_LANG)) {
            resource = ResourceBundle.getBundle(STR_RUS_PROPERTIES);
        } else {
            resource = ResourceBundle.getBundle(STR_ENG_PROPERTIES);
        }


        try {
            JspWriter out = pageContext.getOut();
            out.write("<table border='1' align=\"center\">");
            out.write("<td>" + resource.getString("book_id") + "</td>");
            out.write("<td>" + resource.getString("book_ISBN") + "</td>");
            out.write("<td>" + resource.getString("book_title") + "</td>");
            out.write("<td>" + resource.getString("book_author") + "</td>");
            out.write("<td>" + resource.getString("book_year") + "</td>");
            out.write("<td>" + resource.getString("book_numberOfCopies") + "</td>");
            for (int i = 0; i < books.size(); i++) {
                out.write("<tr>");
                out.write("<td>" + books.get(i).getId() + "</td>");
                out.write("<td>" + books.get(i).getISBN() + "</td>");
                out.write("<td>" + books.get(i).getTitle() + "</td>");
                out.write("<td>" + books.get(i).getAuthor() + "</td>");
                out.write("<td>" + books.get(i).getYear() + "</td>");
                out.write("<td>" + books.get(i).getNumberOfCopies() + "</td>");
                out.write("</tr>");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientCardTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return EVAL_BODY_INCLUDE;
    }


    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</table>");
        } catch (IOException ex) {
            Logger.getLogger(ClientCardTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return EVAL_PAGE;
    }
}
