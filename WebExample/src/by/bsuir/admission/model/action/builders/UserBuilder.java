package by.bsuir.admission.model.action.builders;

import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.MessageManager;
import javax.servlet.http.HttpServletRequest;

/**
 * This class gets user data from
 * request and builds a user
 * @author AndreAY
 */
public class UserBuilder {

    public static final String PATTERN_LOGIN = "pattern.login";
    public static final String PATTERN_PASSWORD = "pattern.password";
    public static final String PATTERN_NAME = "pattern.name";
    public static final String PATTERN_MAIL = "pattern.mail";
    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
    public static final String PARAM_FIRST_NAME = "firstName";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_MAIL = "mail";
    public static final String MSG_ERR_LOGIN = "error.pattern.login";
    public static final String MSG_ERR_MAIL = "error.pattern.mail";
    public static final String MSG_ERR_PASSWORD = "error.pattern.password";
    public static final String MSG_ERR_CONFIRM_PASSWORD = "error.confirm.password";
    public static final String MSG_ERR_FIRST_NAME = "error.pattern.first.name";
    public static final String MSG_ERR_SURNAME = "error.pattern.surname";
    /**
     * This is new user which  will is built
     */
    private User user = new User();
    /**
     * This object in which are written reports on incerrect data
     */
    private MessageManager messages;

    public UserBuilder(MessageManager messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    /**
     * This method builds a user
     * This method read all user properties, if each proterty
     * is successfully read, that user is successfully built
     * @param request a HttpServletRequest
     * @return <code>true</code> if user has built successfully
     */
    public boolean build(HttpServletRequest request) {
        boolean readingSuccessful = true;
        readingSuccessful &= readLogin(request);
        readingSuccessful &= readPassword(request);
        readingSuccessful &= readFirstName(request);
        readingSuccessful &= readSurname(request);
        readingSuccessful &= readMail(request);
        return readingSuccessful;
    }

    /**
     * This method gets login from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if login is correct
     */
    private boolean readLogin(HttpServletRequest request) {
        String login = request.getParameter(PARAM_LOGIN);
        if (login != null) {
            user.setLogin(login);
            if (login.matches(Resource.getStr(PATTERN_LOGIN))) {
                return true;
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_LOGIN) + " " + Resource.getStr(PATTERN_LOGIN));
        return false;
    }

    /**
     * This method gets password from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if password is correct and is equal to confirm password
     */
    private boolean readPassword(HttpServletRequest request) {
        String password = request.getParameter(PARAM_PASSWORD);
        if (password != null) {
            user.setPassword(password);
            if (password.matches(Resource.getStr(PATTERN_PASSWORD))) {
                if (password.equals(request.getParameter(PARAM_CONFIRM_PASSWORD))) {
                    return true;
                } else {
                    messages.addMessage(Resource.getStr(MSG_ERR_CONFIRM_PASSWORD));
                }
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_PASSWORD) + " " + Resource.getStr(PATTERN_PASSWORD));
        return false;
    }

    /**
     * This method gets first name from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if first name is correct
     */
    private boolean readFirstName(HttpServletRequest request) {
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        if (firstName != null) {
            user.setFirstName(firstName);
            if (firstName.matches(Resource.getStr(PATTERN_NAME))) {
                return true;
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_FIRST_NAME) + " " + Resource.getStr(PATTERN_NAME));
        return false;
    }

    /**
     * This method gets surname from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if surname is correct
     */
    private boolean readSurname(HttpServletRequest request) {
        String surname = request.getParameter(PARAM_SURNAME);
        if (surname != null) {
            user.setSurname(surname);
            if (surname.matches(Resource.getStr(PATTERN_NAME))) {
                return true;
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_SURNAME) + " " + Resource.getStr(PATTERN_NAME));
        return false;
    }

    /**
     * This method gets e-mail from request and check it
     * @param request a HttpServletRequest
     * @return <code>true</code> if e-mail is correct
     */
    private boolean readMail(HttpServletRequest request) {
        String mail = request.getParameter(PARAM_MAIL);
        if (mail != null) {
            user.setMail(mail);
            if (mail.matches(Resource.getStr(PATTERN_MAIL))) {
                return true;
            }
        }
        messages.addMessage(Resource.getStr(MSG_ERR_MAIL) + " " + Resource.getStr(PATTERN_MAIL));
        return false;
    }
}
