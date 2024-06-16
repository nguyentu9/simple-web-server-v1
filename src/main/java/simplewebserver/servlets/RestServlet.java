package simplewebserver.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.models.User;

public class RestServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User("John", "Doe");
        String json = objectMapper.writeValueAsString(user);

        response.sendStatus(200);
        response.setHeader("Content-Type", "application/json");
        response.sendBody(json);

    }
}
