// definition of the package in which class is placed
package pl.polsl.lab.client;

import java.io.*;
import java.util.*;

/**
 * Class representing client side protocol properties. 
 * 
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class ClientProperties {
    
    /**
     * Constant representing name of file.
     */
    private final String FILE_NAME = ".properties";
    
    /**
     * Field for properties.
     */
    private Properties properties;
    
    /**
     * Non-argument constructor.
     */
    public ClientProperties() {
        properties = new Properties();
        File propertiesFile = new File(FILE_NAME);
        if (!propertiesFile.exists()) //if there is no properties file
        {
            this.setProperty("host", "127.0.0.1");
            this.setProperty("port", "8888");
            this.store();
        }
    }
    
    /**
     * Method for getting property.
     * 
     * @param name name of property
     * @return string with value
     */
    public String getProperty(String name) {
        return properties.getProperty(name);
    }
    
    /**
     * Method for setting property.
     * 
     * @param name name of property
     * @param value value of property
     */
    public void setProperty(String name, String value) {
        properties.setProperty(name, value);
    }
    
    /**
     * Method for storing properties into file.
     */
    public void store() {
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            properties.store(out, "--Client Configuration--");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Method for loading properties from file.
     */
    public void load() {
        try (FileInputStream in = new FileInputStream(FILE_NAME)) {
            properties.load(in);     
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
