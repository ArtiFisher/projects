package by.bsuir.admission.command.navigate;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class implements a pattern command
 * This class changes a body of main page
 * @author AndreAY
 */
public class ChangeBody extends Command {

    public static final String PARAM_PAGE = "forwardPage";
    public static final String MSG_NO_PAGE = "message.error.page.not.found";

    /**
     * This method changes a body of main page
     * The main page consists of several jsp. This method replaces a body of main page
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newBody = request.getParameter(PARAM_BODY_PAGE);
        if (newBody != null) {
            setForward(Resource.getStr(FORWARD_MAIN));
            request.setAttribute(PARAM_BODY_PAGE, newBody);
        } else {
            getMessages().addMessage(Resource.getStr(MSG_NO_PAGE));
            setForward(Resource.getStr(FORWARD_ERROR));
        }
    }
}
