package simplewebserver.core;

public interface Servlet {
    void init() throws Exception;
    void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;
    void destroy();
}
