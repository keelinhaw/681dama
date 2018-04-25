package com.Dama;


import java.io.IOException;
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
import javax.servlet.http.HttpSession;

import java.lang.reflect.Method;


/**
 * Servlet implementation class MyTurn
 */
@WebServlet("/MyTurn")
public class MyTurn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Game gameBean = new Game();
	String captureLocation = null;
       
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
        String oldLocale = oldLocation.toUpperCase();
        String newLocale = newLocation.toUpperCase();
        HttpSession session = request.getSession();
        gameBean = (Game) session.getAttribute("gameBean");
        String playerturn = gameBean.getPlayerturn();
        String player1 = gameBean.getPlayer1();
        String player2 = gameBean.getPlayer2();
        Long gameid = gameBean.getGameid();
        String piece = "";
        
        try {
	    		//Get the piece to move
	        Method move1 = Game.class.getDeclaredMethod("get" + oldLocale);
	        piece = (String) move1.invoke(gameBean);
        }
        catch (Exception e) {
        		e.printStackTrace(System.out);
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
		        catch (Exception e) {
		        		e.printStackTrace(System.out);
		        }
	            if (playerturn.equals(player1)) {
		        		gameBean.setPlayerturn(player2);
		        		updateMove(piece, oldLocation, newLocation, player2, gameid);
		        }
		        else {
		        		gameBean.setPlayerturn(player1);
		        		updateMove(piece, oldLocation, newLocation, player1, gameid);
		        }
        		}
        		if(checkWon(newLocale)) {
        			
        		}
        }
        saveError(gameid);
        session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");
	}
	public void saveError (Long gameid) {
		String errorString = gameBean.getErrorstring();
		try {
			GetPropertyValues properties = new GetPropertyValues();
			String dburl = properties.getPropValues("dburl");
			String dbuser = properties.getPropValues("dbuser");
			String dbpassword = properties.getPropValues("dbpassword");
	    	    Class.forName("org.postgresql.Driver");
	    	    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
	    		String updateError = "UPDATE games SET error_message=? WHERE id=?";
	    		PreparedStatement ps = con.prepareStatement(updateError);
			ps.clearParameters();
			ps.setString(1, errorString);
			ps.setLong(2, gameid);
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
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
					//System.out.println("X coordinates match, trying to move left or right.");
					//System.out.println("xOld: " + xOld + " | xNew: " + xNew + " | yOld: " + yOld + " | yNew: " + yNew);
					//Check if piece is just moving 1 space
					if(yOld+1 == yNew || yOld-1 == yNew) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
			            		System.out.println("calling get" + newLocale);
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            System.out.println("The spacePiece is" + spacePiece);
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
						System.out.println("Attempting to capture!");
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            //Check that jump to space is empty
			            if (!spacePiece.equals("X") || !spacePiece.equals("O")) {
			            		//Check if piece is jumping to right
			            		if (yNew > yOld) {
			            			spaceLocale = xOld + String.valueOf(yOld + 1);
			            			System.out.println("Piece to check capture is " + spaceLocale);
			            		}
			            		else {
			            			spaceLocale = xOld + String.valueOf(yOld - 1);
			            			System.out.println("Piece to check capture is " + spaceLocale);
			            		}
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (Exception e) {
					        		e.printStackTrace(System.out);
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
					System.out.println("xNew index is: " + xArray.indexOf(xNew));
					System.out.println("xOld index is: " + xArray.indexOf(xOld));
					if((xArray.indexOf(xNew) - xArray.indexOf(xOld)) == 1) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            System.out.println("The spacePiece is: " + spacePiece);
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
				        		e.printStackTrace(System.out);
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
					        		e.printStackTrace(System.out);
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
					//System.out.println("X coordinates match, trying to move left or right.");
					//System.out.println("xOld: " + xOld + " | xNew: " + xNew + " | yOld: " + yOld + " | yNew: " + yNew);
					//Check if piece is just moving 1 space
					if(yOld+1 == yNew || yOld-1 == yNew) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
			            		System.out.println("calling get" + newLocale);
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            System.out.println("The spacePiece is" + spacePiece);
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
						System.out.println("Attempting to capture!");
						String spacePiece = "";
						String capture = "";
						String spaceLocale = "";
			            try {
					        //Get piece (if any) at new location
				            	Method move = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) move.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            //Check that jump to space is empty
			            if (!spacePiece.equals("X") || !spacePiece.equals("O")) {
			            		//Check if piece is jumping to right
			            		if (yNew > yOld) {
			            			spaceLocale = xOld + String.valueOf(yOld + 1);
			            			System.out.println("Piece to check capture is " + spaceLocale);
			            		}
			            		else {
			            			spaceLocale = xOld + String.valueOf(yOld - 1);
			            			System.out.println("Piece to check capture is " + spaceLocale);
			            		}
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (Exception e) {
					        		e.printStackTrace(System.out);
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
					//System.out.println("xNew index is: " + xArray.indexOf(xNew));
					//System.out.println("xOld index is: " + xArray.indexOf(xOld));
					if((xArray.indexOf(xOld) - xArray.indexOf(xNew)) == 1) {
						String spacePiece = "";
			            try {
					        //Get piece (if any) at new location
				            	Method query = Game.class.getDeclaredMethod("get" + newLocale);
				    	        spacePiece = (String) query.invoke(gameBean);
				        }
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            //System.out.println("The spacePiece is: " + spacePiece);
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
				        catch (Exception e) {
				        		e.printStackTrace(System.out);
				        }
			            //Check that jump to space is empty
			            if (spacePiece.equals(" ")) {
			            		spaceLocale = xArray.charAt(xArray.indexOf(xOld)-1) + String.valueOf(yOld);;
				            try {
						        //Get piece to capture location
					            	Method query = Game.class.getDeclaredMethod("get" + spaceLocale);
					    	        capture = (String) query.invoke(gameBean);
					        }
					        catch (Exception e) {
					        		e.printStackTrace(System.out);
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
						//System.out.println("Invalid move. Please try again!");
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
	public boolean checkWon (String newLocale) {
		if (newLocale.substring(0, 1).equals("A") || newLocale.substring(0, 1).equals("H")) {
			System.out.println("The game is finished!");
			return true;
		}
		return false;
	}
	public void updateMove (String piece, String oldLocale, String newLocale, String playerturn, Long gameid) {
		try {
	        String oldLocation = oldLocale.toLowerCase();
	        String newLocation = newLocale.toLowerCase();
			GetPropertyValues properties = new GetPropertyValues();
			String dburl = properties.getPropValues("dburl");
			String dbuser = properties.getPropValues("dbuser");
			String dbpassword = properties.getPropValues("dbpassword");
		    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);

		    String updateGameboard = "UPDATE game_board SET " + oldLocation + "=?, " + newLocation + "=? WHERE id=?";
		    PreparedStatement ps = con.prepareStatement(updateGameboard);
		    ps.setString(1, null);
		    ps.setString(2, piece);
		    ps.setLong(3, gameid);
		    ps.executeUpdate();
		    
		    if(captureLocation != null) {
			    String updateCapture = "UPDATE game_board SET " + captureLocation + "=? WHERE id=?";
			    System.out.println("Capture location is:" + captureLocation);
			    ps = con.prepareStatement(updateCapture);
			    ps.setString(1, " ");
			    ps.setLong(2, gameid);
			    ps.executeUpdate();
		    }
		    
		    String turn = "UPDATE games SET playerturn=? WHERE id=?";
		    ps = con.prepareStatement(turn);
		    ps.setString(1, playerturn);
		    ps.setLong(2, gameid);
		    ps.executeUpdate();
    			con.close();
		}
        catch (Exception e) {
    			e.printStackTrace();
		}
	}

}
