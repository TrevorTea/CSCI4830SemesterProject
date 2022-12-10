import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Dashboard")
public class PotholeDashboard extends HttpServlet {
    private static final long serialVersionUID = 1;
    static String url = "jdbc:mysql://ec2-3-16-188-116.us-east-2.compute.amazonaws.com:3306/myDB";
    //static String url = "jdbc:mysql://ec2-3-90-216-236.compute-1.amazonaws.com:3306/myDB";
	static String user = "user";
	static String password = "password";

    public PotholeDashboard() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql;
        Connection connection = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Pothole Dashboard";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head>" + 
                "<meta charset=\"UTF-8\">" + 
                "<title>" + title + "</title>" + 
                "<style>" + 
                "body {" + 
                    "background-color: white;" + 
                    "font-family: Arial, sans-serif;" + 
                    "text-align: center;" + 
                "}" + 
                "h1 {" + 
                    "color: #222222;" + 
                    "margin-bottom: 20px;" + 
                "}" + 
                "p {" + 
                    "color: #666666;" + 
                    "font-size: 14px;" + 
                    "margin-bottom: 20px;" + 
                "}" + 
                "ul {" + 
                    "list-style-type: none;" + 
                    "padding: 0;" + 
                "}" + 
                "li {" + 
                    "display: inline-block;" + 
                    "margin: 0 10px;" + 
                "}" + 
                "a {" + 
                    "text-decoration: none;" + 
                    "color: #0077c9;" + 
                    "font-weight: bold;" + 
                    "background-color: #dddddf;" + 
                    "padding: 10px 20px;" + 
                    "border-radius: 4px;" + 
                    "margin-bottom: 30px;" +
                "}" + 
                "form {" + 
                    "border: 1px solid #dddddd;" + 
                    "padding: 20px;" + 
                    "border-radius: 4px;" + 
                "}" + 
                "form p {" + 
                    "margin-bottom: 10px;" + 
                "}" + 
                "form input[type=\"text\"], form input[type=\"number\"] {" + 
                    "padding: 10px;" + 
                    "width: 200px;" + 
                    "border: 1px solid #dddddd;" + 
                    "border-radius: 4px;" + 
                "}" + 
                "form select {" + 
                    "padding: 10px;" + 
                    "border: 1px solid #dddddd;" + 
                    "border-radius: 4px;" + 
                "}" + 
                "form input[type=\"submit\"] {" + 
                    "background-color: #0077c9;" + 
                    "color: white;" + 
                    "padding: 10px 20px;" + 
                    "font-weight: bold;" + 
                    "border: none;" + 
                    "border-radius: 4px;" + 
                "}" +
                ".button {" +
                      	"text-decoration: none;" +
                        "background-color: #0077c9;" +
                        "color: white;" + 
                        "padding: 10px 20px;" +
                        "font-weight: bold;" +
                        "border: none;" +
                        "border-radius: 4px;" +
                      "}" +
                "</style>" +
            "</head>\n" + //
            "<body bgcolor = \"white\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n" + //
            "<a href=\"PotholeTrackerHome.html\" class=\"button\">HOME</a>" +
            "<spacer type=\"vertical\" width=\"100\" height=\"100\" size=\"100\"></spacer>");


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
            
        }

        sql = "SELECT * FROM potholes";
        try {

            statement1 = connection.prepareStatement(sql);     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("<table align=\"center\" border=1 width=80% height=30% style=\"margin: 20px 0;\">");
        out.println("<tr><th>ID</th><th>Address</th><th>Severity</th><th>Status</th><th>Reports</th><th>Last Report</th><th>Road Material</th></tr>");
        try {
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("id");
                String address = rs.getString("address");
                String severity = rs.getString("severity");
                String status = rs.getString("status");
                String hits = rs.getString("hits");
                String lastHit = rs.getString("lastHit");
                String material = rs.getString("material");
                out.println("<tr><td>" + id + "</td><td>" + address + "</td><td>" + severity + "</td><td>" + status + "</td><td>" + hits + "</td><td>" + lastHit + "</td><td>" + material + "</td></tr>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}