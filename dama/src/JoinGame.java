import java.beans.Statement;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet("/JoinGame")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinGame() {
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
		HttpSession session = request.getSession();
		String gameid = request.getParameter("gameid");
        String player2 = (String) session.getAttribute("username");
        String status = "started";       

		try {
	    		GetPropertyValues properties = new GetPropertyValues();
	    		String dburl = properties.getPropValues("dburl");
	    		String dbuser = properties.getPropValues("dbuser");
	    		String dbpassword = properties.getPropValues("dbpassword");
	        Class.forName("org.postgresql.Driver");
	        Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
	                        
	    		String newGame = "UPDATE games SET player2=?,status=? WHERE id=?";
	    		PreparedStatement ps = con.prepareStatement(newGame);
	    		ps.clearParameters();
	    		ps.setString(1, player2);
	    		ps.setString(2, status);
	    		ps.setLong(3, Long.parseLong(gameid));
	    		ps.executeUpdate();
	    		con.close();
		}
        catch (Exception e) {
    			e.printStackTrace();
        }
		loadPieces(gameid);
        response.sendRedirect("./NewGame.jsp");                        
		doGet(request, response);
	}
	public void loadPieces(String gameid) {
	    String player1 = "";
	    String player2 = "";
		try {
	    		GetPropertyValues properties = new GetPropertyValues();
	    		String dburl = properties.getPropValues("dburl");
	    		String dbuser = properties.getPropValues("dbuser");
	    		String dbpassword = properties.getPropValues("dbpassword");
	        Class.forName("org.postgresql.Driver");
	        Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
	        
		    //Get player usernames
		    String query = "SELECT player1, player2 FROM games WHERE id=?";
		    PreparedStatement ps = con.prepareStatement(query);
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setLong(1, Long.parseLong(gameid));
			ResultSet game = ps.executeQuery();
		    if (game.next()) {
		    		player1 = game.getString("player1");
		        player2 = game.getString("player2");
		    }
           
	        query = "INSERT INTO piece (id, player, piece, location) VALUES (?,?,?,?)";
	        ps = con.prepareStatement(query);
	    		ps.setLong(1, Long.parseLong(gameid));
	    		ps.setString(2, player1);
	    		ps.setString(3, "R1");
	    		ps.setString(4, "B1");
	    		ps.addBatch();
	        ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R2");
	    		ps.setString(4, "B2");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R3");
	    		ps.setString(4, "B3");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R4");
	    		ps.setString(4, "B4");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R5");
	    		ps.setString(4, "B5");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R6");
	    		ps.setString(4, "B6");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R7");
	    		ps.setString(4, "B7");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R8");
	    		ps.setString(4, "B8");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R9");
	    		ps.setString(4, "C1");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R10");
	    		ps.setString(4, "C2");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R11");
	    		ps.setString(4, "C3");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R12");
	    		ps.setString(4, "C4");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R13");
	    		ps.setString(4, "C5");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R14");
	    		ps.setString(4, "C6");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R15");
	    		ps.setString(4, "C7");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player1);
	    		ps.setString(3, "R16");
	    		ps.setString(4, "C8");
    			ps.addBatch();
    			
	    		ps.setLong(1, Long.parseLong(gameid));
	    		ps.setString(2, player2);
	    		ps.setString(3, "B1");
	    		ps.setString(4, "F1");
	    		ps.addBatch();
	        ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B2");
	    		ps.setString(4, "F2");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B3");
	    		ps.setString(4, "F3");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B4");
	    		ps.setString(4, "F4");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B5");
	    		ps.setString(4, "F5");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B6");
	    		ps.setString(4, "F6");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B7");
	    		ps.setString(4, "F7");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B8");
	    		ps.setString(4, "F8");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B9");
	    		ps.setString(4, "G1");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B10");
	    		ps.setString(4, "G2");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B11");
	    		ps.setString(4, "G3");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B12");
	    		ps.setString(4, "G4");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B13");
	    		ps.setString(4, "G5");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B14");
	    		ps.setString(4, "G6");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B15");
	    		ps.setString(4, "G7");
    			ps.addBatch();
    			ps.setLong(1, Long.parseLong(gameid));
    			ps.setString(2, player2);
	    		ps.setString(3, "B16");
	    		ps.setString(4, "G8");
    			ps.addBatch();
	        int[] results = ps.executeBatch();
		}
        catch (Exception e) {
    			e.printStackTrace();
        }
	}

}
