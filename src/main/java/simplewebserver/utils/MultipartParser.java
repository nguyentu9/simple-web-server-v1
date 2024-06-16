package simplewebserver.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
 * Extracts the boundary from the Content-Type header.
 * Parses the input stream to separate form fields and file parts.
 * Provides methods to retrieve form fields and file parts.
 * Provides a method to save file parts to disk.
 * */
public class MultipartParser {
    private final InputStream inputStream;
    private final String boundary;
    private final Map<String, String> formFields = new HashMap<>();
    private final Map<String, byte[]> fileParts = new HashMap<>();


    public MultipartParser(InputStream inputStream, String contentType) throws IOException {
        this.inputStream = inputStream;
        this.boundary = extractBoundary(contentType);
        parse();
    }


    private String extractBoundary(String contentType) {
        String boundary = null;
        for (String param : contentType.split(";")) {
            if (param.trim().startsWith("boundary=")) {
                boundary = param.split("=")[1].trim();
                if (boundary.startsWith("\"") && boundary.endsWith("\"")) {
                    boundary = boundary.substring(1, boundary.length() - 1);
                }
                break;
            }
        }
        return boundary;
    }

    private void parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(boundary)) {
                String contentDisposition = reader.readLine();
                if (contentDisposition != null && contentDisposition.contains("form-data")) {
                    String name = extractName(contentDisposition);
                    String filename = extractFilename(contentDisposition);

                    reader.readLine(); // Skip the Content-Type line if present

                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    while ((line = reader.readLine()) != null && !line.contains(boundary)) {
                        buffer.write(line.getBytes());
                        buffer.write("\r\n".getBytes());
                    }

                    if (filename != null) {
                        fileParts.put(name, buffer.toByteArray());
                    } else {
                        formFields.put(name, buffer.toString());
                    }
                }
            }

        }

    }

    private String extractFilename(String contentDisposition) {
        for (String param : contentDisposition.split(";")) {
            if (param.trim().startsWith("name=")) {
                String name = param.split("=")[1].trim();
                if (name.startsWith("\"") && name.endsWith("\"")) {
                    name = name.substring(1, name.length() - 1);
                }
                return name;
            }
        }
        return null;
    }

    private String extractName(String contentDisposition) {
        for (String param : contentDisposition.split(";")) {
            if (param.trim().startsWith("filename=")) {
                String filename = param.split("=")[1].trim();
                if (filename.startsWith("\"") && filename.endsWith("\"")) {
                    filename = filename.substring(1, filename.length() - 1);
                }
                return filename;
            }
        }
        return null;
    }

    public Map<String, String> getFormFields() {
        return formFields;
    }

    public Map<String, byte[]> getFileParts() {
        return fileParts;
    }

    public void saveFilePart(String fileName, Path destination) throws IOException {
        byte[] fileData = fileParts.get(fileName);
        if(fileData != null) {
            Files.write(destination, fileData);
        }
    }


}











