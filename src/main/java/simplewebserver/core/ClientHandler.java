package simplewebserver.core;

import simplewebserver.servlets.HelloWorldServlet;
import simplewebserver.utils.ErrorHandler;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;


public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        ) {
            String requestLine = reader.readLine();
            System.out.println(requestLine);

            // Parse HTTP request
            StringTokenizer tokenizer = new StringTokenizer(requestLine);
            String method = tokenizer.nextToken();
            String resource = tokenizer.nextToken();

            if(method.equals("GET")) {
                handleGetRequest(resource, writer);
            } else {
                writer.println("HTTP/1.1 405 Method Not Allowed\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                ErrorHandler.handle(500, new HttpServletResponse(socket.getOutputStream(), false));
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }

    }

    private void handleGetRequest(String resource, PrintWriter writer) {
        if(resource.equals("/")) {
            resource = "/index.html";
        }

        String filePath = "webapp" + resource;
        File file = new File(filePath);

        if(file.exists() && !file.isDirectory()) {
            try(BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                writer.println("HTTP/1.1 200 OK\r\n");
                writer.println("Content-Type: text/html\r\n");
                writer.println("\r\n");

                String line;
                while((line = fileReader.readLine()) != null) {
                    writer.println(line);
                }
            } catch (IOException e) {
                writer.println("HTTP/1.1 500 Internal Server Error\r\n");
            }
        } else {
            writer.println("HTTP/1.1 404 Not Found\r\n");
        }

    }

    private void handleServletRequest(String resource, PrintWriter writer) {
        Servlet servlet = new HelloWorldServlet();
        try {
            servlet.init();
            HttpServletRequest httpServletRequest = new HttpServletRequest(socket.getInputStream());
            HttpServletResponse httpServletResponse = new HttpServletResponse(socket.getOutputStream(), false);
            servlet.service(httpServletRequest, httpServletResponse);
            servlet.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("HTTP/1.1 500 Internal Server Error\r\n");
        }
    }

    private void handleStaticFile(String resource, PrintWriter writer) {
        String filePath = "webapp" + resource;
        File file = new File(filePath);

        if(file.exists() && !file.isDirectory()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(resource))) {
                writer.println("HTTP/1.1 200 OK\r\n");
                writer.println("Content-Type: " + getContentType(filePath) + "\r\n");
                writer.println("\r\n");

                String line;
                while((line = reader.readLine())!= null) {
                    writer.println(line);
                }
            } catch (IOException e) {
                writer.println("HTTP/1.1 500 Internal Server Error\r\n");
            }
        } else {
            writer.println("HTTP/1.1 404 Not Found\r\n");
        }
    }

    private String getContentType(String filePath) {
        if(filePath.endsWith(".html")) {
            return "text/html";
        } else if(filePath.endsWith(".css")) {
            return "text/css";
        } else if(filePath.endsWith(".js")) {
            return "application/javascript";
        }
        return "text/plain";
    }
}












