package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.RateLimiter;

// Use the RateLimiter in your servlet
public class RateLimitingServlet extends HttpServlet {
    private static final RateLimiter rateLimiter = new RateLimiter(100, 6000);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String clientIp = request.getRemoteAddr();
        if (rateLimiter.isAllowed(clientIp)) {
            response.sendStatus(200);
            response.sendBody("<html><body><h1>Request allowed</h1></body></html>");
        } else {
            response.sendStatus(429);
            response.sendBody("<html><body><h1>Too Many Requests</h1></body></html>");
        }
    }
}
