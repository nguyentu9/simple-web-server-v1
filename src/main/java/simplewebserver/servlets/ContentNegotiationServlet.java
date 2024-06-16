package simplewebserver.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.models.User;

public class ContentNegotiationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String acceptHeader = request.getHeader("Accept");
        User user = new User("John", "Doe");

        if (acceptHeader.contains("application/json")) {
            String json = new ObjectMapper().writeValueAsString(user);
            response.setHeader("Content-Type", "application/json");
            response.sendBody(json);
        } else if (acceptHeader.contains("application/xml")) {
            String xml = new XmlMapper().writeValueAsString(user);
            response.setHeader("Content-Type", "application/xml");
            response.sendBody(xml);
        } else {
            response.sendStatus(406);
            response.sendBody("Not Acceptable");
        }
    }
}
