package com.Dama;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Chadia
 */
@WebServlet("/GameHistory")
public class GameHistory extends HttpServlet {

    
//        static Logger log = Logger.getLogger(GameHistory.class);
         private static final Logger log = LogManager.getLogger(GameHistory.class);
        static Connection con = null;
    
                
                public ResultSet GetUserHistory(String username ){
              
                    ResultSet resultSet = null;
                    PreparedStatement ps = null;
                    String query = "SELECT player1, startdate FROM history where player1 = ?";

                try {
                    
                    con = ConnectionManager.getConnection();
                        
                 ps = con.prepareStatement(query);
		    ps.clearParameters();
		    ps.setString(1, username);
                    resultSet =  ps.executeQuery();

                    } catch (SQLException e) {
                        log.error("Encountered SQL error in GameHistory " + e);
                    }                    
                
                    return resultSet;
                
                }

            
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
 /*           out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GameHistory</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GameHistory at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
 
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>GameHistory JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
   
      	        HttpSession session = request.getSession();                      
	        String username = (String) session.getAttribute("username");  
 //     String username = session.getAttribute("username")
 //      String username = request.getParameter("username");

	if(username == null){
	    String redirectURL = "./login.html";
	    response.sendRedirect(redirectURL);
	}

      out.write("\n");
      out.write("\n");
      out.write("        <h1>List of completed games!</h1>\n");
      out.write("        \n");
      out.write("          ");
      out.write("\n");
      out.write("            ");
 
//                History gameHist = new History();
                ResultSet games = GetUserHistory(username);
//                  ResultSet games = gameHist.GetUserHistory("sam");
                
            
      out.write("\n");
      out.write("            \n");
      out.write("            <table border=\"1\">\n");
      out.write("                <tbody>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>for debugging</td>\n");
      out.write("                        <td>Player1</td>\n");
      out.write("                        <td>Game Date</td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 while (games.next()) {
                    
      out.write("\n");
      out.write("                    <tr>\n");
      out.write("                        <td>game record</td>\n");
      out.write("                        <td>");
      out.print( games.getString("player1") );
      out.write("</td>\n");
      out.write("                        <td>");
      out.print( games.getDate("startdate") );
      out.write("</td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("<br/>");
      out.write("<br/>");
      out.write("<br/>");    
     
      out.write(" <form action=\"./Logout\" method=\"post\">\n");
      out.write("<input type=\"submit\" value=\"Logout\" />\n");
      out.write("</form>");
      
      out.write("    </body>\n");
      out.write("</html>\n");
        }   catch (SQLException ex) {
                log.error( "Encountered SQL error in GameHistory " + ex);
            }
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description of Game History Servlet";
    }// </editor-fold>

}
