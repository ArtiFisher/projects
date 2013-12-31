package by.bsu.library.validation;

import javax.servlet.http.HttpServletRequest;


public interface Validator {
    public boolean validate(HttpServletRequest request,String paramForValidation);

}
