package simplewebserver.core;

import simplewebserver.annotations.WebServlet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use reflection to discover and register annotated servlets:
 */
public class ServletRouter {
    private final Map<String, Servlet> routeMap = new HashMap<>();

    public void scanForServlets(String packageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> cls : getClasses(packageName)) {
            if (cls.isAnnotationPresent(WebServlet.class)) {
                WebServlet annotation = cls.getAnnotation(WebServlet.class);
                routeMap.put(annotation.urlPattern(), (Servlet) cls.getDeclaredConstructor().newInstance());
            }

        }
    }

    // Utility method to find classes in a package (use a library like Reflections)
    private List<Class<?>> getClasses(String packageName) {
        // TODO: Implementation to scan the package for classes
        return null;
    }

    public Servlet getServlet(String servletName) {
        return routeMap.get(servletName);
    }
}
