package by.epam.library.validation;

import javax.servlet.http.HttpServletRequest;


public interface Validator {
    public boolean validate(HttpServletRequest request,String paramForValidation);

}
