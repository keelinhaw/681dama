package com.Dama;


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

import org.apache.log4j.Logger;


/**
 * Servlet implementation class NewGame
 */
@WebServlet("/NewGame")
public class NewGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(NewGame.class);
       
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
	        
	    		LoadGame game = new LoadGame();
	    		Long gameid = game.newGame(player1);
	    		Game gameBean = game.getGame(gameid);

		    session.setAttribute("gameBean", gameBean);
		    response.sendRedirect("./NewGame.jsp");                     
    		} 
        catch (Exception e) {
        		log.error("Exception in NewGame : " + e );
            response.sendRedirect("./failure.html");
        }

    }	
}
