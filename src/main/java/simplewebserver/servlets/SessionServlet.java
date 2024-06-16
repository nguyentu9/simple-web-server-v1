package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.core.session.Session;
import simplewebserver.core.session.SessionManager;

public class SessionServlet extends HttpServlet {
    private static final SessionManager sessionManager = new SessionManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sessionId = request.getCookie("SESSIONID");
        Session session = sessionId != null ? sessionManager.getSession(sessionId) : sessionManager.createSession();

        if (sessionId == null) {
            response.setCookie("SESSIONID", session.getId());
        }

        session.setAttribute("lastAccessed", System.currentTimeMillis());

        response.sendStatus(200);
        response.sendBody("<html><body><h1>Session ID: " + session.getId() + "</h1></body></html>");

    }
}
