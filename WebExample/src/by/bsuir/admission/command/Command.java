package by.bsuir.admission.command;

import by.bsuir.admission.util.MessageManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class implements a pattern command
 * This class causes methods a business-logic and sends results on jsp
 * @author AndreAY
 */
public abstract class Command {

    public static final String MSG_DATABASE_ERROR = "error.database.fail";
    public static final String FORWARD_ERROR = "forward.error";
    public static final String FORWARD_MAIN = "forward.main";
    public static final String PARAM_BODY_PAGE = "bodyPage";
    /**
     * This address to which will is realized transition after performing the command
     */
    private String forward;
    /**
     * This object contains a list of messages which will print to jsp
     */
    private MessageManager messages = new MessageManager();
    /**
     * This is logger which print some messages to log file
     */
    protected static Logger logger = Logger.getLogger(Command.class);

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public void setMessages(MessageManager messages) {
        this.messages = messages;
    }

    public MessageManager getMessages() {
        return messages;
    }

    /**
     * This is abstract method which causes methods a business-logic and sends results on jsp
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    public abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
