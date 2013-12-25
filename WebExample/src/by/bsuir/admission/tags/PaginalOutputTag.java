package by.bsuir.admission.tags;

import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.log4j.Logger;

/**
 * This tag outputs the collection an object splitting it on pages.
 * It also outputs menu to navigations between page
 * @author AndreAY
 */
public class PaginalOutputTag extends BodyTagSupport {

    public static final String PAGE_BEGIN = "page.begin";
    public static final String PAGE_END = "page.end";
    public static final String TAG_BEGIN = "tag.begin";
    public static final String TAG_END = "tag.end";
    public static final String PAGE_NUMBER_ALIAS = "1";
    public static final int STANDART_ELEMENT_COUNT = 10;
    /**
     * This is iterator from collection which outputs
     */
    private Iterator collection;
    /**
     * This is name of collection element. You can use it on JSP
     */
    private String var;
    /**
     * This is object for print html
     */
    private JspWriter writer;
    /**
     * This is number of current element in current page
     */
    private int currentElementNumber;
    /**
     * This is number element on page at the option of the developer
     */
    private int optionalElementCount;
    /**
     * This is number of current page
     */
    private int pageNumber;
    /**
     * This is html which outputs before each page
     */
    private String header;
    /**
     * This is html which outputs after each page
     */
    private String footer;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(PaginalOutputTag.class);

    /**
     * This method creates a iterator by a collection
     * If the collection is null then method creates a
     * iteratod by new collection
     * @param collection a output collection
     */
    public void setCollection(Collection collection) {
        if (collection != null) {
            this.collection = collection.iterator();
        } else {
            this.collection = new ArrayList().iterator();
        }
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setOptionalElementCount(int optionalElementCount) {
        this.optionalElementCount = optionalElementCount;
    }

    /**
     * This init some variable and prin header of tag
     * @return <code>EVAL_BODY_INCLUDE</code> if collection isn't empty
     * <code>SKIP_BODY</code> if collection is empty
     * @throws JspException a JspException
     */
    @Override
    public int doStartTag() throws JspException {
        if (optionalElementCount < 1) {
            optionalElementCount = STANDART_ELEMENT_COUNT;
        }
        pageNumber = 1;
        currentElementNumber = 0;
        writer = pageContext.getOut();
        printStartTag();
        if (collection.hasNext()) {
            pageContext.setAttribute(var, collection.next());
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * This method gets next element, if number of this element is equal to
     * <code>optionalElementCount</code> it finishs current page and begins new page
     * @return <code>EVAL_BODY_AGAIN</code> if iterator has got an element again
     * <code>SKIP_BODY</code> if next element isn't in iterator
     * @throws JspException a JspException
     */
    @Override
    public int doAfterBody() throws JspException {
        if (collection.hasNext()) {
            pageContext.setAttribute(var, collection.next());
            currentElementNumber++;
            if (currentElementNumber == optionalElementCount) {
                printNextPage();
            }
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    /**
     * This method prints a final part of page
     * @return EVAL_PAGE
     * @throws JspException a JspException
     */
    @Override
    public int doEndTag() throws JspException {
        try {
            writer.print(footer);
            writer.print(Resource.getStr(PAGE_END).replace(PAGE_NUMBER_ALIAS, String.valueOf(pageNumber)));
            writer.print(Resource.getStr(TAG_END));
        } catch (IOException ex) {
            logger.error(ex);
        }
        return EVAL_PAGE;
    }

    /**
     * This method prints a final part of current page and start part of next page
     */
    private void printNextPage() {
        try {
            writer.print(footer);
            writer.print(Resource.getStr(PAGE_END).replace(PAGE_NUMBER_ALIAS, String.valueOf(pageNumber)));
            currentElementNumber = 0;
            pageNumber++;
            writer.print(Resource.getStr(PAGE_BEGIN).replace(PAGE_NUMBER_ALIAS, String.valueOf(pageNumber)));
            writer.print(header);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
    /**
     * This method prints start part of tag
     */
    private void printStartTag() {
        try {
            writer.print(Resource.getStr(TAG_BEGIN));
            writer.print(Resource.getStr(PAGE_BEGIN).replace(PAGE_NUMBER_ALIAS, String.valueOf(pageNumber)));
            writer.print(header);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}

