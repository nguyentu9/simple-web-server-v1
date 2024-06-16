package simplewebserver.servlets;

import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = "downloads/" + request.getParameter("file");
        File file = new File(filePath);

        if (file.exists() && !file.isDirectory()) {
            response.sendStatus(200);
            response.setHeader("Content-Disposition", "attchment; filename=\"" + file.getName() + "\"");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            try (InputStream in = new FileInputStream(file);
                 OutputStream out = response.getOutputStream();
            ) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

        } else {
            response.sendStatus(404);
            response.sendBody("File not found");
        }

    }
}
