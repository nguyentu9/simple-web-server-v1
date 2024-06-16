package simplewebserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Add a servlet context to share information among servlets.
 */
public class ServletContext {
    // TODO: Modify your simplewebserver.core.Servlet interface and simplewebserver.core.ClientHandler to pass the simplewebserver.core.ServletContext.
    private final Map<String, Object> attributes = new HashMap<>();

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }
}
