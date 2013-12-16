package by.bsuir.admission.mail;

import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.xml.NoPropertyException;
import java.util.ArrayList;

/**
 * This thear send e-mails to many users
 * @author AndreAY
 */
public class SenderThread extends Thread {

    public static final String MSG_SBJ = "mail.subject";
    public static final String MSG_HELL0 = "jsp.message.welcome";
    /**
     * This is list of users to which will sends e-mail
     */
    private ArrayList<User> userList;
    /**
     * This is mail content
     */
    private String content;
    /**
     * This object sends mail
     */
    private MailSender mailSender;

    public SenderThread(ArrayList<User> userList, String content) throws NoPropertyException {
        mailSender = new MailSender();
        this.userList = userList;
        this.content = content;
    }

    /**
     * This method runs the thread and sends mail to each user in list
     */
    @Override
    public void run() {
        mailSender.setMailSubject(Resource.getStr(MSG_SBJ));
        for (User user : userList) {
            StringBuilder message = new StringBuilder(Resource.getStr(MSG_HELL0));
            message.append(" " + user.getFirstName() + " " + user.getSurname() + "\n");
            message.append(content);
            mailSender.setMailTo(user.getMail());
            mailSender.setMailContent(message.toString());
            mailSender.send();
        }
    }
}


