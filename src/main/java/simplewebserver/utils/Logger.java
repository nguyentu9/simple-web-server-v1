package simplewebserver.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Implement logging to record server activity, errors, and requests.
 * Use the `simplewebserver.util.Logger` class in your `simplewebserver.core.ClientHandler`.
 */
public class Logger {
    private static final String LOG_FILE = "server.log";

    public static void log(String message) {
        try(PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(LocalDateTime.now() + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
