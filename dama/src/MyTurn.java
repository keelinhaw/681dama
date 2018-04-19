

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

import java.lang.reflect.Method;

/**
 * Servlet implementation class MyTurn
 */
@WebServlet("/MyTurn")
public class MyTurn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyTurn() {
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
        String oldLocation = request.getParameter("oldLocation");
        String newLocation = request.getParameter("newLocation");
        System.out.println("Old location was: " + oldLocation);
        System.out.println("New location was: " + newLocation);
        String old = oldLocation.toUpperCase();
        String ne = newLocation.toUpperCase();
        HttpSession session = request.getSession();
        Game gameBean = (Game) session.getAttribute("gameBean");
        
        try {
        		//Get the piece to move
	        Method move1 = Game.class.getDeclaredMethod("get" + old);
	        String piece = (String) move1.invoke(gameBean);
	        //Set the original location to blank
	        Method move2 = Game.class.getDeclaredMethod("set" + old, String.class);
	        move2.invoke(gameBean, " ");
	        //get the piece to the new location
	        Method move3 = Game.class.getDeclaredMethod("set" + ne, String.class);
	        move3.invoke(gameBean, piece);
        }
        catch (Exception e) {
        		e.printStackTrace(System.out);
        }
        session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");
	}
	public boolean checkMove (String oldlocation, String newlocation) {
		return true;
	}
	public void updateMove (String piece, String newLocation) {
		try {
		GetPropertyValues properties = new GetPropertyValues();
		String dburl = properties.getPropValues("dburl");
		String dbuser = properties.getPropValues("dbuser");
		String dbpassword = properties.getPropValues("dbpassword");
	    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);

		String findmygames = "SELECT id, startdate, player1, player2, status FROM games WHERE player1=? OR player2=?";
		PreparedStatement ps = con.prepareStatement(findmygames);
		//ps.setString(1, myusername);
		//ps.setString(2, myusername);
		ResultSet mygames = ps.executeQuery();
		}
        catch (Exception e) {
    			e.printStackTrace();
        }
	}

}
