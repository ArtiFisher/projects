package com.fisher.tags;

import org.apache.log4j.Logger;

import java.io.IOException;


import com.fisher.database.resource.Resource;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
/**
 * This tag prints menu which allows to change language of the interface
 * @author AndreAY
 */
public class LanguageMenuTag extends TagSupport {

    public static final String CHOISE_RU = "choise.ru";
    public static final String CHOISE_EN = "choise.en";
    public static final String ALIASE_PAGE = "1";
    /**
     * This is logger which print some messages to log file
     */
    private static Logger logger = Logger.getLogger(LanguageMenuTag.class);
    /**
     * This is an address of page to on which will is realized transition
     * after change the language
     */
    private String forwardString;

    public void setForwardString(String forwardString) {
        this.forwardString = forwardString;
    }

    /**
     * This method prints menu which allows to change language of the interface
     * @return SKIP_BODY
     * @throws JspException a JspException
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(Resource.getStr(CHOISE_RU).replace(ALIASE_PAGE, forwardString));
            writer.print(Resource.getStr(CHOISE_EN).replace(ALIASE_PAGE, forwardString));
        } catch (IOException ex) {
            logger.error(ex);
        }
        return SKIP_BODY;
    }
}
