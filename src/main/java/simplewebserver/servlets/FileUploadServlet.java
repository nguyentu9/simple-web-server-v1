package simplewebserver.servlets;

// 4. File Upload Handling

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.MultipartParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 1. Add the ability to handle file uploads via multipart/form-data.
 * 2. Overrides the doPost method to handle file uploads.
 * Uses MultipartParser to parse the request.
 * Processes form fields and saves file parts to disk.
 * Sends a response indicating the status of the file upload.
 */
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contentType = request.getHeader("Content-Type");

        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            MultipartParser parser = new MultipartParser(request.getInputStream(), contentType);

            // Process form fields
            Map<String, String> formFields = parser.getFormFields();
            for (Map.Entry<String, String> field : formFields.entrySet()) {
                System.out.println("Field: " + field.getKey() + " = " + field.getValue());
            }

            // Process file parts
            Map<String, byte[]> fileParts = parser.getFileParts();
            for (Map.Entry<String, byte[]> filePart : fileParts.entrySet()) {
                String fieldName = filePart.getKey();
                byte[] fileData = filePart.getValue();

                // Save the file to disk
                Path destination = Paths.get("uploads/" + fieldName);
                Files.createDirectories(destination.getParent());
                parser.saveFilePart(fieldName, destination);
                System.out.println("File saved: " + destination);
            }

            response.sendStatus(200);
            response.sendBody("File upload successfully");
        } else {
            response.sendError(400, "Invalid content type");
        }
    }
}
