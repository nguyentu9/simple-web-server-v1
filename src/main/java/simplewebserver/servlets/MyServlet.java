package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.services.MyService;

import javax.inject.Inject;

public class MyServlet extends HttpServlet {

    @Inject
    private MyService myService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String greeting = myService.getGreeting();
        response.sendStatus(200);
        response.sendBody("<html><body><h1>" + greeting + "</h1></body></html>");
    }
}
// In your main application, set up the DI framework (e.g., Guice)