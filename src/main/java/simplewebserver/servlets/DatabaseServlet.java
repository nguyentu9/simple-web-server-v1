package simplewebserver.servlets;

import simplewebserver.configs.DatabaseConfig;
import simplewebserver.core.HttpServlet;
import simplewebserver.core.HttpServletRequest;
import simplewebserver.core.HttpServletResponse;
import simplewebserver.utils.ErrorHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery()
        ) {
            StringBuilder html = new StringBuilder("<html><body><table>");
            while (resultSet.next()) {
                html.append("<tr><td>").append(resultSet.getString("username")).append("</td></tr>");
            }
            html.append("</table></body></html>");
            response.sendStatus(200);
            response.sendBody(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            ErrorHandler.handle(500, response);
        }
    }
}
