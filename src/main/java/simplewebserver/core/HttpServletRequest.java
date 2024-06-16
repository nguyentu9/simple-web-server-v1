package simplewebserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * Represents an HTTP request.
 * Contains methods to get the request method, headers, parameters, body, and remote address.
 * Provides setters for setting these properties, which would be typically set by the server handling the incoming request.
 */
public class HttpServletRequest {
    private InputStream input;
    private String method;
    private String path;
    private String protocol;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private String body;
    private String remoteAddr; // TODO: get the removeAddr from the request
    private Locale locale; // TODO: get the locale from the request


    public HttpServletRequest(InputStream input) throws IOException {
        this.input = input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line);
        this.method = tokenizer.nextToken();
        this.path = tokenizer.nextToken();
    }

    public HttpServletRequest(InputStream input, String remoteAddr) throws IOException {
        this.input = input;
        this.remoteAddr = remoteAddr;
        parseRequest(input);
    }

    private void parseRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String initialLine = reader.readLine();
        if (initialLine != null) {
            parseInitialLine(initialLine);
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                parseHeaderLine(line);
            }
            parseCookies();
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                parseBody(reader);
            }
        }
    }

    private void parseInitialLine(String initialLine) {
        String[] parts = initialLine.split(" ");
        if (parts.length == 3) {
            method = parts[0];
            String[] pathAndParams = parts[1].split("\\?", 2);
            path = pathAndParams[0];
            if (pathAndParams.length == 2) {
                parseParameters(pathAndParams[1]);
            }
            protocol = parts[2];
        }
    }

    private void parseParameters(String paramString) {
        String[] paramPairs = paramString.split("&");
        for (String pair : paramPairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                parameters.put(key, value);
            }
        }
    }

    private void parseHeaderLine(String headerLine) {
        String[] parts = headerLine.split(": ", 2);
        if (parts.length == 2) {
            headers.put(parts[0], parts[1]);
        }
    }

    private void parseBody(BufferedReader reader) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuilder.append(line).append("\r\n");
        }
        body = bodyBuilder.toString().trim();
    }

    private void parseCookies() {
        String cookieHeader = headers.get("Cookie");
        if (cookieHeader != null) {
            String[] cookiePairs = cookieHeader.split("; ");
            for (String pair : cookiePairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    cookies.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public InputStream getInputStream() {
        return this.input;
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

}
