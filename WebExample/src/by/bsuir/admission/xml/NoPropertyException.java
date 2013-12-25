package by.bsuir.admission.xml;

import by.bsuir.admission.resource.Resource;

/**
 * This exception appears if property not found in XML
 * @author AndreAY
 */
public class NoPropertyException extends Exception{

    public static final String NO_PROPERTY="error.no.property";

    public NoPropertyException(String propertyName) {
        super(Resource.getStr(NO_PROPERTY)+propertyName);
    }
}
