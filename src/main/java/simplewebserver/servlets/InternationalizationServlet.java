package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Add support for internationalization to serve content in multiple languages.
 * */
public class InternationalizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);

        String greeting = bundle.getString("greeting");
        response.sendStatus(200);
        response.sendBody("<html><body><h1>" + greeting + "</h1></body></html>");
    }
}
