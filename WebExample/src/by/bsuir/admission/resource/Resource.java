package by.bsuir.admission.resource;

import java.util.ResourceBundle;

/**
 * This class provides an access to properties file
 * @author AndreAY
 */
public class Resource {

    /**
     * This is path to properties file
     */
    public static final String RESOURCE_PATH = "by.bsuir.admission.resource.Resource";
    /**
     * Resource bundles contain locale-specific objects
     */
    private static ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_PATH);

    /**
     * This method return property by key
     * @param key a name of property
     * @return value of property
     */
    public static String getStr(String key) {
        return resource.getString(key);
    }
}
