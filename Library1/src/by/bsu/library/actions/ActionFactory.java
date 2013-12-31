package by.bsu.library.actions;

import by.bsu.library.actions.commands.EmptyCommand;
import javax.servlet.http.HttpServletRequest;


public class ActionFactory {
    private static final String METHOD = "method";
    public ActionCommand defineCommand(HttpServletRequest request){
         ActionCommand current = new EmptyCommand();
         String action = request.getParameter(METHOD);
         if(action == null || action.isEmpty()){
             return current;
         }
         try{
             CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
             current = currentEnum.getCurrentCommand();
         }
         catch(IllegalArgumentException e){
              return current;
         }
         return current;
    }

}
