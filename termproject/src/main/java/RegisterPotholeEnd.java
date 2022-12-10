import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/RegisterPotholeEnd")
public class RegisterPotholeEnd extends HttpServlet {
	static String url = "jdbc:mysql://ec2-3-16-188-116.us-east-2.compute.amazonaws.com:3306/myDB";
    //static String url = "jdbc:mysql://ec2-3-90-216-236.compute-1.amazonaws.com:3306/myDB";
	static String user = "user";
	static String password = "password";
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterPotholeEnd() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs = null;
        String iaddress = request.getParameter("address");
        String iseverity = request.getParameter("severity");
        String istatus = request.getParameter("status");
        String imaterial = request.getParameter("material");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String pagetitle = "Registry Response Page";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + pagetitle + "</title>" +
            "<style> body {background-color: white;font-family: Arial, sans-serif;text-align: center;}h1 {color: #222222;margin-bottom: 20px;}p {color: #666666;font-size: 14px;margin-bottom: 20px;}ul {list-style-type: none;padding: 0;}li {display: inline-block;margin: 0 10px;}a {text-decoration: none;color: #0077c9;font-weight: bold;background-color: #dddddd;padding: 10px 20px;border-radius: 4px;}.button {\n"
            + "      	text-decoration: none;\n"
            + "        background-color: #0077c9;\n"
            + "        color: white;\n"
            + "        padding: 10px 20px;\n"
            + "        font-weight: bold;\n"
            + "        border: none;\n"
            + "        border-radius: 4px;\n"
            + "      }</style>" +
            "</head>\n" + //
            "<body bgcolor = \"white\">\n" + //
            "<h1 align = \"center\">" + pagetitle + "</h1>\n" + //
            "<a href=\"PotholeTrackerHome.html\" class=\"button\">HOME</a>");


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

        try {
	        sql = "select * from potholes where address= ?;";
	        statement1 = connection.prepareStatement(sql);
	        statement1.setString(1, iaddress);
            rs = statement1.executeQuery();
        } 
        catch(SQLException e) {
            e.printStackTrace();
        }
        try {
        if (!rs.isBeforeFirst()) {
        	
        	sql = "insert into potholes (address, severity, status, hits, material) values(?,?,?,?,?);";
        	statement1 = connection.prepareStatement(sql);
        	statement1.setString(1, iaddress);
            statement1.setString(2, iseverity);
            statement1.setString(3, istatus);
            statement1.setString(4, "1");
            statement1.setString(5, imaterial);
            
            statement1.executeUpdate();            
        }
        else {
        	try {
        		rs.next();
        	//remove the old listing
        	String sql1 = "delete from potholes where address= " + "\"" + iaddress + "\";";
        	statement1 = connection.prepareStatement(sql1);
        	
        	int hits = Integer.parseInt(rs.getString("hits"));
            String address = rs.getString("address");
            String severity = rs.getString("severity");
            String status = istatus;
            String newhits = Integer.toString(hits + 1);
            String material = rs.getString("material");
            
            String sql2 = "insert into potholes (address, severity, status, hits, material) values(?,?,?,?,?);";
            statement2 = connection.prepareStatement(sql2);
        	statement2.setString(1, address);
            statement2.setString(2, severity);
            statement2.setString(3, status);
            statement2.setString(4, newhits);
            statement2.setString(5, material);
            
            
            	statement1.executeUpdate();
            	statement2.executeUpdate();
            }
            catch(SQLException e) {
            	e.printStackTrace();
            } 
        }} catch (SQLException e) {
        	e.printStackTrace();
        }

        out.println("<h2>Thank you for registering this pothole</h2>");
        out.println("<a href=\"Dashboard\" class=\"button\">Go to Pothole Dashboard</a>");
        out.println("</body></html>");
        try {connection.close();}
        catch(SQLException e) {e.printStackTrace();}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}