package simplewebserver.core.session;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

/**
 * Implement session management to track user sessions across multiple requests. Use a HashMap to store session data.
 * Modify your Request class to manage sessions.
 * */
@Deprecated
public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }


}
