

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import oracle.jdbc.driver.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        if (password1 == password2) {
	        try {
		        String email = request.getParameter("email");
		        String username = request.getParameter("username");
		        String password1 = request.getParameter("password1");
		        String password2 = request.getParameter("password2");
		
		        	Class.forName("oracle.jdbc.driver.OracleDriver");
		    		Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com:1521:dama","swe681","SWEpass123");
		    		String query = "INSERT INTO USERS VALUES (?,?,?)";
		    		PreparedStatement ps = con.prepareStatement(query);
		    		ps.clearParameters();
		    		ps.setString(1, email);
		    		ps.setString(2, username);
		    		ps.setString(3, password1);
		    		ps.executeUpdate();
	    		} 
	        catch (SQLException | ClassNotFoundException e) {
	        		e.printStackTrace();
	        }
        }
        else {
        		response.sendRedirect("./failure.html");
        }
        response.sendRedirect("./success.html");
	}
}
