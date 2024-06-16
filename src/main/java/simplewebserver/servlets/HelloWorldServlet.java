package simplewebserver.servlets;

import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.core.Servlet;

public class HelloWorldServlet implements Servlet {
    @Override
    public void init() {
        // Initialization code
    }

    @Override
    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.sendStatus(200);
        httpServletResponse.sendBody("<html><body><h1>Hello, World!</h1></body></html>");
    }

    @Override
    public void destroy() {
        // Cleanup code
    }
}
