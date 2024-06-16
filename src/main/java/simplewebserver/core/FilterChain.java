package simplewebserver.core;

import simplewebserver.filters.Filter;

import java.util.List;

/**
 * Filters can process requests and responses before they reach servlets or after servlets have processed them.
 * For example, a logging filter, authentication filter, etc.
 * */
public class FilterChain {
    private List<Filter> filters;
    private int currentIndex = 0;
    private Servlet servlet;

    public FilterChain(List<Filter> filters, Servlet servlet) {
        this.filters = filters;
        this.servlet = servlet;
    }

    public void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if(currentIndex < filters.size()) {
            filters.get(currentIndex++).doFilter(httpServletRequest, httpServletResponse, this);
        } else {
            servlet.service(httpServletRequest, httpServletResponse);
        }
    }


}
