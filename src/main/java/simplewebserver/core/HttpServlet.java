package simplewebserver.core;

/**
 * This is an abstract class with methods to handle various HTTP methods (GET, POST, PUT, DELETE).
 * Each method can be overridden by subclasses to implement specific behavior.
 * The service method determines which HTTP method is called and dispatches the request to the appropriate handler.
 */
public abstract class HttpServlet implements Servlet {

    @Override
    public void init() throws Exception {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "GET method not supported");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "POST method not supported");
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "PUT method not supported");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "DELETE method not supported");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        switch(method) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
            case "PUT":
                doPut(request, response);
            case "DELETE":
                doDelete(request, response);
            default:
                response.sendError(405, "Method not supported");
        }
    }

    @Override
    public void destroy() {

    }
}
