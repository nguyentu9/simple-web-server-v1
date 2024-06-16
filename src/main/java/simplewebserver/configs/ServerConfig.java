package simplewebserver.configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Load server configurations from a properties file (e.g., server.properties).
 * Use the simplewebserver.configs.ServerConfig class in your main server class.
 */
public class ServerConfig {
    private Properties properties = new Properties();

    public ServerConfig(String configFilePath) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
