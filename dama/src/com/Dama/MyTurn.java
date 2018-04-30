package com.Dama;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Level;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;


/**
 * Servlet implementation class MyTurn
 */
@WebServlet("/MyTurn")
public class MyTurn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Game gameBean = new Game();
	String captureLocation = null;
	static Logger log = Logger.getLogger(MyTurn.class);
        static Connection con = null;
        static int max_length = 2;
       
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
                 log.debug("Attempt to access MyTun page with GET ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldLocation = request.getParameter("oldLocation");
        String newLocation = request.getParameter("newLocation");
        HttpSession session = request.getSession();
        gameBean = (Game) session.getAttribute("gameBean");
        String playerturn = gameBean.getPlayerturn();
        String player1 = gameBean.getPlayer1();
        String player2 = gameBean.getPlayer2();
        Long gameid = gameBean.getGameid();
        String piece = "";
        
        //todo input validation
        try {
                    String cleaned_oldLocation; // canonicalize
                    cleaned_oldLocation = ESAPI.validator().getValidInput("MyTurnPage_oldLocationField",oldLocation,
                            "PieceLocation", // regex spec
                            max_length, // max lengyh
                            false, // no nulls
                            true);
                    
                    String cleaned_newLocation; // canonicalize
                    cleaned_newLocation = ESAPI.validator().getValidInput("MyTurnPage_newLocationField",newLocation,
                            "PieceLocation", // regex spec
                            max_length, // max lengyh
                            false, // no nulls
                            true);
        
                    String oldLocale = cleaned_oldLocation.toUpperCase();
                    String newLocale = cleaned_newLocation.toUpperCase();

        

        
        try {
	    		//Get the piece to move
	        Method move1 = Game.class.getDeclaredMethod("get" + oldLocale);
	        piece = (String) move1.invoke(gameBean);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            java.util.logging.Logger.getLogger(ReturnToGame.class.getName()).log(Level.SEVERE, null, e);
        		//log.error("Exception in MyTurn : " + e );
        }
        //Check if the piece you are moving is the correct piece
        if (checkPiece(player1, player2, playerturn, piece)) {
        		if(checkMove(piece, oldLocale, newLocale)) {
	            try {
			        //Set the original location to blank
			        Method move2 = Game.class.getDeclaredMethod("set" + oldLocale, String.class);
			        move2.invoke(gameBean, " ");
			        //get the piece to the new location
			        Method move3 = Game.class.getDeclaredMethod("set" + newLocale, String.class);
			        move3.invoke(gameBean, piece);
		        }
		        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
		        		log.error("Exception in MyTurn : " + e );
		        }
	            if (playerturn.equals(player1)) {
		        		gameBean.setPlayerturn(player2);
		        		updateMove(piece, cleaned_oldLocation, cleaned_newLocation, player1, player2, gameid);
		        }
		        else {
		        		gameBean.setPlayerturn(player1);
		        		updateMove(piece, cleaned_oldLocation, cleaned_newLocation, player2, player1, gameid);
		        }
        		}
        		checkWon(newLocale, player1, player2, playerturn, gameid);
        }
        saveError(gameid);
        session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");
        
                       } catch (ValidationException ex) {
                java.util.logging.Logger.getLogger(JoinGame.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("./failure.html");
        } catch (IntrusionException ex) {
                java.util.logging.Logger.getLogger(JoinGame.class.getName()).log(Level.SEVERE, null, ex);
//                Potential Intrusion Attempt
                //if valid user, then lock the user's account. If user loggedin invalidate session
                session.invalidate();
                response.sendRedirect("./failure.html");
        }
}

        
        public void saveError (Long gameid) {
		String errorString = gameBean.getErrorstring();
		try {
			con = ConnectionManager.getConnection();
	    		String updateError = "UPDATE games SET error_message=? WHERE id=?";
	    		PreparedStatement ps = con.prepareStatement(updateError);
			ps.clearParameters();
			ps.setString(1, errorString);
			ps.setLong(2, gameid);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			log.error("Exception in MyTurn : " + e );
		}
	}
	public boolean checkPiece (String player1, String player2, String playerturn, String piece) {
		if (player1.equals(playerturn) && piece.equals("O")) {
			return true;
		}
		else if (player2.equals(playerturn) && piece.equals("X")) {
			return true;
		}
		gameBean.setErrorstring("Please move your own pieces!");
		return false;
	}
	public boolean checkMove (String piece, String oldLocale, String newLocale) {
		//Get x and y coordinates for move
		char xOld = oldLocale.charAt(0);
		char xNew = newLocale.charAt(0);
		int yOld = Character.getNumericValue(oldLocale.charAt(1));
		int yNew = Character.getNumericValue(newLocale.charAt(1));
		String xArray = "ABCDEFGH";
		//String xArray2 = "HGFEDCBA";
		//String yArray = "12345678";
		
		if (oldLocale.equals(newLocale)) {
			gameBean.setErrorstring("You can't move to the same spot!");
			return false;
		}
		if (piece.equals("O")) {
			//See if player is trying to move backwards
			if(xArray.indexOf(xNew) < xArray.indexOf(xOld)) {
				gameBean.setErrorstring("You can't move backwards!");
				return false;
			}
			else {
				//Check if player if moving left or right
				if(xOld == xNew) {
					//Check if piece is just moving 1 space
					if(yOld+1 == yNew || yOld-1 == yNew) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            if (spacePiece.equals("X") || spacePiece.equals("O")) {
				            	gameBean.setErrorstring("That space is already occupied!");
			            		return false;
			            }
			            else {
			            		gameBean.setErrorstring("");
		            			return true;
			            }
					}
					//Check if piece is trying to jump opponent
					if(yOld+2 == yNew || yOld-2 == yNew) {
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            //Check that jump to space is empty
			            if (!spacePiece.equals("X") || !spacePiece.equals("O")) {
			            		//Check if piece is jumping to right
			            		if (yNew > yOld) {
			            			spaceLocale = xOld + String.valueOf(yOld + 1);
			            		}
			            		else {
			            			spaceLocale = xOld + String.valueOf(yOld - 1);
			            		}
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
					        		log.error("Exception in MyTurn : " + e );
					        }
				            if(capture.equals(piece)) {
				            		gameBean.setErrorstring("You can't capture your own piece!");
			            			return false;
				            }
				            else if (capture.equals(" ")) {
					            	gameBean.setErrorstring("Illegal move!");
			            			return false;
				            }
				            captureLocation = spaceLocale;
				            gameBean.setErrorstring("");
		            			return true;
			            }
					}
				}
				//Check if player if moving forward
				if(yOld == yNew) {
					if((xArray.indexOf(xNew) - xArray.indexOf(xOld)) == 1) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            if (spacePiece.equals("X") || spacePiece.equals("O")) {
			            		gameBean.setErrorstring("That space is already occupied!");
		            			return false;
			            }
			            else {
			            		gameBean.setErrorstring("");
		            			return true;
			            }
					}
					//Check if piece is trying to jump opponent
					if((xArray.indexOf(xNew) - xArray.indexOf(xOld)) == 2) {
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            //Check that jump to space is empty
			            if (spacePiece.equals(" ")) {
			            		spaceLocale = xArray.charAt(xArray.indexOf(xOld)+1) + String.valueOf(yOld);;
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (Exception e) {
					        		log.error("Exception in MyTurn : " + e );
					        }
				            if(capture.equals(piece)) {
				            		gameBean.setErrorstring("You can't capture your own piece or blank space!");
			            			return false;
				            }
				            if (capture.equals(" ")) {
				            		gameBean.setErrorstring("Illegal move!");
			            			return false;
				            }
				            captureLocation = spaceLocale;
		            			return true;
			            }
			            gameBean.setErrorstring("That space is already occupied!");
			            return false;
					}
				}
				else {
					gameBean.setErrorstring("Illegal move!");
					return false;
				}
			}
		}
		else if (piece.equals("X")) {
			//See if player is trying to move backwards
			if(xArray.indexOf(xNew) > xArray.indexOf(xOld)) {
				gameBean.setErrorstring("You can't move backwards!");
				return false;
			}
			else {
				//Check if player if moving left or right
				if(xOld == xNew) {
					//Check if piece is just moving 1 space
					if(yOld+1 == yNew || yOld-1 == yNew) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            if (spacePiece.equals("X") || spacePiece.equals("O")) {
				            	gameBean.setErrorstring("That space is already occupied!");
			            		return false;
			            }
			            else {
			            		gameBean.setErrorstring("");
		            			return true;
			            }
					}
					//Check if piece is trying to jump opponent
					if(yOld+2 == yNew || yOld-2 == yNew) {
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            //Check that jump to space is empty
			            if (!spacePiece.equals("X") || !spacePiece.equals("O")) {
			            		//Check if piece is jumping to right
			            		if (yNew > yOld) {
			            			spaceLocale = xOld + String.valueOf(yOld + 1);
			            		}
			            		else {
			            			spaceLocale = xOld + String.valueOf(yOld - 1);
			            		}
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
					        		log.error("Exception in MyTurn : " + e );
					        }
				            if(capture.equals(piece)) {
				            		gameBean.setErrorstring("You can't capture your own piece!");
			            			return false;
				            }
				            else if (capture.equals(" ")) {
					            	gameBean.setErrorstring("Illegal move!");
			            			return false;
				            }
				            captureLocation = spaceLocale;
				            gameBean.setErrorstring("");
		            			return true;
			            }
					}
				}
				//Check if player if moving forward
				if(yOld == yNew) {
					if((xArray.indexOf(xOld) - xArray.indexOf(xNew)) == 1) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            if (spacePiece.equals("X") || spacePiece.equals("O")) {
			            		gameBean.setErrorstring("That space is already occupied!");
		            			return false;
			            }
			            else {
			            		gameBean.setErrorstring("");
		            			return true;
			            }
					}
					//Check if piece is trying to jump opponent
					if((xArray.indexOf(xOld) - xArray.indexOf(xNew)) == 2) {
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch ( IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
				        		log.error("Exception in MyTurn : " + e );
				        }
			            //Check that jump to space is empty
			            if (spacePiece.equals(" ")) {
			            		spaceLocale = xArray.charAt(xArray.indexOf(xOld)-1) + String.valueOf(yOld);;
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
					        		log.error("Exception in MyTurn : " + e );
					        }
				            if(capture.equals(piece)) {
				            		gameBean.setErrorstring("You can't capture your own piece or blank space!");
			            			return false;
				            }
				            else if (capture.equals(" ")) {
				            		gameBean.setErrorstring("Illegal move!");
			            			return false;
				            }
				            captureLocation = spaceLocale;
		            			return true;
			            }
					}
					else {
						gameBean.setErrorstring("Illegal move!");
						return false;
					}	
				}
				else {
					gameBean.setErrorstring("Illegal move!");
					return false;
				}
			}
		}
		return true;
	}
	public boolean checkWon (String newLocale, String player1, String player2, String playerturn, Long gameid) {
		if (newLocale.substring(0, 1).equals("A") || newLocale.substring(0, 1).equals("H")) {
			String opponent = "";
            if (playerturn.equals(player1)) {
            		opponent = player2;
	        }
	        else {
	        		opponent = player1;
	        }
			try {
				con = ConnectionManager.getConnection();

			    String updateGameboard = "UPDATE games SET status=?, win=?, loss=? WHERE id=?";
			    PreparedStatement ps;
                            ps = con.prepareStatement(updateGameboard);
			    ps.setString(1, "complete");
			    ps.setString(2, playerturn);
			    ps.setString(3, opponent);
			    ps.setLong(4, gameid);
			    ps.executeUpdate();
			    
			    String updateStatistics = "UPDATE user_statistics SET win=win+1 WHERE username=?";
			    ps = con.prepareStatement(updateStatistics);
			    ps.setString(1, playerturn);
			    ps.executeUpdate();
			    
			    updateStatistics = "UPDATE user_statistics SET loss=loss+1 WHERE username=?";
			    ps = con.prepareStatement(updateStatistics);
			    ps.setString(1, opponent);
			    ps.executeUpdate();
			    con.close();
			}
			catch (SQLException e) {
				log.error("Exception in MyTurn : " + e );
			}
			return true;
		}
		return false;
	}
	public void updateMove (String piece, String oldLocale, String newLocale, String playermove, String playerturn, Long gameid) {
		try {
	        String oldLocation = oldLocale.toLowerCase();
	        String newLocation = newLocale.toLowerCase();
			con = ConnectionManager.getConnection();

		    String updateGameboard = "UPDATE game_board SET " + oldLocation + "=?, " + newLocation + "=? WHERE id=?";
		    PreparedStatement ps = con.prepareStatement(updateGameboard);
		    ps.setString(1, null);
		    ps.setString(2, piece);
		    ps.setLong(3, gameid);
		    ps.executeUpdate();
		    
		    if(captureLocation != null) {
			    String updateCapture = "UPDATE game_board SET " + captureLocation + "=? WHERE id=?";
			    ps = con.prepareStatement(updateCapture);
			    ps.setString(1, " ");
			    ps.setLong(2, gameid);
			    ps.executeUpdate();
		    }
		    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    
		    String turn = "UPDATE games SET playerturn=?, last_move=? WHERE id=?";
		    ps = con.prepareStatement(turn);
		    ps.setString(1, playerturn);
		    ps.setTimestamp(2, timestamp);
		    ps.setLong(3, gameid);
		    ps.executeUpdate();
		    
		    String move = "INSERT INTO game_moves (id, player, oldlocale, newlocale) VALUES (?,?,?,?)";
		    ps = con.prepareStatement(move);
		    ps.setLong(1, gameid);
		    ps.setString(2, playermove);
		    ps.setString(3, oldLocale);
		    ps.setString(4, newLocale);
		    ps.executeUpdate();
    			con.close();
		}
        catch (SQLException e) {
        		log.error("Exception in MyTurn : " + e );
		}
	}

}
