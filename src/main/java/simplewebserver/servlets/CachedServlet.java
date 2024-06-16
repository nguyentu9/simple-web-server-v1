package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.SimpleCache;

//  Use the cache in your servlet
public class CachedServlet extends HttpServlet {
    private static final SimpleCache cache = new SimpleCache();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String key = "someKey";
        String value = cache.get(key);
        if(value == null) {
            value = "Generated content";
            cache.put(key, value);
        }

        response.sendStatus(200);
        response.sendBody("<html><body><h1>" + value + "</h1></body></html>");
    }
}
