package by.bsuir.admission.util;

import by.bsuir.admission.command.CommandEnum;
import by.bsuir.admission.model.beans.User;
import javax.servlet.http.HttpServletRequest;
/**
 * This class checks whether the current user to perform this command
 * @author AndreAY
 */
public class Security {

    public static final String PARAM_USER = "user";

    /**
     * This method gets current user from request and compare his
     * permission with command type
     * @param commandType a command type
     * @param request a HttpServletRequest
     * @return true if user can execute this command
     */
    public static boolean checkPermission(CommandEnum commandType, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        switch (commandType.getUserType()) {
            case ADMIN:
                if (user != null) {
                    if (user.isIsAdmin()) {
                        return true;
                    }
                }
                break;
            case USER:
                if (user != null) {
                    return true;
                }
                break;
            case ALL:
                return true;
        }
        return false;
    }
}
