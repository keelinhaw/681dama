

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.server.GameServer;

/**
 * Servlet implementation class NewGame
 */
@WebServlet("/NewGame")
public class NewGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
	        HttpSession session = request.getSession();  
	        String player1 = (String) session.getAttribute("username");
	        String status = "new";
        	
        		GetPropertyValues properties = new GetPropertyValues();
        		String dburl = properties.getPropValues("dburl");
        		String dbuser = properties.getPropValues("dbuser");
        		String dbpassword = properties.getPropValues("dbpassword");
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
            
                            
	    		String newGame = "INSERT INTO games (player1, status) VALUES (?,?)";
	    		PreparedStatement ps = con.prepareStatement(newGame, Statement.RETURN_GENERATED_KEYS);
	    		ps.clearParameters();
	    		ps.setString(1, player1);
	    		ps.setString(2, status);
	    		ps.executeUpdate();
	    		
	    		long key = -1L;
	    		ResultSet rs = ps.getGeneratedKeys();
	    		if (rs.next()) {
	    		    key = rs.getLong(1);
	    		}
	    		System.out.println("My generated key is: " + key);
	    		con.close();
	    		
	    		//response.setContentType("application/json");    
	    		//PrintWriter out = response.getWriter();
	    		
	    		Game gameBean = new Game();
	    		gameBean.setGameid(key);
	    		gameBean.setA1(" ");
	    		gameBean.setA2(" ");
	    		gameBean.setA3(" ");
	    		gameBean.setA4(" ");
	    		gameBean.setA5(" ");
	    		gameBean.setA6(" ");
	    		gameBean.setA7(" ");
	    		gameBean.setA8(" ");
	    		gameBean.setB1("O");
	    		gameBean.setB2("O");
	    		gameBean.setB3("O");
	    		gameBean.setB4("O");
	    		gameBean.setB5("O");
	    		gameBean.setB6("O");
	    		gameBean.setB7("O");
	    		gameBean.setB8("O");
	    		gameBean.setC1("O");
	    		gameBean.setC2("O");
	    		gameBean.setC3("O");
	    		gameBean.setC4("O");
	    		gameBean.setC5("O");
	    		gameBean.setC6("O");
	    		gameBean.setC7("O");
	    		gameBean.setC8("O");
	    		gameBean.setD1(" ");
	    		gameBean.setD2(" ");
	    		gameBean.setD3(" ");
	    		gameBean.setD4(" ");
	    		gameBean.setD5(" ");
	    		gameBean.setD6(" ");
	    		gameBean.setD7(" ");
	    		gameBean.setD8(" ");
	    		gameBean.setE1(" ");
	    		gameBean.setE2(" ");
	    		gameBean.setE3(" ");
	    		gameBean.setE4(" ");
	    		gameBean.setE5(" ");
	    		gameBean.setE6(" ");
	    		gameBean.setE7(" ");
	    		gameBean.setE8(" ");
	    		gameBean.setF1("X");
	    		gameBean.setF2("X");
	    		gameBean.setF3("X");
	    		gameBean.setF4("X");
	    		gameBean.setF5("X");
	    		gameBean.setF6("X");
	    		gameBean.setF7("X");
	    		gameBean.setF8("X");
	    		gameBean.setG1("X");
	    		gameBean.setG2("X");
	    		gameBean.setG3("X");
	    		gameBean.setG4("X");
	    		gameBean.setG5("X");
	    		gameBean.setG6("X");
	    		gameBean.setG7("X");
	    		gameBean.setG8("X");
	    		gameBean.setH1(" ");
	    		gameBean.setH2(" ");
	    		gameBean.setH3(" ");
	    		gameBean.setH4(" ");
	    		gameBean.setH5(" ");
	    		gameBean.setH6(" ");
	    		gameBean.setH7(" ");
	    		gameBean.setH8(" ");

			//request.setAttribute("gameBean", gameBean);
		    session.setAttribute("gameBean", gameBean);
			javax.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("NewGame.jsp");
			dispatcher.forward(request, response);
            //response.sendRedirect("./NewGame.jsp");                        
    		} 
        catch (Exception e) {
        		e.printStackTrace();
            response.sendRedirect("./failure.html");
        }
    }	
}
