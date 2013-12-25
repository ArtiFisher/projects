package by.bsuir.admission.command.util;

import by.bsuir.admission.command.Command;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class changes language of user interface
 * @author AndreAY
 */
public class ChangeLanguage extends Command {

    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_PAGE = "forwardPage";
    public static final String PAGE_BODY_WELCOME = "forward.body.welcome";

    /**
     * This method changes language of user interface
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = request.getParameter(PARAM_PAGE);
        String language = request.getParameter(PARAM_LANGUAGE);
        request.getSession().setAttribute(PARAM_LANGUAGE, language);
        setForward(forwardPage);
    }
}
