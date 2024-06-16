package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implement Server-Sent Events to push updates to the client.
 * */
public class SSEServlet extends HttpServlet {
    private static final Set<PrintWriter> clients = Collections.synchronizedSet(new HashSet<>());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");


        PrintWriter writer = response.getWriter();
        clients.add(writer);

        while (true) {
            // Simulate an event
            writer.write("data: " + "New Event" + "\n\n");
            writer.flush();

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void destroy() {
        for (PrintWriter writer : clients) {
            writer.close();
        }
    }
}
