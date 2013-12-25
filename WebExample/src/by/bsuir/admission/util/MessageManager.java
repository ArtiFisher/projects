package by.bsuir.admission.util;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 * This class keeps a list of messages
 * @author AndreAY
 */
public class MessageManager {

    public static final String PARAM_MESSAGE = "message";
    /**
     * This is list of messages
     */
    private ArrayList<String> messageList = new ArrayList<String>();

    /**
     *This method adds a message to messages list
     * @param message a new message
     */
    public void addMessage(String message) {
        if (message != null) {
            messageList.add(message);
        }
    }

    /**
     * This method adds messages list to request
     * @param request a HttpServletRequest
     */
    public void saveMessages(HttpServletRequest request) {
        request.setAttribute(PARAM_MESSAGE, messageList);
    }

    /**
     * This method adds all messages from new messages list to current list
     * @param messages a new messages list
     */
    public void importMessages(ArrayList<String> messages) {
        if (messages != null) {
            messageList.addAll(messages);
        }
    }
}
