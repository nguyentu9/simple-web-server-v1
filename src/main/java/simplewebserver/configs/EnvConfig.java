package simplewebserver.configs;


/**
 * Configuration Management with Environment Variables
 * Use environment variables for configuration management.
 * Usage:
 * String dbUrl = EnvConfig.get("DATABASE_URL", "jdbc:mysql://DOMAIN:PORT/DB_NAME");
 */
public class EnvConfig {
    public static String get(String key, String defaultValue) {
        String value = System.getenv(key);
        return value != null ? value : defaultValue;
    }
}
