package by.bsuir.admission.command;

import by.bsuir.admission.util.UserTypeEnum;
/**
 * This enum contains enumeration of all commands
 * @author AndreAY
 */
public enum CommandEnum {
    UNKNOWN_COMMAND (UserTypeEnum.ALL), TO_PAGE (UserTypeEnum.ALL), AUTHORIZATION (UserTypeEnum.ALL),
    CHANGE_LANGUAGE (UserTypeEnum.ALL), REGISTRATION(UserTypeEnum.ALL), CHANGE_BODY(UserTypeEnum.USER),
    ADD_CERTIFICATE(UserTypeEnum.USER),EXIT (UserTypeEnum.ALL), LOAD_UNIVERSITIES(UserTypeEnum.USER),
    LOAD_FACULTIES(UserTypeEnum.USER),LOAD_SPECIALITIES(UserTypeEnum.USER), ADD_APPLICATION(UserTypeEnum.USER),
    VIEW_APPLICATIONS(UserTypeEnum.USER), VIEW_SPECIALITY_APPLICATIONS(UserTypeEnum.ADMIN),
    DELETE_SPECIALITY_BY_ADMIN(UserTypeEnum.ADMIN),DELETE_APPLICATION_BY_USER(UserTypeEnum.USER),
    CONFIRM_APPLICATIONS(UserTypeEnum.USER), END_FILING_APPLICATIONS(UserTypeEnum.ADMIN),
    SEND_WARNING(UserTypeEnum.ADMIN), AUTO_CONFIRM(UserTypeEnum.ADMIN), NO_CONFIRMED_USERS(UserTypeEnum.ADMIN);

    /**
     * This is instance which defines what users can execute this command
     */
    private UserTypeEnum userType;

    private CommandEnum(UserTypeEnum userType) {
        this.userType = userType;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }
}
