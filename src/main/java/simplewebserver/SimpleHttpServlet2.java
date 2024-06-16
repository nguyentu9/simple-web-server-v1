package simplewebserver;

import simplewebserver.core.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServlet2 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    OutputStream outputStream = socket.getOutputStream();

                    HttpServletResponse response = new HttpServletResponse(outputStream);

                    // Handle the request (this example just sends a simple response)
                    response.setHeader("Content-Type", "text/html");
                    response.setCookie("sessionId", "123456");
                    response.write("<html><body><h1>Hello, World!</h1></body></html>");
                    response.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
