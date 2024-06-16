package simplewebserver.filters;

import simplewebserver.core.FilterChain;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

public class LoggingFilter implements Filter {
    @Override
    public void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws Exception {
        System.out.println("Request received: " + httpServletRequest.getPath());
        chain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("Response sent: " + httpServletResponse.getStatus());
    }
}
