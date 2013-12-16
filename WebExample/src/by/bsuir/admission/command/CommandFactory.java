package by.bsuir.admission.command;

import by.bsuir.admission.command.applications.AddApplication;
import by.bsuir.admission.command.applications.UserApplications;
import by.bsuir.admission.command.applications.SpecialityApplications;
import by.bsuir.admission.command.applications.action.ConfirmApplication;
import by.bsuir.admission.command.applications.action.DeleteApplication;
import by.bsuir.admission.command.applications.action.DeleteApplicationByAdmin;
import by.bsuir.admission.command.applications.action.LoadApplications;
import by.bsuir.admission.command.authorization.Authorization;
import by.bsuir.admission.command.authorization.Registration;
import by.bsuir.admission.command.certificates.AddCertificate;
import by.bsuir.admission.command.navigate.ToPage;
import by.bsuir.admission.command.navigate.ToErrorPage;
import by.bsuir.admission.command.specialities.LoadSpecialities;
import by.bsuir.admission.command.navigate.ChangeBody;
import by.bsuir.admission.command.navigate.Exit;
import by.bsuir.admission.command.specialities.LoadFaculties;
import by.bsuir.admission.command.specialities.LoadUniversities;
import by.bsuir.admission.command.adminaction.EndFilingApplication;
import by.bsuir.admission.command.adminaction.AutoConfirmApplications;
import by.bsuir.admission.command.adminaction.NoConfirmedUsers;
import by.bsuir.admission.command.adminaction.SendWarning;
import by.bsuir.admission.command.util.ChangeLanguage;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.Security;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * This is class which implements a pattern factory
 * This class get a commandName from request, checks this name
 * and return a instance of class <code>Command</code>
 * @author AndreAY
 */
public class CommandFactory {

    public static final String PARAM_COMMAND = "commandName";
    public static final String MSG_ACCESS_DENIED = "error.access.denied";
    public static final String MSG_ILLEGAL_COMMAND = "error.illegal.command";
    public static final String MSG_NOT_SUPPORTED_COMMAND = "error.not.supported.command";
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    /**
     * This method get a commandName from request, checks this name and return
     * a instance of class <code>Command</code>
     * @param request a HttpServletRequest
     * @return a new <code>Command</code>, this a instance of class which implements
     * a pattern command
     */
    public static Command getCommand(HttpServletRequest request) {
        Command command = null;
        CommandEnum commandType = getCommandEnum(request.getParameter(PARAM_COMMAND));
        if (Security.checkPermission(commandType, request)) {
            switch (commandType) {
                case NO_CONFIRMED_USERS:
                    command = new NoConfirmedUsers();
                    break;
                case AUTO_CONFIRM:
                    command = new AutoConfirmApplications();
                    break;
                case SEND_WARNING:
                    command = new SendWarning();
                    break;
                case END_FILING_APPLICATIONS:
                    command = new EndFilingApplication();
                    break;
                case CONFIRM_APPLICATIONS:
                    command = new UserApplications(new ConfirmApplication());
                    break;
                case DELETE_APPLICATION_BY_USER:
                    command = new UserApplications(new DeleteApplication());
                    break;
                case DELETE_SPECIALITY_BY_ADMIN:
                    command = new SpecialityApplications(new DeleteApplicationByAdmin());
                    break;
                case VIEW_SPECIALITY_APPLICATIONS:
                    command = new SpecialityApplications(new LoadApplications());
                    break;
                case VIEW_APPLICATIONS:
                    command = new UserApplications(new LoadApplications());
                    break;
                case ADD_APPLICATION:
                    command = new AddApplication();
                    break;
                case LOAD_UNIVERSITIES:
                    command = new LoadUniversities();
                    break;
                case LOAD_FACULTIES:
                    command = new LoadFaculties();
                    break;
                case LOAD_SPECIALITIES:
                    command = new LoadSpecialities();
                    break;
                case EXIT:
                    command = new Exit();
                    break;
                case CHANGE_BODY:
                    command = new ChangeBody();
                    break;
                case ADD_CERTIFICATE:
                    command = new AddCertificate();
                    break;
                case AUTHORIZATION:
                    command = new Authorization();
                    break;
                case REGISTRATION:
                    command = new Registration();
                    break;
                case TO_PAGE:
                    command = new ToPage();
                    break;
                case CHANGE_LANGUAGE:
                    command = new ChangeLanguage();
                    break;
                case UNKNOWN_COMMAND:
                    command = new ToErrorPage(Resource.getStr(MSG_ILLEGAL_COMMAND));
                    break;
                default:
                    command = new ToErrorPage(Resource.getStr(MSG_NOT_SUPPORTED_COMMAND));
                    break;
            }
        } else {
            command = new ToErrorPage(Resource.getStr(MSG_ACCESS_DENIED));
        }
        return command;
    }

    /**
     * This method creats a instance of <code>CommandEnum</code> by a command name
     * If command name is incorrect then <code>CommandEnum.UNKNOWN_COMMAND</code> will return
     * @param commandName a name of command
     * @return a instance of <code>CommandEnum</code>
     */
    private static CommandEnum getCommandEnum(String commandName) {
        CommandEnum commandEnum = null;
        try {
            commandEnum = CommandEnum.valueOf(commandName);
        } catch (IllegalArgumentException ex) {
            commandEnum = CommandEnum.UNKNOWN_COMMAND;
            logger.error(ex);
        } catch (NullPointerException ex) {
            commandEnum = CommandEnum.UNKNOWN_COMMAND;
            logger.error(ex);
        }
        return commandEnum;
    }
}
