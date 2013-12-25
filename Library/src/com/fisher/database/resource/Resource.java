package com.fisher.database.resource;

import java.util.ResourceBundle;

public class Resource {

    /**
     * This is path to properties file
     */
    public static final String RESOURCE_PATH = "com.fisher.database.resource.Resource";
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
