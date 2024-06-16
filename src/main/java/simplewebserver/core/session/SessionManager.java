package simplewebserver.core.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session createSession() {
        Session session = new Session();
        sessions.put(session.getId(), session);
        return session;
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
