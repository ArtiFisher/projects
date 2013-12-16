package by.bsuir.admission.tags;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

/**
 * This tag prints a property from resource file by key
 * This tag chooses language on attribute <code>language</code> keeping in sessions
 * @author AndreAY
 */
public class ResourceTag extends TagSupport {

    public static final String RESOURCE_PATH = "by.bsuir.admission.resource.Resource";
    public static final String PARAM_LANGUAGE = "language";
    public static final String NO_RESOURSE = "error.no.resource";
    /**
     * This is name of property in resource file  
     */
    private String key;
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(ResourceTag.class);

    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * This method prints a property from resource file by key
     * This method chooses language on attribute <code>language</code> keeping in sessions
     * If attribute <code>language</code> not found it uses a default language
     * @return SKIP_BODY
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
        String language = (String) pageContext.getSession().getAttribute(PARAM_LANGUAGE);
        Locale locale;
        if (language != null) {
            locale = new Locale(language);
        } else {
            locale = pageContext.getRequest().getLocale();
        }
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH, locale);
        JspWriter writer = pageContext.getOut();
        try {
            try {
                writer.print(resource.getString(key));
            } catch (MissingResourceException ex) {
                logger.error(ex);
                writer.print(resource.getString(NO_RESOURSE));
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        return SKIP_BODY;
    }
}
