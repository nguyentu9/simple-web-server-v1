package simplewebserver.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use annotations to map servlets to URLs, making it easier to manage routes.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {
    String urlPattern();
}
