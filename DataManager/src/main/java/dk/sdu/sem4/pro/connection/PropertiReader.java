package dk.sdu.sem4.pro.connection;

import java.io.IOException;
import java.util.Properties;

public class PropertiReader {
    private Properties properties;

    public PropertiReader(String filename) throws IOException {
        properties = new Properties();
        java.io.InputStream inputStream = this.getClass().getResourceAsStream(filename);
        Properties properties = new Properties();
        properties.load(inputStream);
    }

    public String getProperty(String key) throws IOException {
        return properties.getProperty(key);
    }
}
