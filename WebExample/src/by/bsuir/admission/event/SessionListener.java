package by.bsuir.admission.event;

import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 * This is Listener class
 * This class listens for events create and delete sessions
 * @author AndreAY
 */
public class SessionListener implements HttpSessionListener {

    public static final String PARAM_USER = "user";
    public static final String MSG_USER_EXIT = "logger.message.user.exit";
    public static final String MSG_UNKNOWN_EXIT = "logger.message.unknown.exit";
    public static final String MSG_UNKNOWN_ENTER = "logger.message.unknown.enter";
    /**
     * This is logger which print some messages to log file
     */
    protected static Logger logger = Logger.getLogger(SessionListener.class);

    /**
     * This method adds a message in the log file on the user logs
     * @param se a HttpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent se) {
        logger.info(Resource.getStr(MSG_UNKNOWN_ENTER));
    }

    /**
     * This method adds a message in the log file on the user logouts
     * @param se a HttpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession().getAttribute(PARAM_USER);
        if (user != null) {
            logger.info(user.getLogin() + " " + Resource.getStr(MSG_USER_EXIT));
        } else {
            logger.info(Resource.getStr(MSG_UNKNOWN_EXIT));
        }
    }
}
