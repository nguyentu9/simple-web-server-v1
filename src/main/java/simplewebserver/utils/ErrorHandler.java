package simplewebserver.utils;

import simplewebserver.core.HttpServletResponse;

import java.io.IOException;

public class ErrorHandler {
    public static void handle(int statusCode, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendStatus(statusCode);
        httpServletResponse.sendBody("<html><body><h1>Error " + statusCode + "</h1></body></html>");
    }
}
