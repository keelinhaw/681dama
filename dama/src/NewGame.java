

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	    		PreparedStatement ps = con.prepareStatement(newGame);
	    		ps.clearParameters();
	    		ps.setString(1, player1);
	    		ps.setString(2, status);
	    		ps.executeUpdate();
	    		con.close();
            response.sendRedirect("./NewGame.jsp");                        
    		} 
        catch (Exception e) {
        		e.printStackTrace();
            response.sendRedirect("./failure.html");
        }
    }	
		/*
		try {
			new Thread() {
				public void run() {
					try {
						GameServer game = new GameServer();
						game.newGame();
						HttpSession session = request.getSession();
						session.setAttribute("game", game);
						//while (true) {
						//	System.out.println("Waiting for opponent...");	
						//}
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
	        //HttpSession session = request.getSession();
	        //String username = session.getAttribute("username");
	        //session.setAttribute("player1", username);
			response.sendRedirect("./NewGame.jsp");
		}
		catch (Exception e) {
			
		}
		doGet(request, response);
	}
	*/

}
