package simplewebserver.filters;

import simplewebserver.core.FilterChain;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

public interface Filter {
    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception;
}
