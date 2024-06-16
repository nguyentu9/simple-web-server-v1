package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.TokenBucketRateLimiter;

public class TokenBucketServlet extends HttpServlet {
    private static final TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(5, 1);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String clientIp = request.getRemoteAddr();
        if (rateLimiter.isAllowed(clientIp)) {
            response.sendStatus(200);
            response.sendBody("Request allowed");
        } else {
            response.sendStatus(429);
            response.sendBody("Too Many Requests");
        }
    }
}
