package by.bsu.library.validation;

import javax.servlet.http.HttpServletRequest;


public class NumberValidator implements Validator{

    public boolean validate(HttpServletRequest request,String paramForValidation) {
        boolean result = true;
        try{
            Integer.parseInt(request.getParameter(paramForValidation));
            result = true;
        }
        catch(NumberFormatException e){
            result = false;
        }
        finally{
            return result;
        }
    }
}
