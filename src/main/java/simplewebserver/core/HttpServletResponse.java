package simplewebserver.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Represents an HTTP response.
 * Contains methods to set the response status, headers, and body.
 * Methods to send error responses and flush the buffer.
 */
public class HttpServletResponse {
    private static final String CRLF = "\r\n";

    private OutputStream output;
    private PrintWriter writer;
    private boolean useGzip;
    private int status;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private StringBuilder body = new StringBuilder();

    public HttpServletResponse(OutputStream output) {
        this.output = output;
//        this.status = 200; // default status code is 200 ok
//        this.statusMessage = "OK"; // default status message
        this.writer = new PrintWriter(output);
    }


    public HttpServletResponse(OutputStream output, boolean useGzip) throws IOException {
        this.output = useGzip ? new GZIPOutputStream(output) : output;
        this.writer = new PrintWriter(this.output, true);
        this.useGzip = useGzip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(int status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void write(String content) {
        body.append(content);
    }


    public void sendError(int status, String message) throws IOException {
        setStatus(status);
        write("<html><head><title>" + status + " " + message + "</title></head><body>");
        write("<h1>" + status + " " + message + "</h1>");
        write("</body></html>");
        send();
    }

    public void sendStatus(int statusCode) {
        writer.println("HTTP/1.1 " + statusCode + " " + getStatusText(statusCode));
        writer.println();
    }

    // Methods to send status and body
    public void sendBody(String body) throws IOException {
        writer.println(body);
        writer.flush();
        if (useGzip) {
            ((GZIPOutputStream) output).finish();
        }
    }

    public void send() throws IOException {
        // Write status line
        writer.write("HTTP/1.1 " + status + " " + statusMessage + CRLF);

        // Write headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writer.write(header.getKey() + ": " + header.getValue() + CRLF);
        }

        // Write cookie
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            writer.write("Set-Cookie: " + cookie.getKey() + "=" + cookie.getValue() + CRLF);
        }

        // Write a blank line to indicate the end of headers
        writer.write(CRLF);

        // Write body
        writer.write(body.toString());
        writer.flush();
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void flushBuffer() throws IOException {
        writer.flush();
    }

    private String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            default:
                return "Unknown";
        }
    }

    public OutputStream getOutputStream() {
        return output;
    }

    public void setCookie(String name, String value) {
        cookies.put(name, value);
    }

    public void sendRedirect(String location) throws IOException {
        setStatus(302, "Found");
        setHeader("Location", location);
        send();
    }

}





