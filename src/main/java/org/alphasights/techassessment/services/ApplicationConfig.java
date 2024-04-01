package org.alphasights.techassessment.services;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * @author lucascorrea
 */
@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application {

    public static final String APPLICATION_JSON_CHARSET_UTF8 = "application/json; charset=utf-8";

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
//        resources.add(ServiceAccount.class);
    }
}
