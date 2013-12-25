package by.bsuir.admission.command.adminaction;

import by.bsuir.admission.command.Command;
import by.bsuir.admission.resource.Resource;
import by.bsuir.admission.util.SystemStateEnum;
import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements a pattern command
 * This class completes filing applications
 * @author AndreAY
 */
public class EndFilingApplication extends Command {

    public static final String PARAM_PAGE = "forwardPage";
    public static final String MSG_NO_PAGE = "message.error.page.not.found";
    public static final String LOGGER_MSG_STATE_CHANGED = "logger.message.admin.change.system.state";
    public static final String PARAM_SYSTEM_STATE = "state";

    /**
     * This method completes filing applications
     * This method changes the status of the system, now users can not apply and add certificates.
     * They must confirm one of the applications
     * @param request a httpServletRequest
     * @param response a httpServletResponse
     * @throws ServletException a ServletException
     * @throws IOException a IOException
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            XMLManager.setElement(PARAM_SYSTEM_STATE, SystemStateEnum.CONFIRMATION_APPLICATION.toString());
            request.getSession().getServletContext().setAttribute(PARAM_SYSTEM_STATE, SystemStateEnum.CONFIRMATION_APPLICATION.toString());
            logger.info(Resource.getStr(LOGGER_MSG_STATE_CHANGED).replace("1", SystemStateEnum.CONFIRMATION_APPLICATION.toString()));
            setForward(Resource.getStr(FORWARD_MAIN));
        } catch (NoPropertyException ex) {
            getMessages().addMessage(ex.getMessage());
            setForward(Resource.getStr(FORWARD_ERROR));
            logger.error(ex);
        }
    }
}

