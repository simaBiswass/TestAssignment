package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SetProperties {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties = loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //getProperties method returns the value of the properties variable
    public static Properties getProperties() {
        return properties;
    }
    //loadProperties
    private static Properties loadProperties() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./Global Settings.properties");
        try {
            properties.load(fileInputStream);
        }finally {
            fileInputStream.close();
        }
        return properties;
    }

}
