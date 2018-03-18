

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
        if (password1.equals(password2)) {
//TODO list added by Chadia
// should present user-friendly message when password1 and password2 are not equal
// should present user-friendly message when username already exist in DB (should not wait for SQL exception)
// should add logging entry for user created
// should remove hard-coded DB credentials from this file and from Validate.java. Instead read these creds from a server config file.
// should add input validation (whitelist w/ regular expressions) for the email
// should not be storing passwords in clear form. Instead store only salted hash.
// should encrypt connections to the DB (use TLS encryption)
// question: should one email be allowed to register multiple usernames?
// consider: validating the exitance of the email (ie. send confirmation link to prove ownership of the email being registered)
// consider enforcing a password length and complexity for the user's password
// consider adding captcha for preventing bots from abusing this form (least important task for now)
	        try {

/*		        String email = request.getParameter("email");
		        String username = request.getParameter("username");
		        String password1 = request.getParameter("password1");
		        String password2 = request.getParameter("password2");
*/		


/*		        	Class.forName("oracle.jdbc.driver.OracleDriver");
		    		Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@dama.cc0rojk8d4jm.us-east-1.rds.amazonaws.com:1521:dama","swe681","SWEpass123");
		    		String query = "INSERT INTO USERS VALUES (?,?,?)";
*/
                                Class.forName("org.postgresql.Driver");
                                Connection con = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/dama","postgres","ISA681DamaP!");
		    		String query = "INSERT INTO USERS VALUES (?,?,?)";

		    		PreparedStatement ps = con.prepareStatement(query);
		    		ps.clearParameters();
		    		ps.setString(2, email);
		    		ps.setString(1, username);
		    		ps.setString(3, password1);
		    		ps.executeUpdate();

                                response.sendRedirect("./success.html");
                                con.close();
                                
	    		} 
	        catch (SQLException | ClassNotFoundException e) {
	        		e.printStackTrace();
	        }
        }
        else {
        		response.sendRedirect("./failure.html");
        }
 //       response.sendRedirect("./success.html");
	}
}
