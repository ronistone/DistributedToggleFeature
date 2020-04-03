package br.com.ronistone.feature.toggle.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class FeatureToggleProperties {

    private static Properties properties;
    private static Logger logger = Logger.getLogger(FeatureToggleProperties.class.getName());

    public static void loadProperties() {

        if(System.getProperties() == null) {
            System.out.println("Creating new Properties");
            properties = new Properties();
            System.setProperties(properties);
        } else {
            properties = System.getProperties();
        }

        try(InputStream inputStream = FeatureToggleProperties.class.getClassLoader().getResourceAsStream("featureToggle.properties")){
            properties.load(inputStream);
        } catch (IOException e) {
            logger.warning("featureToggle.properties not found on classpath");
        }
    }

    public static String getProperty(String key) {
        if(properties == null) {
            return "";
        }
        return properties.getProperty(key);
    }

}
