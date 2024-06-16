package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.ErrorHandler;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * `Asynchronous Request Handling`
 * - Implement asynchronous request processing to handle long-running tasks without blocking the server.
 * */
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000); // Simulate a long-running task
                response.sendStatus(200);
                response.sendBody("<html><body><h1>Task Completed</h1></body></html>");
            } catch (Exception e) {
                e.printStackTrace();

                try {
                    ErrorHandler.handle(500, response);
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        });

    }
}
