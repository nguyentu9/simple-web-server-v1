package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.core.session.Session;
import simplewebserver.core.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationServlet extends HttpServlet {
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("admin", "password123");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            Session session = new SessionManager().createSession();
            session.setAttribute("username", username);
            response.setCookie("SESSIONID", session.getId());
            response.sendStatus(200);
            response.sendBody("Login successful");
        } else {
            response.sendStatus(401);
            response.sendBody("Invalid credentials");
        }
    }
}
