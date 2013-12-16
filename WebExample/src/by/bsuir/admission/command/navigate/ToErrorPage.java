package by.bsuir.admission.command.navigate;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.resource.Resource;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class implements a pattern command
 * This class makes a transition to the error page
 * @author AndreAY
 */
public class ToErrorPage extends Command {

    /**
     * This is message about error
     */
    private String message;

    public ToErrorPage(String message) {
        this.message = message;
    }

    /**
     * This is method makes a transition to the error page
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getMessages().addMessage(message);
        setForward(Resource.getStr(FORWARD_ERROR));
    }
}
